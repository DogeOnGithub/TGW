package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.BusinessmanDetail;

public interface BusinessmanDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessmanDetail record);

    int insertSelective(BusinessmanDetail record);

    BusinessmanDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessmanDetail record);

    int updateByPrimaryKey(BusinessmanDetail record);

    //自定义方法开始

    BusinessmanDetail selectByBusinessmanId(Integer businessmanId);

    //自定义方法结束
}