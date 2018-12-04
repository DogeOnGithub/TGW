package cn.tgw.admin.mapper;

import cn.tgw.admin.model.TgwFirstCategory;

import java.util.List;

public interface TgwFirstCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TgwFirstCategory record);

    int insertSelective(TgwFirstCategory record);

    TgwFirstCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TgwFirstCategory record);

    int updateByPrimaryKey(TgwFirstCategory record);

    List<TgwFirstCategory> allTgwFirstCategory();
}