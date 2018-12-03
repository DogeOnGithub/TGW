package cn.tgw.businessman.controller;

/*
 * @Project:tgw
 * @Description:test
 * @Author:TjSanshao
 * @Create:2018-12-03 09:53
 *
 **/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessmanTestController {

    @GetMapping("/businessman/test")
    public String test(){
        return "test";
    }

}
