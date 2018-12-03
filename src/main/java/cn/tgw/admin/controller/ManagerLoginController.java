package cn.tgw.admin.controller;

import cn.tgw.admin.mapper.TgwManagerMapper;
import cn.tgw.admin.model.TgwManager;
import cn.tgw.admin.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ManagerLoginController {


    @Autowired
    ManagerService managerService;

    @RequestMapping("/manager_login")
    public Object manager_login(String tgwManagerName, String managerPassword, HttpSession httpSession){
        TgwManager manager = managerService.isLogin(tgwManagerName, managerPassword);
        Map<String,Object>map=new HashMap<>();
        if (manager!=null){
            httpSession.setAttribute("manager",manager);
            map.put("status",true);
            map.put("url","manager_main_page");
            return map;
        }
        map.put("status",false);
        map.put("msg","账号或者密码有错！请检查！");
        return map;
    }
}
