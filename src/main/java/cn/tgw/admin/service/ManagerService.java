package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwManagerMapper;
import cn.tgw.admin.model.TgwManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Autowired
    TgwManagerMapper tgwManagerMapper;

    public TgwManager isLogin(String tgwManagerName,String managerPassword){
        return tgwManagerMapper.findTgwManagerByNameAndPassword(tgwManagerName, managerPassword);
    }

}
