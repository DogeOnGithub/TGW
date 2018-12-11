package cn.tgw.order.service;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.goods.model.Goods;
import cn.tgw.order.model.Order;
import cn.tgw.user.model.User;

import java.util.List;

public interface OrderService {

    /*
     * @Description:获取所有的订单信息，作测试使用
     * @Param:[]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:15:38
     **/
    List<Order> getAllOrders();

    /*
     * @Description:根据用户获取该用户所有订单，用户不可使用，因为该方法会查询到逻辑删除的订单
     * @Param:[user]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:12
     **/
    List<Order> getAllOrdersByUser(User user);

    /*
     * @Description:根据用户id获取该用户所有订单，用户不可使用，因为该方法会查询到逻辑删除的订单
     * @Param:[userId]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:12
     **/
    List<Order> getAllOrdersByUserId(int userId);

    /*
     * @Description:根据商家查询所有该商家的订单
     * @Param:[businessman]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:53
     **/
    List<Order> getAllOrdersByBusinessman(Businessman businessman);

    /*
     * @Description:根据商家id查询所有该商家的订单
     * @Param:[businessmanId]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:53
     **/
    List<Order> getAllOrdersByBusinessmanId(int businessmanId);

    /*
     * @Description:根据用户以及商品和数量创建订单
     * @Param:[user, goods, count]
     * @Return:cn.tgw.order.model.Order
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:29
     **/
    Order createOrderWithUserAndGoods(User user, Goods goods, int count);

    /*
     * @Description:根据用户id、商品id、数量创建订单
     * @Param:[userId, goodsId, count]
     * @Return:cn.tgw.order.model.Order
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:30
     **/
    Order createOrderWithUserIdAndGoodsId(int userId, int goodsId, int count);

    /*
     * @Description:根据用户id、商品、数量创建订单
     * @Param:[userId, goods, count]
     * @Return:cn.tgw.order.model.Order
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:16:33
     **/
    Order createOrderWithUserIdAndGoods(int userId, Goods goods, int count);

    /*
     * @Description:根据用户以及订单状态查询订单，未逻辑删除的
     * @Param:[user, sellStatus]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:17:07
     **/
    List<Order> getOrdersByUserAndOrderSellStatusAndStatusNormal(User user, Byte sellStatus);

    /*
     * @Description:根据用户id以及订单状态查询订单，未逻辑删除的
     * @Param:[userId, sellStatus]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-05
     * @Time:17:18
     **/
    List<Order> getOrdersByUserIdAndOrderSellStatusAndStatusNormal(int userId, Byte sellStatus);

    List<Order> getUserAllOrders(int userId);

    /*
     * @Description:根据用户查询该用户的所有未逻辑删除的订单，用户使用
     * @Param:[user]
     * @Return:java.util.List<cn.tgw.order.model.Order>
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:08:54
     **/
    List<Order> getUserAllOrders(User user);

    /*
     * @Description:RabbitMQ监听消息队列
     * @Param:[id]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:08:36
     **/
    public void orderTimeQueueListener(String id);

    /*
     * @Description:根据订单的ID逻辑删除订单，用于处理用户删除订单的请求
     * @Param:[id]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:08:37
     **/
    public boolean deleteByOrderId(int id);

}
