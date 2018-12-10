package cn.tgw.admin.controller;

import cn.tgw.admin.model.EasyUIDataGridResult;
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
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 条件可选查询
     * @param page 分页起始页
     * @param rows 行数
     * @param phone 手机号  模糊搜索
     * @param stime  时间范围查询 开始时间
     * @param etime  时间范围查询 结束时间
     * @param userStatus  用户状态出巡
     * @return
     */
    @RequestMapping("/usermanager/findAllUsers")
    public EasyUIDataGridResult findAllUsers(Integer page,Integer rows,
                                             @RequestParam(value = "phone",required = false) String phone,
                                             @RequestParam(value = "stime",required = false) Date stime,
                                             @RequestParam(value = "etime",required = false) Date etime,
                                             @RequestParam(value = "userStatus",required = false) Integer userStatus){
        return managerUserInfoService.findAllUsers(page, rows, phone, stime, etime,userStatus);
    }

    @RequestMapping("/modify_user_status")
    public Object modify_user_status(Integer id){

        int i = managerUserInfoService.modify_user_status(id);
        Map<String,Object> map=new HashMap<>();
        if (i>0){
            map.put("status",true);
            return map;
        }
        map.put("status",false);
        return map;

    }


}
