package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.BusinessmanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessmanDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessmanDetail record);

    int insertSelective(BusinessmanDetail record);

    BusinessmanDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessmanDetail record);

    int updateByPrimaryKey(BusinessmanDetail record);

    //自定义方法开始

    BusinessmanDetail selectByBusinessmanId(Integer businessmanId);

    List<BusinessmanDetail> selectBusinessmanByShopSettleStatus(Byte status);

    List<BusinessmanDetail> selectAllBusinessmanDetail();

    BusinessmanDetail selectByContactPhone(String contactPhone);

    //自定义方法结束

    List<BusinessmanDetail> findAllDetailsByBusinessmanId(@Param("tgw_businessman_id") Integer tgw_businessman_id);
}