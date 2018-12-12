package cn.tgw.admin.controller;

import cn.tgw.admin.redisUtil.RedisLock;
import cn.tgw.admin.service.SecKillService;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.order.model.Order;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    SecKillService secKillService;

    @Autowired
    RedisLock redisLock;

    /**
    * @Description:    查询正在秒杀的商品
    * @Author:         梁智发
    * @CreateDate:     2018/12/11 0011 8:43
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/11 0011 8:43
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @RequestMapping("/findGoodsIsKilling")
    public Object findGoodsIsKilling(){
        List<Map<String, Object>> goodsIsKilling = secKillService.findGoodsIsKilling();
        return goodsIsKilling;
    }

    /**
    * @Description:    执行秒杀
    * @Author:         梁智发
    * @CreateDate:     2018/12/11 0011 11:30
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/11 0011 11:30
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @RequestMapping("/{seckillId}/{md5}")
    public  Object executeSecKill(@PathVariable(value = "seckillId") Integer seckillId, @PathVariable(value = "md5") String md5, HttpSession session){

        String md5Url = MD5Utils.tgwMD5(seckillId + "");
        Map<String,Object>map=new HashMap<>();
        if (!md5.equals(md5Url)){
            map.put("status",false);
            map.put("msg","接口被修改，重新刷新页面再尝试!");
            return map;
        }
        User user = (User) session.getAttribute(TGWStaticString.TGW_USER);
        if (user==null){
            map.put("status",false);
            map.put("msg","当前用户未登录,请登录后再秒杀!");
            return map;
        }
        if(secKillService.IsRepeatKill(user.getId(),seckillId)){
            map.put("status",false);
            map.put("msg","重复秒杀!");
            return map;
        }


        String key = String.valueOf(seckillId);
        String value=String.valueOf(System.currentTimeMillis()+(3*1000));
        /***
         * 获取锁，能就进行减库存，下单操作
         */

        if (redisLock.lock(key,value)){

            try {

                Order order = secKillService.executeSecKill(user.getId(), seckillId);
                map.put("status",false);
                map.put("msg",order);
                redisLock.unlock(key,value);
                return map;
            }catch (RuntimeException e){
                e.printStackTrace();
                map.put("status",false);
                map.put("msg","很遗憾，换个姿势再试!");
                return map;

            }



        }else {
            map.put("status",false);
            map.put("msg","很遗憾，换个姿势再试!");
            return map;

        }


    }

    /**
    * @Description:    查询准备秒杀的商品
    * @Author:         梁智发
    * @CreateDate:     2018/12/11 0011 16:19
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/11 0011 16:19
    * @UpdateRemark:   修改内容
    * @Version:        1.0
    */
    @RequestMapping("/findGoodspreparationKilling")
    public Object findGoodspreparationKilling(){
        List<Map<String, Object>> preparationKilling = secKillService.findGoodspreparationKilling();
        return preparationKilling;
    }






}
