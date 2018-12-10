package cn.tgw.order.controller;

import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @Project:tgw
 * @Description:order controller
 * @Author:TjSanshao
 * @Create:2018-12-05 17:32
 *
 **/
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/tjsanshao/order/create")
    public Map<String, Object> create(Integer goodsId, Integer count, HttpSession session) {
        HashMap<String, Object> createStatus = new HashMap<>();

        if (goodsId == null || count == null) {
            createStatus.put("status", "fail");
            createStatus.put("message", "please input the whole");
            return createStatus;
        }

        User user = (User) session.getAttribute(TGWStaticString.TGW_USER);

        Order order =  orderService.createOrderWithUserIdAndGoodsId(user.getId(), goodsId, count);

        if (order == null) {
            createStatus.put("status", "fail");
            createStatus.put("message", "unknown error");
            return createStatus;
        }

        createStatus.put("status", "success");
        createStatus.put("message", "success");
        createStatus.put("order", order);

        return createStatus;

    }

    /**
     *
     * 功能描述: 查询用户订单，根据订单状态sellStatus查询
     *                                  sellStatus：0未付款，1已付款，2退款，3已使用，4已评论，5已过期
     * @param: HttpSession session,int sellStatus
     * @return:
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "/xiaojian/order/getOrders",method = RequestMethod.GET)
    public Object getOrders(HttpSession session,@RequestParam(value = "sellStatus",required = false)Integer sellStatus){
        Map<String,Object> result = new HashMap<>();
        result.put("status", "fail");
        result.put("message", "unknown error");
        User user = (User) session.getAttribute(TGWStaticString.TGW_USER);
        List<Order> resultList = new ArrayList<>();
        if (sellStatus!=null){
            resultList = orderService.getOrdersByUserIdAndOrderSellStatusAndStatusNormal(user.getId(), sellStatus.byteValue());
        }else {
            resultList = orderService.getAllOrdersByUserId(user.getId());
        }
        result.put("status", "success");
        result.put("message", "success");
        result.put("orders",resultList);
        return result;
    }

}
