package cn.tgw.admin.controller;

import cn.tgw.common.utils.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UploadImgs {

    @RequestMapping("/zhifa/upload")
    @ResponseBody
    public Object getImg(MultipartFile[] image) {
        Map<String,Object> params=new HashMap<>();
        List<String>list=new ArrayList<>();
        try {
            for (int i=0;i<image.length;i++){
                String uploadImgUrl = QiniuUtil.uploadImg(image[i]);
                //
                list.add(uploadImgUrl);
            }
            params.put("errno",0);
            params.put("data",list);
        } catch (IOException e) {
            params.put("errno", 1);
            e.printStackTrace();
        }
        return params;
    }
}
