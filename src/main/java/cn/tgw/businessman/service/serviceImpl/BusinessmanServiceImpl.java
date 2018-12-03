package cn.tgw.businessman.service.serviceImpl;

import cn.tgw.businessman.mapper.BusinessmanDetailMapper;
import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /*
     * @Description:返回所有待审核入驻的商家详细信息
     * @Param:[]
     * @Return:java.util.List<cn.tgw.businessman.model.BusinessmanDetail>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:15:59
     **/
    @Override
    public List<BusinessmanDetail> getBusinessmanDetailWaitForReview() {
        return businessmanDetailMapper.selectBusinessmanByShopSettleStatus(new Byte("0"));
    }

    /*
     * @Description:获取所有的商家详细信息
     * @Param:[]
     * @Return:java.util.List<cn.tgw.businessman.model.BusinessmanDetail>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:16:08
     **/
    @Override
    public List<BusinessmanDetail> getAllBusinessmanDetail() {
        return businessmanDetailMapper.selectAllBusinessmanDetail();
    }

    /*
     * @Description:获取所有的出于正常状态的商家的详细信息，即状态为1
     * @Param:[]
     * @Return:java.util.List<cn.tgw.businessman.model.BusinessmanDetail>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:16:08
     **/
    @Override
    public List<BusinessmanDetail> getBusinessmanDetailNormalStatus() {
        return businessmanDetailMapper.selectBusinessmanByShopSettleStatus(new Byte("1"));
    }

    /*
     * @Description:获取所有的未通过入驻审核的商家的详细信息，即状态为2
     * @Param:[]
     * @Return:java.util.List<cn.tgw.businessman.model.BusinessmanDetail>
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:16:09
     **/
    @Override
    public List<BusinessmanDetail> getBusinessmanDetailFailForReview() {
        return businessmanDetailMapper.selectBusinessmanByShopSettleStatus(new Byte("2"));
    }
}
