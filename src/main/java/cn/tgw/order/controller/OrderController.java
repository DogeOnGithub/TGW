package cn.tgw.order.controller;

import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
     * @Description:通过商品id以及数量创建订单
     * @Param:[goodsId, count, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-10
     * @Time:11:18
     **/
    @PostMapping("/tjsanshao/order/create")
    @ResponseBody
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

    /*
     * @Description:支付宝支付成功异步通知url
     * @Param:[request]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-12-10
     * @Time:11:21
     **/
    @PostMapping("/alipay/notify")
    public void alipayNotifyUrl(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();

        //TODO 支付成功后，更新数据库
    }

    /*
     * @Description:支付宝支付成功同步通知url
     * @Param:[request]
     * @Return:java.lang.String
     * @Author:TjSanshao
     * @Date:2018-12-10
     * @Time:11:24
     **/
    @GetMapping("/alipay/return")
    public String alipayReturnUrl(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();

        //TODO 支付成功后，跳转页面到支付完毕页面

        return "payFinish";
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
