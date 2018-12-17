package cn.tgw.order.controller;

import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.admin.service.SecKillService;
import cn.tgw.common.service.AlipayService;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.service.GoodsService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SecKillService secKillService;

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
            createStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            createStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input the whole");
            return createStatus;
        }

        User user = (User) session.getAttribute(TGWStaticString.TGW_USER);

        Order order =  orderService.createOrderWithUserIdAndGoodsId(user.getId(), goodsId, count);

        if (order == null) {
            createStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            createStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "unknown error");
            return createStatus;
        }

        createStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        createStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");
        createStatus.put("order", order);

        //返回过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderCreateTime());
        calendar.add(Calendar.MINUTE, 15);

        createStatus.put("expire", calendar.getTime());

        return createStatus;

    }

    /*
     * @Description:订单支付
     * @Param:[orderId, session, request, response]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:11:13
     **/
    @PostMapping("/tjsanshao/order/pay")
    public void pay(Integer orderId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Order order = orderService.getOrderByIdPayAble(orderId);

        //该订单不存在，无效
        if (order == null) {
            response.setContentType("application/json");
            response.getWriter().print("\"status\":\"fail\",\"message\":\"order is not available\"");
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        //查询goods和goodsDetail
        Map<String, Object> detailMap = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(order.getTgwGoodsId());
        Goods goods = (Goods)detailMap.get("goods");
        GoodsDetail goodsDetail = (GoodsDetail)detailMap.get("goodsDetail");

        //发送支付请求
        String result = alipayService.alipayWithBizParam(
                order.getUniqueOrderNumber(),
                order.getTotal().toString(),
                goods.getGoodsTitle(),
                goodsDetail.getGoodsDesc()
        );

        //响应
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(result);
        writer.flush();
        writer.close();

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
    @ResponseBody
    public String alipayNotifyUrl(
            String notify_time,
            String trade_no,
            String app_id,
            String out_trade_no,
            String trade_status) {

        if (!trade_status.equals("TRADE_SUCCESS")) {
            return "fail";
        }

        //支付成功后，更新数据库
        Order order = orderService.getOrderByUniqueOrderNumber(out_trade_no);
        order.setPaySerialsNumber(trade_no);

        if (orderService.orderPayFinish(order)) {
            return "success";
        } else {
            return "fail";
        }
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
    public String alipayReturnUrl(
            String timestamp,
            String out_trade_no,
            String trade_no,
            String total_amount,
            String seller_id,
            Map<String, String> kvMap) {

        System.out.println(timestamp);
        System.out.println(out_trade_no);
        System.out.println(trade_no);
        System.out.println(total_amount);
        System.out.println(seller_id);

        kvMap.put("timestamp", timestamp);
        kvMap.put("out_trade_no", out_trade_no);
        kvMap.put("trade_no", trade_no);
        kvMap.put("total_amount", total_amount);
        kvMap.put("seller_id", seller_id);

        return "test/return";
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
