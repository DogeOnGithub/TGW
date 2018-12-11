package cn.tgw.admin.controller;

import cn.tgw.admin.service.SecKillService;
import cn.tgw.goods.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
