package cn.tgw.user.controller;

import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
            user.setPassword(""); //不返回密码到客户端
            loginStatus.put("user", user);

            //将记录存入session，键为"user"，值为User对象
            session.setAttribute(TGWStaticString.TGW_USER, user);

        }else{
            loginStatus.put("status", "fail");
            loginStatus.put("message", "username or password error");
        }

        return loginStatus;
    }

    /*
     * @Description:发送验证码，requestParam=password参数可以用于请求修改密码的验证码（根据用户登录信息发送验证码，不需要输入手机号），不带有requestParam参数时，需要填写mobileNumber参数
     * @Param:[mobileNumber, requestParam, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-30
     * @Time:10:31
     **/
    @GetMapping("/user/sendMsgCode")
    public Map<String, Object> sendMsgCode(String mobileNumber, String requestParam, HttpSession session){
        HashMap<String, Object> sendMsgStatus = new HashMap<>();

        //是否是修改密码要求的验证码
        if (requestParam.equals("password")){
            //查询session，用户是否已经登录
            Object sessionUser = session.getAttribute(TGWStaticString.TGW_USER);
            if (sessionUser == null){
                //用户没有登录，提示用户先登录
                sendMsgStatus.put("status", "authority");
                sendMsgStatus.put("message", "login first");
                return sendMsgStatus;
            }

            //用户已经登录
            User userFromSession = (User)sessionUser;

            //根据用户名查询绑定的手机号码
            UserDetail userDetail = userService.getUserDetailByUserId(userFromSession);

            smsVerifyService.sendMsgCodeAsync(userDetail.getMobile(), miaoDiService.generateCode(6));

            sendMsgStatus.put("status", "success");
            sendMsgStatus.put("message", "success");

            return sendMsgStatus;
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

    /*
     * @Description:修改密码，需要输入新密码、旧密码、验证码，需要用户登录
     * @Param:[password, code, oldPassword, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-30
     * @Time:10:29
     **/
    @GetMapping("/user/password")
    public Map<String, Object> password(String password, String code, String oldPassword, HttpSession session){
        HashMap<String, Object> passwordStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute("user");

        //用户已经登录
        User userFromSession = (User)sessionUser;

        //根据用户名查询绑定的手机号码
        UserDetail userDetail = userService.getUserDetailByUserId(userFromSession);

        //判断是否带有验证码，如果没有，需要发送验证码
        if (StringUtils.isEmpty(code)){
            //请求中没有验证码
            passwordStatus.put("status", "fail");
            passwordStatus.put("message", "verify code error");
            return passwordStatus;
        }

        //请求中带有验证码，开始验证验证码
        if (!smsVerifyService.checkCode(userDetail.getMobile(), code)){
            //验证码不通过
            passwordStatus.put("status", "fail");
            passwordStatus.put("message", "verify code error");
            return passwordStatus;
        }

        //查询数据库中的User，验证用户名和用户输入的旧密码是否匹配
        User queryUser = userService.getUserByUsernameAndPasswordAndStatus(userFromSession.getUsername(), oldPassword, new Byte("1"));

        if (queryUser == null){
            //没有查询到用户，即用户名和旧密码不匹配
            passwordStatus.put("status", "fail");
            passwordStatus.put("message", "old password error");
            return passwordStatus;
        }

        //更新数据库信息
        queryUser.setPassword(password);
        queryUser.setUserStatus(new Byte("1"));
        userService.updateUserPassword(queryUser);

        //设置验证码状态为已使用
        smsVerifyService.codeUsed(userDetail.getMobile());

        passwordStatus.put("status", "success");
        passwordStatus.put("message", "success");

        return passwordStatus;
    }

    /*
     * @Description:使用get请求，返回当前登录用户的用户详细信息，需要用户登录
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-30
     * @Time:10:36
     **/
    @GetMapping("/user/detail")
    public Map<String, Object> userDetail(HttpSession session){
        HashMap<String, Object> getDetailStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute("user");

        //验证用户登录已使用过滤器
//        if (sessionUser == null){
//            //用户未登录
//            getDetailStatus.put("status", "authority");
//            getDetailStatus.put("message", "login first");
//            return getDetailStatus;
//        }

        User userFromSession = (User)sessionUser;

        User userInDB = userService.getUserById(userFromSession.getId());
        UserDetail userDetail = userService.getUserDetailByUserId(userInDB);

        getDetailStatus.put("status", "success");
        userInDB.setPassword("");
        getDetailStatus.put("user", userInDB);
        getDetailStatus.put("userDetail", userDetail);
        getDetailStatus.put("message", "success");

        return getDetailStatus;
    }

    @PostMapping("/user/detail")
    public Map<String, Object> userDetail(UserDetail userDetail, HttpSession session){
        HashMap<String, Object> postDetailStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute("user");

        User userFromSession = (User)sessionUser;
        userDetail.setTgwUserId(userFromSession.getId());
        userDetail.setLastUpdateTime(new Date());

        //更新数据库，并获得更新后的UserDetail
        UserDetail userDetailUpdated = userService.updateUserDetail(userDetail);

        postDetailStatus.put("status", "success");
        userFromSession.setPassword("");
        postDetailStatus.put("user", userFromSession);
        postDetailStatus.put("userDetail", userDetailUpdated);
        postDetailStatus.put("message", "success");

        return postDetailStatus;
    }

}
