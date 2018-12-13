package cn.tgw.admin.controller;

import cn.tgw.admin.model.SeckillResultInfo;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.admin.redisUtil.RedisLock;
import cn.tgw.admin.service.SecKillService;
import cn.tgw.common.utils.MD5Utils;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.order.model.Order;
import cn.tgw.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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


    // 自定义类型转换器
    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

        // System.out.println("initBinder  &&&&"+request.getParameter("hiredate")+"***"+request.getParameter("username"));

        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }


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
                map.put("status",true);
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


    /**
    * @Description:    添加秒杀秒杀商品信息接口
    * @Author:         梁智发
    * @CreateDate:     2018/12/13 0013 8:50
    * @UpdateUser:     梁智发
    * @UpdateDate:     2018/12/13 0013 8:50
    * @UpdateRemark:   修改内容
    * @Version:        1.0
     *  private Integer id;
     *
     *     private Integer tgwGoodsId;
     *
     *     private Integer seckillRepertory;
     *
     *     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
     *     private Date seckillCreattime;
     *
     *     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
     *     private Date seckillEnd;
     *
     *     private BigDecimal seckillPrice;
    */
    @RequestMapping("/insertSeckill")
    public Object insertSeckill(Integer tgwGoodsId,Integer seckillRepertory, String seckillCreattime,String seckillEnd,BigDecimal seckillPrice){
        TgwSeckill tgwSeckill=new TgwSeckill(tgwGoodsId,seckillRepertory,new Date(Long.valueOf(seckillCreattime)),new Date(Long.valueOf(seckillEnd)),seckillPrice);
       int i = secKillService.InsertSeckill(tgwSeckill);
        Map<String,Object>map=new HashMap<>();
        if (i>0){
            map.put("status",true);
            map.put("msg", "添加成功");
        }else {
            map.put("status",false);
            map.put("msg", "添加失败，请重试");
        }
        return map;

    }

    @RequestMapping("/isKillingNow")
    public Object isKillingNow(Integer tgwGoodsId){
        SeckillResultInfo info = secKillService.findTgwSeckillBygoodsIdAndNowTime(tgwGoodsId, new Date());
        Map<String,Object>map=new HashMap<>();
        if (info.getTgwSeckill()!=null){
            map.put("status",false);
            map.put("msg", "该商品正在秒杀，不能设置抢购");
            map.put("info",info);
        }else {
            map.put("status",true);
            map.put("msg", "");

        }
        return map;
    }

    @RequestMapping("/deleteSeckilling")
    public Object deleteSeckilling(Integer id){
        Map<String,Object>map=new HashMap<>();
        int i = secKillService.deleteSeckilling(id);
        if (i>0){
            map.put("status",true);
            map.put("msg", "删除成功");
        }else {
            map.put("status",false);
            map.put("msg", "删除失败，请重试");
        }
        return map;
    }




}
