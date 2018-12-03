package cn.tgw.businessman.controller;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @Project:tgw
 * @Description:businessman controller
 * @Author:TjSanshao
 * @Create:2018-12-03 10:11
 *
 **/

@RestController
public class BusinessmanController {

    @Autowired
    private BusinessmanService businessmanService;

    @Autowired
    private SmsVerifyService smsVerifyService;

    @Autowired
    private MiaoDiService miaoDiService;

    /*
     * @Description:商家用户登录功能
     * @Param:[username, password, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:10:48
     **/
    @PostMapping("/businessman/login")
    public Map<String, Object> login(String username, String password, HttpSession session) {
        HashMap<String, Object> loginStatus = new HashMap<>();

        //校验用户名和密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            loginStatus.put("message", "please input username and password");
            loginStatus.put("status", "fail");
            return loginStatus;
        }

        //调用service，获取记录
        Businessman businessman = businessmanService.getBusinessmanByUsernameAndPasswordAndStatus(username, password, new Byte("1"));
        if (businessman != null) {
            loginStatus.put("status", "success");
            loginStatus.put("message", "login success");
            businessman.setPassword(""); //不返回密码到客户端
            loginStatus.put("businessman", businessman);

            //将记录存入session，键为"user"，值为User对象
            session.setAttribute("businessman", businessman);
        }else {
            loginStatus.put("status", "fail");
            loginStatus.put("message", "username or password error");
        }

        return loginStatus;
    }

    @GetMapping("/businessman/sendMsgCode")
    public Map<String, Object> sendMsgCode(String mobileNumber, String requestParam, HttpSession session) {
        HashMap<String, Object> sendMsgStatus = new HashMap<>();

        if (requestParam != null) {
            //是否是修改密码要求的验证码
            if (requestParam.equals("password")) {
                //查询session，用户是否已经登录
                Object sessionBusinessman = session.getAttribute("businessman");
                if (sessionBusinessman == null) {
                    //没有登录
                    sendMsgStatus.put("status", "authority");
                    sendMsgStatus.put("message", "login first");
                    return sendMsgStatus;
                }

                //已经登录，从session中获取的businessman对象持有id
                Businessman businessmanFromSession = (Businessman)sessionBusinessman;

                //根据businessman的userId查询绑定的手机号
                BusinessmanDetail businessmanDetail = businessmanService.getBusinessmanDetailByBusinessmanId(businessmanFromSession);

                //异步发送验证码，返回响应
                smsVerifyService.sendMsgCodeAsync(businessmanDetail.getPhoneNumber(), miaoDiService.generateCode(6));

                sendMsgStatus.put("status", "success");
                sendMsgStatus.put("message", "success");

                return sendMsgStatus;
            }
        }

        //验证手机号码是否为空
        if (StringUtils.isEmpty(mobileNumber)){
            sendMsgStatus.put("status", "fail");
            sendMsgStatus.put("message", "please input correct phone number");
            return sendMsgStatus;
        }

        //校验手机号码的正则表达式
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

        if (mobileNumber.length() != 11) {
            //如果不是11位，不是手机号码，直接返回fail
            sendMsgStatus.put("status", "fail");
            sendMsgStatus.put("message", "please input correct phone number");
            return sendMsgStatus;
        } else {
            //是11位手机号码，开始用正则匹配
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobileNumber);
            boolean isMatch = m.matches();
            if (!isMatch) {
                //如果匹配不成功，不合法，返回fail
                sendMsgStatus.put("status", "fail");
                sendMsgStatus.put("message", "please input correct phone number");
                return sendMsgStatus;
            } else {
                //验证手机号是否可以发送验证码
                if (!smsVerifyService.enableSend(mobileNumber)) {
                    //该手机号不可以发送验证码，超出了每天发送次数
                    sendMsgStatus.put("status", "fail");
                    sendMsgStatus.put("message", "send times out");
                    return sendMsgStatus;
                }

                //异步发送验证码
                smsVerifyService.sendMsgCodeAsync(mobileNumber, miaoDiService.generateCode(6));

                sendMsgStatus.put("status", "success");
                sendMsgStatus.put("message", "success");

                return sendMsgStatus;
            }
        }

    }
}
