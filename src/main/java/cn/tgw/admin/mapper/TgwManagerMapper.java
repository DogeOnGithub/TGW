package cn.tgw.admin.mapper;

import cn.tgw.admin.model.TgwManager;
import org.apache.ibatis.annotations.Param;

public interface TgwManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TgwManager record);

    int insertSelective(TgwManager record);

    TgwManager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TgwManager record);

    int updateByPrimaryKey(TgwManager record);

    TgwManager findTgwManagerByNameAndPassword(@Param("tgwManagerName") String tgwManagerName, @Param("managerPassword") String managerPassword);
}