package cn.tgw.businessman.service.serviceImpl;

import cn.tgw.businessman.mapper.BusinessmanDetailMapper;
import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Project:tgw
 * @Description:businessman service
 * @Author:TjSanshao
 * @Create:2018-12-03 10:25
 *
 **/
@Service
public class BusinessmanServiceImpl implements BusinessmanService
{
    @Autowired
    private BusinessmanMapper businessmanMapper;

    @Autowired
    private BusinessmanDetailMapper businessmanDetailMapper;

    /*
     * @Description:根据用户名、密码、状态查询商家用户
     * @Param:[username, password, status]
     * @Return:cn.tgw.businessman.model.Businessman
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:10:34
     **/
    @Override
    public Businessman getBusinessmanByUsernameAndPasswordAndStatus(String username, String password, Byte status) {

        Businessman businessman = new Businessman();
        businessman.setUsername(username);
        businessman.setPassword(password);
        businessman.setStatus(status);

        return businessmanMapper.selectByUsernameAndPasswordAndStatus(businessman);
    }

    /*
     * @Description:根据businessman的Id获取businessmanDetail,传入的是businessman对象，该对象必须持有id
     * @Param:[businessman]
     * @Return:cn.tgw.businessman.model.BusinessmanDetail
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:11:14
     **/
    @Override
    public BusinessmanDetail getBusinessmanDetailByBusinessmanId(Businessman businessman) {
        return businessmanDetailMapper.selectByBusinessmanId(businessman.getId());
    }
}
