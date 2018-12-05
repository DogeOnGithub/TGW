package cn.tgw.order.service.serviceImpl;

import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.goods.mapper.GoodsDetailMapper;
import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.order.mapper.OrderMapper;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private BusinessmanMapper businessmanMapper;

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
    public Order createOrderWithUserAndGoods(User user, Goods goods, int count) {

        Order createOrder = new Order();

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
}
