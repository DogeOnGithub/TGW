package cn.tgw.common.controller;

import cn.tgw.common.service.AlipayService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

/*
 * @Project:tgw
 * @Description:test common service
 * @Author:TjSanshao
 * @Create:2018-12-06 10:02
 *
 **/
@Controller
public class TestController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/common/test/alipay")
    public void testAlipay(HttpServletResponse response) {
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = "134201204240999";
        //付款金额，必填
        String total_amount = "99";
        //订单名称，必填
        String subject = "test";
        //商品描述，可空
        String body = "test test";

        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(alipayService.alipayWithBaseParam(out_trade_no, total_amount, subject));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/common/test/notify_url")
    @ResponseBody
    public String testNotifyUrl(HttpServletRequest request){

        Map<String, String[]> parameterMap = request.getParameterMap();

        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String name = (String)iterator.next();
            String[] strings = parameterMap.get(name);
            System.out.print(name + " : ");
            for (int i = 0; i < strings.length; i++) {
                System.out.print(strings[i] + ", ");
            }
        }

        System.out.println("notify_url");
        return "notify_url";
    }

    @GetMapping("/common/test/return_url")
    @ResponseBody
    public Map<String, String[]> testReturnUrl(HttpServletRequest request){

        return request.getParameterMap();
    }

}
