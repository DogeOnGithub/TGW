package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.Businessman;

public interface BusinessmanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Businessman record);

    int insertSelective(Businessman record);

    Businessman selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Businessman record);

    int updateByPrimaryKey(Businessman record);

    //自定义方法开始

    Businessman selectByUsernameAndPasswordAndStatus(Businessman record);

    //自定义方法结束

}