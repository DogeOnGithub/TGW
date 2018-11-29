package cn.tgw.user.controller;

import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
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
                if (!smsVerifyService.enableSend(mobileNumber)){
                    //该手机号不可以发送验证码，超出了每天发送次数
                    sendMsgStatus.put("status", "fail");
                    sendMsgStatus.put("message", "send times out");
                    return sendMsgStatus;
                }

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

    /*
     * @Description:用户注册，用户名、密码、手机号码、验证码是必填项
     * @Param:[user, mobile, code]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:11:09
     **/
    @GetMapping("/user/register")
    public Map<String, Object> register(User user, UserDetail userDetail, String code){
        HashMap<String, Object> registerStatus = new HashMap<>();

        //验证用户名、密码、手机号码是否为空
        if (user.getUsername() == null || user.getPassword() == null || StringUtils.isEmpty(userDetail.getMobile()) || StringUtils.isEmpty(code)){
            registerStatus.put("status", "fail");
            registerStatus.put("message", "please input the whole");
            return registerStatus;
        }

        //验证验证码是否正确
        if (!smsVerifyService.checkCode(userDetail.getMobile(), code)){
            registerStatus.put("status", "fail");
            registerStatus.put("message", "code is invalid");
            return registerStatus;
        }

        //填写了用户名、密码、手机号码、验证码，验证是否可以注册
        if (!userService.enableUserRegister(user)){
            //用户名已存在，不可注册
            registerStatus.put("status", "fail");
            registerStatus.put("message", "username exists");
            return registerStatus;
        }

        if (!userService.enableMoblieRegister(userDetail.getMobile())){
            //手机号已绑定，不可注册
            registerStatus.put("status", "fail");
            registerStatus.put("message", "mobile exists");
            return registerStatus;
        }

        //存入到数据库
        userService.userRegister(user, userDetail);

        registerStatus.put("status", "success");
        registerStatus.put("message", "register success");

        return registerStatus;
    }

}
