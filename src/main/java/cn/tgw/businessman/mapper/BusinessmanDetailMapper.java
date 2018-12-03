package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.BusinessmanDetail;

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

    //自定义方法结束
}