package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSeckillMapper;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Integer tgwGoodsId = seckill.getTgwGoodsId();
            Map<String, Object> map = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(tgwGoodsId);
            String md5Url = MD5Utils.tgwMD5(tgwGoodsId + "");
            map.put("seckill",seckill);
            map.put("status","正在秒杀");
            map.put("url","/seckill/"+tgwGoodsId+"/"+md5Url);
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


}
