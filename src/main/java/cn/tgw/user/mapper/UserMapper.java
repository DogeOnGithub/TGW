package cn.tgw.user.mapper;

import cn.tgw.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    User selectByMobile(User user);

    User selectByMobileAndPasswordAndUserStatus(User record);

    //自定义方法结束

    /**
     * 查询全部用户
     * @return
     * @param phone
     * @param stime
     * @param etime
     */
    List<User>findAllUsers(@Param("phone") String phone, @Param("stime")Date stime, @Param("etime")Date etime,@Param("userStatus")Integer userStatus);
}