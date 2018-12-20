package cn.tgw.user.mapper;

import cn.tgw.user.model.UserDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int updateByUserIdSelective(UserDetail record);

    //自定义查询结束


    int updateUserImgs(@Param("userImageUrl") String userImageUrl,@Param("id") Integer id);

    List<UserDetail>findAllUserDetail();
}