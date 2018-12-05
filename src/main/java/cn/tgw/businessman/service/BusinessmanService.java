package cn.tgw.businessman.service;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * @Project:tgw
 * @Description:businessman service
 * @Author:TjSanshao
 * @Create:2018-12-03 10:19
 *
 **/
public interface BusinessmanService {

    Businessman getBusinessmanByUsernameOrMobileAndPasswordAndStatus(String username, String password, Byte status);

    BusinessmanDetail getBusinessmanDetailByBusinessmanId(Businessman businessman);

    List<BusinessmanDetail> getBusinessmanDetailWaitForReview();

    List<BusinessmanDetail> getAllBusinessmanDetail();

    List<BusinessmanDetail> getBusinessmanDetailNormalStatus();

    List<BusinessmanDetail> getBusinessmanDetailFailForReview();

    boolean enableBusinessmanRegister(Businessman businessman);

    boolean businessmanRegister(Businessman businessman);

    PageInfo<Businessman> findBusinessmansByLikeMobile(Integer page, Integer rows, String mobile);

    PageInfo<BusinessmanDetail> findAllDetailsByBusinessmanId(Integer page, Integer rows, Integer id);

    PageInfo<BusinessmanDetail> findAllAppliDetails(Integer page, Integer rows,Byte status);

    int changeShop_settle_statusById(Integer id,Byte status);

}
