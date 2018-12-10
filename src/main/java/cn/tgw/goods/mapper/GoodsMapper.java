package cn.tgw.goods.mapper;

import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsVO> findIndexGoodsByCityAndTypeAndFirstCategoryName(Map<String,Object> param);

    List<GoodsVO> findAllGoodsVO();

}