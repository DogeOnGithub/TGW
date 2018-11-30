package cn.tgw.user.mapper;

import cn.tgw.user.model.UserDetail;

public interface UserDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);

    //自定义查询开始

    UserDetail selectByMobile(String mobile);

    UserDetail selectByUserId(Integer userId);

    //自定义查询结束
}