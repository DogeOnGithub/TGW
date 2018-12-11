package cn.tgw.order.service.serviceImpl;

import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.common.utils.OrderUtils;
import cn.tgw.goods.mapper.GoodsDetailMapper;
import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.order.mapper.OrderMapper;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
 * @Project:tgw
 * @Description:order service impl
 * @Author:TjSanshao
 * @Create:2018-12-05 15:38
 *
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private BusinessmanMapper businessmanMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAllOrders();
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return this.getAllOrdersByUserId(user.getId());
    }

    @Override
    public List<Order> getAllOrdersByUserId(int userId) {
        return orderMapper.selectAllOrdersByUserId(userId);
    }

    @Override
    public List<Order> getAllOrdersByBusinessman(Businessman businessman) {
        return orderMapper.selectAllOrdersByBusinessmanId(businessman.getId());
    }

    @Override
    public List<Order> getAllOrdersByBusinessmanId(int businessmanId) {
        return getAllOrdersByBusinessman(businessmanMapper.selectByPrimaryKey(businessmanId));
    }

    @Override
    @Transactional
    public Order createOrderWithUserAndGoods(User user, Goods goods, int count) {

        Order createOrder = new Order();

        //设置订单唯一编号
        createOrder.setUniqueOrderNumber(OrderUtils.getOrderNumber(user.getMobile()));

        createOrder.setTgwUserId(user.getId());
        createOrder.setTgwGoodsId(goods.getId());
        createOrder.setCount(count);
        createOrder.setOrderCreateTime(new Date());
        createOrder.setSellStatus(new Byte("0"));
        createOrder.setOrderStatus(new Byte("1"));
        createOrder.setTotal(goods.getDiscountPrice().multiply(new BigDecimal(count)));
        createOrder.setTgwBusinessmanId(goods.getTgwBusinessmanId());

        int row = orderMapper.insertSelective(createOrder);

        if (row < 1) {
            return null;
        }

        //加入到消息队列
        rabbitTemplate.convertAndSend("tgw.ordertime.firstIn.exchange", "", createOrder.getId());

        return orderMapper.selectByPrimaryKey(createOrder.getId());
    }

    @Override
    public Order createOrderWithUserIdAndGoodsId(int userId, int goodsId, int count) {

        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

        return createOrderWithUserIdAndGoods(userId, goods, count);
    }

    @Override
    public Order createOrderWithUserIdAndGoods(int userId, Goods goods, int count) {
        Order createOrder = new Order();

        //设置订单唯一编号
        createOrder.setUniqueOrderNumber(OrderUtils.getOrderNumber(userMapper.selectByPrimaryKey(userId).getMobile()));

        createOrder.setTgwUserId(userId);
        createOrder.setTgwGoodsId(goods.getId());
        createOrder.setCount(count);
        createOrder.setOrderCreateTime(new Date());
        createOrder.setSellStatus(new Byte("0"));
        createOrder.setOrderStatus(new Byte("1"));
        createOrder.setTotal(goods.getDiscountPrice().multiply(new BigDecimal(count)));
        createOrder.setTgwBusinessmanId(goods.getTgwBusinessmanId());

        int row = orderMapper.insertSelective(createOrder);

        if (row < 1) {
            return null;
        }

        //加入到消息队列
        rabbitTemplate.convertAndSend("tgw.ordertime.firstIn.exchange", "", createOrder.getId());

        return orderMapper.selectByPrimaryKey(createOrder.getId());
    }

    @Override
    public List<Order> getOrdersByUserAndOrderSellStatusAndStatusNormal(User user, Byte sellStatus) {
        return this.getOrdersByUserIdAndOrderSellStatusAndStatusNormal(user.getId(), sellStatus);
    }

    @Override
    public List<Order> getOrdersByUserIdAndOrderSellStatusAndStatusNormal(int userId, Byte sellStatus) {

        Order order = new Order();
        order.setTgwUserId(userId);
        order.setSellStatus(sellStatus);
        order.setOrderStatus(new Byte("1"));

        return orderMapper.selectAllOrdersByUserIdAndSellStatusAndStatus(order);
    }

    @Override
    public List<Order> getUserAllOrders(int userId) {
        return orderMapper.selectOrdersStatusNormalByUserId(userId);
    }

    @Override
    public List<Order> getUserAllOrders(User user) {
        return this.getUserAllOrders(user.getId());
    }

    @Override
    @RabbitListener(queues = {"tgw.ordertime.queue"})
    public void orderTimeQueueListener(String id) {
        Order order = orderMapper.selectByPrimaryKey(Integer.valueOf(id));

        System.out.println("orderTimeQueueListener");
        System.out.println(order);
        System.out.println(id);

        if (order == null) {
            return;
        }

        if (order.getSellStatus().intValue() != new Byte("0").intValue()) {
            return;
        }

        //订单已过期
        order.setSellStatus(new Byte("5"));

        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public boolean deleteByOrderId(int id) {

        Order order = orderMapper.selectByPrimaryKey(id);

        if (order == null) {
            return false;
        }

        //将标志位设置为删除
        order.setOrderStatus(new Byte("0"));

        int row = orderMapper.updateByPrimaryKeySelective(order);

        if (row < 1) {
            return false;
        }

        return true;
    }

    @Override
    public Order getOrderByIdPayAble(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);

        //如果订单不存在
        if (order == null) {
            return null;
        }

        //如果订单不是正常状态，返回null
        if (order.getOrderStatus().intValue() != 1) {
            return null;
        }

        //如果订单不是处于未付款状态，返回null
        if (order.getSellStatus().intValue() != 0) {
            return null;
        }

        return order;
    }

    @Override
    public Order getOrderById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}
