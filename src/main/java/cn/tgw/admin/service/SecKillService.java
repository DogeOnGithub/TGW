package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSeckillMapper;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SecKillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    TgwSeckillMapper tgwSeckillMapper;


    public List<Map<String, Object>> findGoodsIsKilling(){
        List<TgwSeckill> goodsIsKilling = tgwSeckillMapper.findGoodsIsKilling(new Date());
        List<Map<String, Object>> goodsInfo=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < goodsIsKilling.size(); i++) {
            TgwSeckill seckill = goodsIsKilling.get(i);
            Integer seckillId = seckill.getId();
            Integer tgwGoodsId = seckill.getTgwGoodsId();
            Map<String, Object> map = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(tgwGoodsId);
            String md5Url = MD5Utils.tgwMD5(seckillId + "");
            map.put("seckill",seckill);
            map.put("status","正在秒杀");
            map.put("url","/seckill/"+seckillId+"/"+md5Url);
            goodsInfo.add(map);
        }
        return goodsInfo;
    }
    public List<Map<String, Object>> findGoodspreparationKilling(){
        Date date = new Date();
        List<TgwSeckill> goodsIsKilling = tgwSeckillMapper.findGoodspreparationKilling(date);
        List<Map<String, Object>> goodsInfo=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < goodsIsKilling.size(); i++) {
            TgwSeckill seckill = goodsIsKilling.get(i);
            Integer tgwGoodsId = seckill.getTgwGoodsId();
            Map<String, Object> map = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(tgwGoodsId);
            map.put("seckill",seckill);
            map.put("status","等待秒杀");
            long pretime = seckill.getSeckillCreattime().getTime()-date.getTime();
            map.put("pretime",pretime);

            goodsInfo.add(map);
        }
        return goodsInfo;
    }

    /**
    * @Description:    根据秒杀记录表id查询秒杀记录信息
    * @Author:         梁智发
    * @CreateDate:     2018/12/11 0011 16:29
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/11 0011 16:29
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    public TgwSeckill findTgwSeckillById(Integer id){
        return tgwSeckillMapper.selectByPrimaryKey(id);
    }


    /**
    * @Description:    执行秒杀业务逻辑,开启事务管理
    * @Author:         梁智发
    * @CreateDate:     2018/12/11 0011 16:47
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/11 0011 16:47
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @Transactional
    public void executeSecKill(Integer seckillId,Integer UserId){

        /**
         * 减秒杀信息表的库存
         */
        int updaterepertory = tgwSeckillMapper.updaterepertory(seckillId);
        if (updaterepertory<=0){
            throw new RuntimeException();//库存不够扣
        }
        /**
         * 下订单
         */

    }


}