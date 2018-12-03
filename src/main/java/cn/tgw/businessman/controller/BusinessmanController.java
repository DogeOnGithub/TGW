package cn.tgw.businessman.controller;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import cn.tgw.common.service.MiaoDiService;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.common.utils.TGWStaticString;
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
    @GetMapping("/tjsanshao/businessman/login")
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

            //将记录存入session，键为"businessman"，值为businessman对象
            session.setAttribute(TGWStaticString.TGW_BUSINESSMAN, businessman);
        }else {
            loginStatus.put("status", "fail");
            loginStatus.put("message", "username or password error");
        }

        return loginStatus;
    }

    /*
     * @Description:商家发送验证码功能
     * @Param:[mobileNumber, requestParam, session]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:16:28
     **/
    @GetMapping("/tjsanshao/businessman/sendMsgCode")
    public Map<String, Object> sendMsgCode(String mobileNumber, String requestParam, HttpSession session) {
        HashMap<String, Object> sendMsgStatus = new HashMap<>();

        if (requestParam != null) {
            //是否是修改密码要求的验证码
            if (requestParam.equals("password")) {
                //查询session，用户是否已经登录
                Object sessionBusinessman = session.getAttribute(TGWStaticString.TGW_BUSINESSMAN);
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
                smsVerifyService.sendMsgCodeAsync(businessmanDetail.getContactPhoneNumber(), miaoDiService.generateCode(6));

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

    /*
     * @Description:商家注册
     * @Param:[businessman, businessmanDetail, code]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:16:31
     **/
    @GetMapping("/tjsanshao/businessman/register")
    public Map<String, Object> register(Businessman businessman, BusinessmanDetail businessmanDetail, String code) {
        HashMap<String, Object> registerStatus = new HashMap<>();

        //验证用户名、密码、手机号码是否为空
        if (businessman.getUsername() == null || businessman.getPassword() == null || StringUtils.isEmpty(businessmanDetail.getContactPhoneNumber()) || StringUtils.isEmpty(code)) {
            registerStatus.put("status", "fail");
            registerStatus.put("message", "please input the whole");
            return registerStatus;
        }

        //验证验证码是否正确
        if (!smsVerifyService.checkCode(businessmanDetail.getContactPhoneNumber(), code)){
            registerStatus.put("status", "fail");
            registerStatus.put("message", "code is invalid");
            return registerStatus;
        }

        //填写了用户名、密码、验证码，验证是否可以注册
        if (!businessmanService.enableBusinessmanRegister(businessman)){
            //用户名已存在，不可注册
            registerStatus.put("status", "fail");
            registerStatus.put("message", "username exists");
            return registerStatus;
        }

        //存入到数据库
        businessmanService.businessmanRegister(businessman, businessmanDetail);

        registerStatus.put("status", "success");
        registerStatus.put("message", "register success");

        businessman.setPassword("");
        businessmanDetail = businessmanService.getBusinessmanDetailByBusinessmanId(businessman);
        registerStatus.put("businessman", businessman);
        registerStatus.put("businessmanDetail", businessmanDetail);

        return registerStatus;
    }
}
