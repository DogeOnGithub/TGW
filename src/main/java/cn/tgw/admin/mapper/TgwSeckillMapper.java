package cn.tgw.admin.mapper;
import cn.tgw.admin.model.TgwSeckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TgwSeckillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TgwSeckill record);

    int insertSelective(TgwSeckill record);

    TgwSeckill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TgwSeckill record);

    int updateByPrimaryKey(TgwSeckill record);

    List<TgwSeckill> findGoodsIsKilling(@Param("nowTime") Date nowTime);

    List<TgwSeckill>findGoodspreparationKilling(@Param("nowTime") Date nowTime);

    int updaterepertory(Integer id);

    TgwSeckill findTgwSeckillBygoodsIdAndNowTime(@Param("tgw_goods_id") Integer tgw_goods_id,@Param("nowTime") Date nowTime);
}