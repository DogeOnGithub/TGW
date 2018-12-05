package cn.tgw.businessman.mapper;

import cn.tgw.businessman.model.Businessman;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessmanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Businessman record);

    int insertSelective(Businessman record);

    Businessman selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Businessman record);

    int updateByPrimaryKey(Businessman record);

    //自定义方法开始

    Businessman selectByUsernameAndPasswordAndStatus(Businessman record);

    Businessman selectByUsername(Businessman record);

    Businessman selectByMobileAndPasswordAndStatus(Businessman record);

    Businessman selectByMobile(Businessman record);

    //自定义方法结束

    List<Businessman> findBusinessmansByLikeMobile(@Param("mobile") String mobile);

}