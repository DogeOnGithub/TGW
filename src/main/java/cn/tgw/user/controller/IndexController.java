package cn.tgw.user.controller;

import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.user.model.UserDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/test/qiniu")
    public String testQiniu(UserDetail userDetail, MultipartFile headImage) throws IOException {
        System.out.println(userDetail);
        System.out.println("origin:" + headImage.getOriginalFilename());
        System.out.println(headImage.getName());
        return QiniuUtil.tjsanshaoUploadImage(headImage);
    }
}
