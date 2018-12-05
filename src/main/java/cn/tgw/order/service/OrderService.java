package cn.tgw.order.service;

import cn.tgw.order.model.Order;

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

}
