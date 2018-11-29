package cn.tgw.user.controller;

import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.user.model.User;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @Project:tgw
 * @Description:user controller
 * @Author:TjSanshao
 * @Create:2018-11-26 17:28
 *
 **/

@RestController
public class UserController {

    //注入用户处理的service
    @Autowired
    private UserService userService;

    //注入手机验证码处理的service
    @Autowired
    private SmsVerifyService smsVerifyService;

    //注入秒嘀工具类service，用于生成验证码
    @Autowired
    private MiaoDiService miaoDiService;

    /*
     * @Description:用户登录
     * @Param:[username, password, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:17:38
     **/
    @GetMapping("/user/login")
    public Map<String, Object> login(String username, String password, HttpSession session){
        HashMap<String, Object> loginStatus = new HashMap<>();

        //校验用户名和密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            loginStatus.put("message", "please input username and password");
            loginStatus.put("status", "fail");
            return loginStatus;
        }

        //调用service，获取记录
        User user = userService.getUserByUsernameAndPasswordAndStatus(username, password, new Byte("1"));
        if (user != null){
            loginStatus.put("status", "success");
            loginStatus.put("message", "login success");
            loginStatus.put("user", user);

            //将记录存入session，键为username，值为User对象
            session.setAttribute(user.getUsername(), user);

        }else{
            loginStatus.put("status", "fail");
            loginStatus.put("message", "username or password error");
        }

        return loginStatus;
    }

    /*
     * @Description:请求发送验证码
     * @Param:[mobileNumber]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:17:40
     **/
    @GetMapping("/user/sendMsgCode")
    public Map<String, Object> sendMsgCode(String mobileNumber){
        HashMap<String, Object> sendMsgStatus = new HashMap<>();

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
            }else{
                //TODO 合法的手机号码，校验今天验证码的发送次数

                //校验成功，返回结果，使用异步的方式，发送手机验证码
                sendMsgStatus.put("status", "success");
                sendMsgStatus.put("message", "success");

                //异步发送手机验证码
                smsVerifyService.testAsync();
                smsVerifyService.sendMsgCodeAsync(mobileNumber, miaoDiService.generateCode(6));

                return sendMsgStatus;
            }
        }
    }

}
