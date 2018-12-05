package cn.tgw.order.service.serviceImpl;

import cn.tgw.order.mapper.OrderMapper;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAllOrders();
    }
}
