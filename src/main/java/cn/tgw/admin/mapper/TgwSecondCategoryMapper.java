package cn.tgw.admin.mapper;

import cn.tgw.admin.model.TgwSecondCategory;

import java.util.List;

public interface TgwSecondCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TgwSecondCategory record);

    int insertSelective(TgwSecondCategory record);

    TgwSecondCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TgwSecondCategory record);

    int updateByPrimaryKey(TgwSecondCategory record);

    List<TgwSecondCategory> findtgwSecondCategory(Integer first_category_id);
}