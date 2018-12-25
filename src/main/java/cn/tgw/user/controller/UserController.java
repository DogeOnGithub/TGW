package cn.tgw.user.controller;

import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.goods.service.GoodsService;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    /*
     * @Description:用户登录
     * @Param:[username, password, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:17:38
     **/
    @PostMapping("/tjsanshao/user/login")
    public Map<String, Object> login(String username, String password, HttpSession session){
        HashMap<String, Object> loginStatus = new HashMap<>();

        //校验用户名和密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            loginStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input username and password");
            loginStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            return loginStatus;
        }

        //调用service，获取记录
        User user = userService.getUserByUsernameOrMobileAndPasswordAndStatus(username, password, new Byte("1"));
        if (user != null){

            //根据用户id获取userDetail，例如昵称、头像url
            UserDetail userDetail = userService.getUserDetailByUserId(user);

            loginStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            loginStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "login success");
            user.setPassword(""); //不返回密码到客户端
            loginStatus.put("user", user);

            loginStatus.put("userDetail", userDetail);

            //将记录存入session，键为"user"，值为User对象
            session.setAttribute(TGWStaticString.TGW_USER, user);

        }else{
            loginStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            loginStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "username or password error");
        }

        return loginStatus;
    }

    /*
     * @Description:用户退出登录
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-12
     * @Time:15:53
     **/
    @RequestMapping("/tjsanshao/user/logout")
    public Map<String, Object> logout(HttpSession session) {
        HashMap<String, Object> logoutStatus = new HashMap<>();

        session.setAttribute(TGWStaticString.TGW_USER, null);

        logoutStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        logoutStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        return logoutStatus;
    }

    /*
     * @Description:发送验证码，requestParam=password参数可以用于请求修改密码的验证码（根据用户登录信息发送验证码，不需要输入手机号），不带有requestParam参数时，需要填写mobileNumber参数
     * @Param:[mobileNumber, requestParam, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-30
     * @Time:10:31
     **/
    @RequestMapping("/tjsanshao/user/sendMsgCode")
    public Map<String, Object> sendMsgCode(String mobileNumber, String requestParam, HttpSession session){
        HashMap<String, Object> sendMsgStatus = new HashMap<>();

        if(requestParam != null) {
            //是否是修改密码要求的验证码
            if (requestParam.equals("password")){
                //查询session，用户是否已经登录
                Object sessionUser = session.getAttribute(TGWStaticString.TGW_USER);
                if (sessionUser == null){
                    //用户没有登录，提示用户先登录
                    sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_AUTH);
                    sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "login first");
                    return sendMsgStatus;
                }

                //用户已经登录
                User userFromSession = (User)sessionUser;

                smsVerifyService.sendMsgCodeAsync(userFromSession.getMobile(), miaoDiService.generateCode(6));

                sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
                sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

                return sendMsgStatus;
            }
        }

        //验证手机号码是否为空
        if (StringUtils.isEmpty(mobileNumber)){
            sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input correct phone number");
            return sendMsgStatus;
        }

        //校验手机号码的正则表达式
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

        if (mobileNumber.length() != 11) {
            //如果不是11位，不是手机号码，直接返回fail
            sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input correct phone number");
            return sendMsgStatus;
        } else {
            //是11位手机号码，开始用正则匹配
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobileNumber);
            boolean isMatch = m.matches();
            if (!isMatch) {
                //如果匹配不成功，不合法，返回fail
                sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
                sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input correct phone number");
                return sendMsgStatus;
            }else{
                if (!smsVerifyService.enableSend(mobileNumber)){
                    //该手机号不可以发送验证码，超出了每天发送次数
                    sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
                    sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "send times out");
                    return sendMsgStatus;
                }

                //校验成功，返回结果，使用异步的方式，发送手机验证码
                sendMsgStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
                sendMsgStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

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
    @PostMapping("/tjsanshao/user/register")
    public Map<String, Object> register(User user, String code, HttpSession session){
        HashMap<String, Object> registerStatus = new HashMap<>();

        //验证用户名、密码、手机号码是否为空
        if (user.getUsername() == null || user.getPassword() == null || StringUtils.isEmpty(user.getMobile()) || StringUtils.isEmpty(code)){
            registerStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            registerStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input the whole");
            return registerStatus;
        }

        //验证验证码是否正确
        if (!smsVerifyService.checkCode(user.getMobile(), code)){
            registerStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            registerStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "code is invalid");
            return registerStatus;
        }

        //填写了用户名、密码、手机号码、验证码，验证是否可以注册
        if (!userService.enableUserRegister(user)){
            //用户名已存在，不可注册
            registerStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            registerStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "username exists");
            return registerStatus;
        }

        if (!userService.enableMoblieRegister(user.getMobile())){
            //手机号已绑定，不可注册
            registerStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            registerStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "mobile exists");
            return registerStatus;
        }

        //存入到数据库
        userService.userRegister(user);

        registerStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        registerStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "register success");

        user.setPassword("");
        registerStatus.put("user", user);

        //注册之后直接登录
        session.setAttribute(TGWStaticString.TGW_USER, user);

        //查询到userDetail返回到前端
        UserDetail userDetail = userService.getUserDetailByUserId(user);
        registerStatus.put("userDetail", userDetail);

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
    @PostMapping("/tjsanshao/user/password")
    public Map<String, Object> password(String password, String code, String oldPassword, HttpSession session){
        HashMap<String, Object> passwordStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute(TGWStaticString.TGW_USER);

        //用户已经登录，这个session中包含有用户记录的所有信息
        User userFromSession = (User)sessionUser;

        //判断是否带有验证码，如果没有，需要发送验证码
        if (StringUtils.isEmpty(code)){
            //请求中没有验证码
            passwordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            passwordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "verify code error");
            return passwordStatus;
        }

        //请求中带有验证码，开始验证验证码
        if (!smsVerifyService.checkCode(userFromSession.getMobile(), code)){
            //验证码不通过
            passwordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            passwordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "verify code error");
            return passwordStatus;
        }

        //查询数据库中的User，验证用户名和用户输入的旧密码是否匹配
        User queryUser = userService.getUserByUsernameOrMobileAndPasswordAndStatus(userFromSession.getUsername(), oldPassword, new Byte("1"));

        if (queryUser == null){
            //没有查询到用户，即用户名和旧密码不匹配
            passwordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            passwordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "old password error");
            return passwordStatus;
        }

        //更新数据库信息
        queryUser.setPassword(password);
        queryUser.setUserStatus(new Byte("1"));
        userService.updateUserPassword(queryUser);

        //设置验证码状态为已使用
        smsVerifyService.codeUsed(userFromSession.getMobile());

        passwordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        passwordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        //清空session中的信息
        session.setAttribute(TGWStaticString.TGW_USER, null);

        return passwordStatus;
    }

    /*
     * @Description:在换绑手机时，验证输入的手机号和密码是否正确
     * @Param:[mobile, password, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-13
     * @Time:15:37
     **/
    @PostMapping("/tjsanshao/user/verifyMobile")
    public Map<String, Object> verifyMobile(String mobile, String password, HttpSession session) {
        HashMap<String, Object> verifyStatus = new HashMap<>();

        //判断字段是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            verifyStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            verifyStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "input incorrect");
            return verifyStatus;
        }

        User user = userService.getUserByUsernameOrMobileAndPasswordAndStatus(mobile, password, new Byte("1"));

        if (user == null) {
            verifyStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            verifyStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "input incorrect");
            return verifyStatus;
        }

        verifyStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        verifyStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        return verifyStatus;
    }

    /*
     * @Description:换绑手机
     * @Param:[mobile, code, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-13
     * @Time:15:48
     **/
    @RequestMapping("/tjsanshao/user/mobile")
    public Map<String, Object> changeMobile(String mobile, String code, HttpSession session) {
        HashMap<String, Object> mobileStatus = new HashMap<>();

        //验证字段是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)) {
            mobileStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            mobileStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "input incorrect");
            return mobileStatus;
        }

        //验证手机号是否被绑定过
        if (!userService.enableMoblieRegister(mobile)) {
            //如果手机号被绑定过
            mobileStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            mobileStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "mobile is exists");
            return mobileStatus;
        }

        //验证验证码是否正确
        if (!smsVerifyService.checkCode(mobile, code)) {
            mobileStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            mobileStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "verify code incorrect");
            return mobileStatus;
        }

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        User updatedUser = userService.updateUserMobile(user, mobile);

        //更新session
        session.setAttribute(TGWStaticString.TGW_USER, updatedUser);
        smsVerifyService.codeUsed(updatedUser.getMobile());

        //返回结果
        mobileStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        mobileStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        updatedUser.setPassword("");
        mobileStatus.put(TGWStaticString.TGW_USER, updatedUser);

        return mobileStatus;
    }

    /*
     * @Description:用户选择忘记密码，直接根据用户名发送验证码
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:14:50
     **/
    @RequestMapping("/tjsanshao/user/forgot")
    public Map<String, Object> forgotPassword(String username) {
        HashMap<String, Object> forgotStatus = new HashMap<>();

        //如果没有输入用户名
        if (StringUtils.isEmpty(username)) {
            forgotStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            forgotStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input the whole");
            return forgotStatus;
        }

        //根据username查询数据库，username的传入可能是user的属性username或者mobile
        User user = userService.getUserByUsername(username);

        if (user == null) {
            forgotStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            forgotStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "user is not available");
            return forgotStatus;
        }

        //用户名有效，向用户手机发送验证码
        smsVerifyService.sendMsgCodeAsync(user.getMobile(), miaoDiService.generateCode(6));

        forgotStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        forgotStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");
        return forgotStatus;
    }

    /*
     * @Description:忘记密码后，新密码
     * @Param:[username, password, code]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-13
     * @Time:09:42
     **/
    @PostMapping("/tjsanshao/user/newpwd")
    public Map<String, Object> newPassword(String username, String password, String code) {
        HashMap<String, Object> newPasswordStatus = new HashMap<>();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "please input the whole");
            return newPasswordStatus;
        }

        User user = userService.getUserByUsername(username);

        if (user == null) {
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "user is not available");
            return newPasswordStatus;
        }

        //验证码错误
        if (!smsVerifyService.checkCode(user.getMobile(), code)) {
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            newPasswordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "code is not available");
            return newPasswordStatus;
        }

        //验证码正确，用户名正确，更新数据库
        user.setPassword(password);
        user.setUserStatus(new Byte("1"));

        userService.updateUserPassword(user);

        smsVerifyService.codeUsed(user.getMobile());

        newPasswordStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        newPasswordStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");
        return newPasswordStatus;
    }

    /*
     * @Description:使用get请求，返回当前登录用户的用户详细信息，需要用户登录
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-11-30
     * @Time:10:36
     **/
    @GetMapping("/tjsanshao/user/detail")
    public Map<String, Object> userDetail(HttpSession session){
        HashMap<String, Object> getDetailStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute(TGWStaticString.TGW_USER);

        User userFromSession = (User)sessionUser;

        UserDetail userDetail = userService.getUserDetailByUserId(userFromSession);

        getDetailStatus.put(TGWStaticString.TGW_RESULT_STATUS, "success");
        getDetailStatus.put("user", userFromSession);
        getDetailStatus.put("userDetail", userDetail);
        getDetailStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        return getDetailStatus;
    }

    /*
     * @Description:修改用户信息
     * @Param:[userDetail, headImage, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-13
     * @Time:09:20
     **/
    @PostMapping("/tjsanshao/user/detail")
    public Map<String, Object> userDetail(UserDetail userDetail, MultipartFile headImage, HttpSession session) throws IOException {
        HashMap<String, Object> postDetailStatus = new HashMap<>();

        //验证用户登录已使用过滤器，详细请查看cn.tgw.user.filter.UserAuthenticationFilter

        //从session中获取用户信息
        Object sessionUser = session.getAttribute(TGWStaticString.TGW_USER);

        User userFromSession = (User)sessionUser;
        userDetail.setTgwUserId(userFromSession.getId());
        userDetail.setLastUpdateTime(new Date());
        userDetail.setRegTime(null);
        userDetail.setMobile(null);

        //判断用户是否上传了头像图片
        if (headImage != null) {

            //将图片上传到七牛云
            String qiNiuRes = QiniuUtil.tjsanshaoUploadImage(headImage);
            if (!qiNiuRes.equals("error")) {
                //如果图片上传到七牛云成功，将返回的url存入到UserDetail记录中
                userDetail.setUserImageUrl(qiNiuRes);
            }
        }

        //更新数据库，并获得更新后的UserDetail
        UserDetail userDetailUpdated = userService.updateUserDetail(userDetail);

        postDetailStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        userFromSession.setPassword("");
        postDetailStatus.put("user", userFromSession);
        postDetailStatus.put("userDetail", userDetailUpdated);
        postDetailStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");

        return postDetailStatus;
    }

    /*
     * @Description:获取用户的所有订单
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-10
     * @Time:17:39
     **/
    @RequestMapping("/tjsanshao/user/orders")
    public Map<String, Object> allOrders(HttpSession session) {
        HashMap<String, Object> allOrdersStatus = new HashMap<>();

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        List<Order> allOrders = orderService.getUserAllOrders(user);

        allOrdersStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        allOrdersStatus.put("orders", dealWithOrderResult(allOrders));

        return allOrdersStatus;
    }

    /*
     * @Description:获取用户的未付款订单
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-10
     * @Time:17:39
     **/
    @RequestMapping("/tjsanshao/user/ordersNotPay")
    public Map<String, Object> ordersWaitForPay(HttpSession session) {
        HashMap<String, Object> ordersWaitForPayStatus = new HashMap<>();

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        //0表示待付款
        List<Order> allOrders = orderService.getOrdersByUserAndOrderSellStatusAndStatusNormal(user, new Byte("0"));

        ordersWaitForPayStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        ordersWaitForPayStatus.put("orders", dealWithOrderResult(allOrders));

        return ordersWaitForPayStatus;
    }

    /*
     * @Description:获取用户的已付款的未使用的订单
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:14:40
     **/
    @RequestMapping("/tjsanshao/user/ordersNotUse")
    public Map<String, Object> ordersWaitForUse(HttpSession session) {
        HashMap<String, Object> ordersWaitForUseStatus = new HashMap<>();

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        //1表示已付款，未使用
        List<Order> allOrders = orderService.getOrdersByUserAndOrderSellStatusAndStatusNormal(user, new Byte("1"));

        ordersWaitForUseStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        ordersWaitForUseStatus.put("orders", dealWithOrderResult(allOrders));

        return ordersWaitForUseStatus;
    }

    /*
     * @Description:获取用户未评价的订单
     * @Param:[session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:08:30
     **/
    @RequestMapping("/tjsanshao/user/ordersNotComment")
    public Map<String, Object> ordersWaitForComment(HttpSession session) {
        HashMap<String, Object> ordersWaitForCommentStatus = new HashMap<>();

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        //3表示已使用
        List<Order> allOrders = orderService.getOrdersByUserAndOrderSellStatusAndStatusNormal(user, new Byte("3"));

        ordersWaitForCommentStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        ordersWaitForCommentStatus.put("orders", dealWithOrderResult(allOrders));

        return ordersWaitForCommentStatus;
    }

    /*
     * @Description:用户删除订单
     * @Param:[id, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-11
     * @Time:08:41
     **/
    @RequestMapping("/tjsanshao/user/deleteOrder")
    public Map<String, Object> deleteOrder(int id, HttpSession session) {
        HashMap<String, Object> deleteOrderStatus = new HashMap<>();

        User user = (User)session.getAttribute(TGWStaticString.TGW_USER);

        if (orderService.deleteByOrderId(id)) {
            deleteOrderStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            deleteOrderStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "success");
        } else {
            deleteOrderStatus.put(TGWStaticString.TGW_RESULT_STATUS, TGWStaticString.TGW_RESULT_STATUS_FAIL);
            deleteOrderStatus.put(TGWStaticString.TGW_RESULT_MESSAGE, "Unknown Error");
        }

        return deleteOrderStatus;
    }

    /*
     * @Description:处理订单的返回数据
     * @Param:[orderList]
     * @Return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author:TjSanshao
     * @Date:2018-12-17
     * @Time:10:02
     **/
    private List<Map<String, Object>> dealWithOrderResult(List<Order> orderList) {

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = (orderList.size() - 1); i >= 0; i--) {
            Map<String, Object> tempMap = new HashMap<>();

            Order order = orderList.get(i);
            Map<String, Object> goodsAndGoodsDetailAndGoodsImage = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(order.getTgwGoodsId());
            Goods goods = (Goods)goodsAndGoodsDetailAndGoodsImage.get("goods");
            GoodsDetail goodsDetail = (GoodsDetail)goodsAndGoodsDetailAndGoodsImage.get("goodsDetail");
            GoodsImage goodsImage = (GoodsImage)goodsAndGoodsDetailAndGoodsImage.get("goodsImage");

            String sellStatusToZH = changeSellStatusToZH(order.getSellStatus().intValue());

            tempMap.put("order", order);
            tempMap.put("goods", goods);
            tempMap.put("goodsDetail", goodsDetail);
            tempMap.put("goodsImage", goodsImage);
            tempMap.put("sellStatus", sellStatusToZH);

            result.add(tempMap);
        }

        return result;
    }

    private String changeSellStatusToZH(int status) {
        switch (status) {
            case 0:
                return "未付款";
            case 1:
                return "待使用";
            case 3:
                return "待评价";
            case 4:
                return "已完成";
            case 5:
                return "已过期";
            case 2:
                return "退款";
            default:
                return "未知";
        }
    }

}
