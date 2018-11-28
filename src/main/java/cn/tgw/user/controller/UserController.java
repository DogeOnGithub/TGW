package cn.tgw.user.controller;

import cn.tgw.user.model.User;
import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/*
 * @Project:tgw
 * @Description:user controller
 * @Author:TjSanshao
 * @Create:2018-11-26 17:28
 *
 **/

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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

        //TODO 验证手机号码是否正确



        return sendMsgStatus;
    }

}
