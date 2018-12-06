package cn.tgw.businessman.service.serviceImpl;

import cn.tgw.businessman.mapper.BusinessmanDetailMapper;
import cn.tgw.businessman.mapper.BusinessmanMapper;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SmsVerifyMapper smsVerifyMapper;

    /*
     * @Description:根据用户名和密码或者根据手机号和密码查询用户
     * @Param:[username, password, status]
     * @Return:cn.tgw.businessman.model.Businessman
     * @Author:TjSanshao
     * @Date:2018-12-04
     * @Time:09:11
     **/
    @Override
    public Businessman getBusinessmanByUsernameOrMobileAndPasswordAndStatus(String username, String password, Byte status) {

        //调用方传入的username参数有可能是用户名，也有可能是手机号，因此要做两次查询

        Businessman businessman = new Businessman();
        businessman.setUsername(username);
        businessman.setPassword(password);
        businessman.setStatus(status);

        businessman.setMobile(username);   //username可能是手机号

        //根据用户名和密码查询用户
        Businessman businessmanByUsername = businessmanMapper.selectByUsernameAndPasswordAndStatus(businessman);

        //如果根据用户名和密码查询不到记录，则返回根据mobile和密码查询到的记录，如果均没有，返回null
        return businessmanByUsername == null ? businessmanMapper.selectByMobileAndPasswordAndStatus(businessman) : businessmanByUsername;
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

    /*
     * @Description:验证用户名是否可以注册
     * @Param:[businessman]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-12-03
     * @Time:17:16
     **/
    @Override
    public boolean enableBusinessmanRegister(Businessman businessman) {
        //判断用户名是否存在
        if (businessmanMapper.selectByUsername(businessman) != null) {
            //用户名已存在
            return false;
        }

        //判断手机号是否已经绑定
        if (businessmanMapper.selectByMobile(businessman) != null) {
            //手机号已绑定
            return false;
        }

        //用户名未注册，手机号未绑定
        return true;
    }

    /*
     * @Description:商家注册
     * @Param:[businessman]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-12-04
     * @Time:09:34
     **/
    @Override
    @Transactional
    public boolean businessmanRegister(Businessman businessman) {
        businessman.setStatus(new Byte("1"));
        businessmanMapper.insertSelective(businessman);

        //更新验证码状态
        SmsVerify smsVerify = new SmsVerify();
        smsVerify.setMobile(businessman.getMobile());
        smsVerify.setStatus(new Byte("1"));
        smsVerifyMapper.updateCodeStatusSmsVerify(smsVerify);

        return true;
    }

    @Override
    public Businessman updateBusinessmanPassword(Businessman businessman) {
        businessmanMapper.updateByPrimaryKeySelective(businessman);
        return businessmanMapper.selectByPrimaryKey(businessman.getId());
    }

    /**
    * @Description:    根据手机模糊搜索商家信息
    * @Author:         梁智发
    * @CreateDate:     2018/12/5 0005 8:56
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/5 0005 8:56
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @Override
    public PageInfo<Businessman> findBusinessmansByLikeMobile(Integer page, Integer rows, String mobile) {
        PageHelper.startPage(page,rows);
        List<Businessman> businessmansByLikeMobile = businessmanMapper.findBusinessmansByLikeMobile(mobile);
        PageInfo<Businessman>pageInfo=new PageInfo<>(businessmansByLikeMobile);
        return pageInfo;
    }

    /**
    * @Description:    根据商家id、查询商户所有店铺信息，Integer id可选
    * @Author:         梁智发
    * @CreateDate:     2018/12/5 0005 14:46
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/5 0005 14:46
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @Override
    public PageInfo<BusinessmanDetail> findAllDetailsByBusinessmanId(Integer page, Integer rows, Integer id) {
        PageHelper.startPage(page,rows);
        List<BusinessmanDetail> allDetailsByBusinessmanId = businessmanDetailMapper.findAllDetailsByBusinessmanId(id);
        PageInfo<BusinessmanDetail> pageInfo=new PageInfo<>(allDetailsByBusinessmanId);
        return pageInfo;
    }

    /**
    * @Description:    按照条件查询商家详情，Byte status 可选，默认是正在审核
    * @Author:         梁智发
    * @CreateDate:     2018/12/5 0005 18:52
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/5 0005 18:52
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @Override
    public PageInfo<BusinessmanDetail> findAllAppliDetails(Integer page, Integer rows, Byte status) {
        PageHelper.startPage(page,rows);
        List<BusinessmanDetail> allAppliDetails = businessmanDetailMapper.findAllAppliDetails(status);
        PageInfo<BusinessmanDetail> pageInfo=new PageInfo<>(allAppliDetails);
        return pageInfo;
    }

    @Override
    public int changeShop_settle_statusById(Integer id, Byte status) {
        int i = businessmanDetailMapper.changeShop_settle_statusById(id, status);
        return i;
    }

}
