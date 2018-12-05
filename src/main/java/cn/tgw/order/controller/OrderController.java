package cn.tgw.order.controller;

import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

}
