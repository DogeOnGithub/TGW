package cn.tgw.user.mapper;

import cn.tgw.user.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //自定义方法开始

    User selectByUsernameAndPasswordAndUserStatus(User record);

    User selectByUsername(User user);

    //自定义方法结束
}