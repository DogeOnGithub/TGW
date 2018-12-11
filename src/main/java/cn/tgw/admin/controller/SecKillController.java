package cn.tgw.admin.controller;

import cn.tgw.admin.service.SecKillService;
import cn.tgw.common.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    SecKillService secKillService;

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
    @RequestMapping("/{id}/{md5}")
    public  Object executeSecKill(@PathVariable(value = "id") Integer id,@PathVariable(value = "md5") String md5){

        String md5Url = MD5Utils.tgwMD5(id + "");
        Map<String,Object>map=new HashMap<>();
        if (!md5.equals(md5Url)){
            map.put("status",false);
            map.put("msg","接口被修改，重新刷新页面再尝试！");
            return map;
        }


        return "";
    }

    @RequestMapping("/findGoodspreparationKilling")
    public Object findGoodspreparationKilling(){
        List<Map<String, Object>> preparationKilling = secKillService.findGoodspreparationKilling();
        return preparationKilling;
    }



}
