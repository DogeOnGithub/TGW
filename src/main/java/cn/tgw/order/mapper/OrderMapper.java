package cn.tgw.order.mapper;

import cn.tgw.order.model.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //自定义查询开始

    List<Order> selectAllOrders();

    List<Order> selectAllOrdersByUserId(Integer userId);

    List<Order> selectAllOrdersByBusinessmanId(Integer businessmanId);

    List<Order> selectAllOrdersByUserIdAndSellStatusAndStatus(Order order);

    //自定义查询结束
}