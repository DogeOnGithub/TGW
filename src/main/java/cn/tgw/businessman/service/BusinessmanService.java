package cn.tgw.businessman.service;

import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @Project:tgw
 * @Description:businessman service
 * @Author:TjSanshao
 * @Create:2018-12-03 10:19
 *
 **/
public interface BusinessmanService {

    Businessman getBusinessmanByUsernameAndPasswordAndStatus(String username, String password, Byte status);

    BusinessmanDetail getBusinessmanDetailByBusinessmanId(Businessman businessman);

    List<BusinessmanDetail> getBusinessmanDetailWaitForReview();

    List<BusinessmanDetail> getAllBusinessmanDetail();

    List<BusinessmanDetail> getBusinessmanDetailNormalStatus();

    List<BusinessmanDetail> getBusinessmanDetailFailForReview();

}
