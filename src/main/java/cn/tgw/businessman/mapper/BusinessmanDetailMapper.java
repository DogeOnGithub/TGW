package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.BusinessmanDetail;

public interface BusinessmanDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessmanDetail record);

    int insertSelective(BusinessmanDetail record);

    BusinessmanDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessmanDetail record);

    int updateByPrimaryKey(BusinessmanDetail record);
}