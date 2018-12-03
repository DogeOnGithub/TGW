package cn.tgw.admin.service;

import cn.tgw.admin.model.EasyUIDataGridResult;
import cn.tgw.admin.model.UserMapperPo;
import cn.tgw.user.mapper.UserDetailMapper;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagerUserInfoService {



    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDetailMapper userDetailMapper;


    public EasyUIDataGridResult findAllUsers(Integer page,Integer rows,String phone,Date stime, Date etime){
        EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
        PageHelper.startPage(page,rows);
        if (phone!=null){
            phone="%"+phone+"%";
        }
        List<User> allUsers = userMapper.findAllUsers(phone,stime,etime);
        PageInfo<User>pageInfo=new PageInfo<>(allUsers);
        List<User> list = pageInfo.getList();
        List<UserMapperPo>allUserMapperPo=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UserMapperPo userMapperPo=new UserMapperPo();
            User user = list.get(i);
            userMapperPo.setId(user.getId());
            userMapperPo.setUsername(user.getUsername());
            userMapperPo.setPassword(user.getPassword());
            userMapperPo.setUserStatus(user.getUserStatus());
            UserDetail userDetail = userDetailMapper.selectByUserId(user.getId());
            userMapperPo.setEmail(userDetail.getEmail());
            userMapperPo.setMobile(userDetail.getMobile());
            userMapperPo.setSex(userDetail.getSex());
            userMapperPo.setRegTime(userDetail.getRegTime());
            userMapperPo.setLastUpdateTime(userDetail.getLastUpdateTime());
            userMapperPo.setNickName(userDetail.getNickName());
            userMapperPo.setUserImageUrl(userDetail.getUserImageUrl());
            allUserMapperPo.add(userMapperPo);

        }
        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        easyUIDataGridResult.setRows(allUserMapperPo);
        return easyUIDataGridResult;
    }
}
