package cn.tgw.admin.controller;

import cn.tgw.admin.model.EasyUIDataGridResult;
import cn.tgw.admin.service.ManagerService;
import cn.tgw.admin.service.ManagerUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ManagerUserInfoController {

    // 自定义类型转换器
    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

        // System.out.println("initBinder  &&&&"+request.getParameter("hiredate")+"***"+request.getParameter("username"));

        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    ManagerUserInfoService managerUserInfoService;

    @RequestMapping("/usermanager/findAllUsers")
    public EasyUIDataGridResult findAllUsers(Integer page,Integer rows,
                                             @RequestParam(value = "phone",required = false) String phone,
                                             @RequestParam(value = "stime",required = false) Date stime,
                                             @RequestParam(value = "etime",required = false) Date etime){
        return managerUserInfoService.findAllUsers(page, rows, phone, stime, etime);
    }


}
