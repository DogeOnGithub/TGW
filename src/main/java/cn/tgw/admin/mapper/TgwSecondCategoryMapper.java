package cn.tgw.admin.mapper;

import cn.tgw.admin.model.TgwSecondCategory;

public interface TgwSecondCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TgwSecondCategory record);

    int insertSelective(TgwSecondCategory record);

    TgwSecondCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TgwSecondCategory record);

    int updateByPrimaryKey(TgwSecondCategory record);
}