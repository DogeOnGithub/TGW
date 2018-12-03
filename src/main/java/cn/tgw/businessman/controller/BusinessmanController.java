package cn.tgw.businessman.controller;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.service.BusinessmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
        Businessman businessman = businessmanService.getUserByUsernameAndPasswordAndStatus(username, password, new Byte("1"));
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

}
