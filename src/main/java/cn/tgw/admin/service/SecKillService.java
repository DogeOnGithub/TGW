package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSeckillMapper;
import cn.tgw.admin.model.SeckillResultInfo;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.goods.service.GoodsService;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
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
    private OrderService orderService;

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
     *
    */

    /**
     * 进入商品详情页的时候判断该商品是否为  秒杀商品
     * @param tgw_goods_id  商品id
     * @param nowTime  当前时间
     * @return
     */
    public SeckillResultInfo findTgwSeckillBygoodsIdAndNowTime(Integer tgw_goods_id, Date nowTime){
        TgwSeckill tgwSeckill = tgwSeckillMapper.findTgwSeckillBygoodsIdAndNowTime(tgw_goods_id, nowTime);
        String url="";
        if (tgwSeckill!=null){
            Integer seckillId = tgwSeckill.getId();
            String md5Url = MD5Utils.tgwMD5(seckillId + "");
            url="/seckill/"+seckillId+"/"+md5Url;
        }

        SeckillResultInfo seckillResultInfo=new SeckillResultInfo(url,tgwSeckill);
        return seckillResultInfo;
    }

    @Transactional
    public Order executeSecKill(Integer userId,Integer seckillId){

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
        Order order = orderService.createmsKillOrderByUserIdAndmsKillId(userId, seckillId, Integer.valueOf(1));
        if (order==null){
            throw new RuntimeException();//添加订单失败
        }
        return order;
    }

    public boolean IsRepeatKill(Integer userId,Integer seckillId){
        TgwSeckill tgwSeckill = tgwSeckillMapper.selectByPrimaryKey(seckillId);
        Integer tgwGoodsId = tgwSeckill.getTgwGoodsId();
        Date seckillCreattime = tgwSeckill.getSeckillCreattime();
        Date seckillEndttime = tgwSeckill.getSeckillEnd();
        List<Order> orders = orderService.IsRepeatKill(userId, tgwGoodsId, seckillCreattime, seckillEndttime);
        if (orders!=null&&orders.size()>0){
            return true;
        }

        return false;
    }

    /**
    * @Description:    添加秒杀商品
    * @Author:         梁智发
    * @CreateDate:     2018/12/13 0013 10:02
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/13 0013 10:02
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    public int InsertSeckill(TgwSeckill tgwSeckill){
        int insert = tgwSeckillMapper.insert(tgwSeckill);
        return insert;

    }

    /**
    * @Description:    shanchu删除秒杀商品
    * @Author:         梁智发
    * @CreateDate:     2018/12/13 0013 10:03
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/13 0013 10:03
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    public  int deleteSeckilling(Integer id){
        int i = tgwSeckillMapper.deleteByPrimaryKey(id);
        return i;
    }


}
