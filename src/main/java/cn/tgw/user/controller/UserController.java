package cn.tgw.user.controller;

import cn.tgw.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:tgw
 * @descrption:user controller
 * @author:TjSanshao
 * @create:2018-11-26 17:28
 **/

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, String> login(@NotNull String username, @NotNull String password, Byte status){
        HashMap<String, String> loginStatus = new HashMap<>();

        if (userService.getUserByUsernameAndPasswordAndStatus(username, password, new Byte("1")) != null){
            loginStatus.put("status", "success");
            loginStatus.put("message", "login success");
        }else{
            loginStatus.put("status", "fail");
            loginStatus.put("message", "username or password error");
        }

        return loginStatus;
    }

}
