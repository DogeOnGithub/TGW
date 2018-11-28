package cn.tgw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project:tgw
 * @Description:user module user controller
 * @Author:TjSanshao
 * @Create:2018-11-26 16:33
 *
 **/

@RestController
public class IndexController {

    @GetMapping("/user/index")
    public String index(){
        return "User Index";
    }
}
