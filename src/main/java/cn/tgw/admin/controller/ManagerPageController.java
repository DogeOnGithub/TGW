package cn.tgw.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManagerPageController {

    @RequestMapping("/manager_main_page")
    public String ToManager_main(){
        return "managerPage/manager_main";
    }


    @RequestMapping("/manager_login_page")
    public String manager_login(){
        return "managerPage/manager_login";
    }


    @RequestMapping("/shangjiaxinxi")
    public String shangjiaxinxi(){
        return "managerPage/shangjiaxinxi";
    }


    @RequestMapping("/shangjiaruzhuguanli")
    public String shangjiaruzhuguanli(){
        return "managerPage/shangjiaruzhuguanli";
    }

    @RequestMapping("/yonghuxinxiguanli")
    public String yonghuxinxiguanli(){
        return "managerPage/yonghuxinxiguanli";
    }

    @RequestMapping("/shangpinfenleiguanli")
    public String shangpinfenleiguanli(){ return "managerPage/shangpinfenleiguanli"; }

}
