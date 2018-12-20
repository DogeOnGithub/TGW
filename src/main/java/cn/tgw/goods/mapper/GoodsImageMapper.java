package cn.tgw.goods.mapper;

import cn.tgw.goods.model.GoodsImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsImage record);

    int insertSelective(GoodsImage record);

    GoodsImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsImage record);

    int updateByPrimaryKey(GoodsImage record);

    GoodsImage selectByTgwGoodsId(Integer tgwGoodsId);

    int updateGoodsImgs(@Param("imageUrl") String imageUrl,@Param("id") Integer id);

    List<GoodsImage>findAllGoodsImage();
}