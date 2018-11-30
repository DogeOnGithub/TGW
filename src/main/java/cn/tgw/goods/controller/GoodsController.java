package cn.tgw.goods.controller;


import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 张华健
 * @Date: 2018/11/28 11:16
 * @Description:团购数据控制层
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @RequestMapping(value = "/hotGoods",method = RequestMethod.GET)
    public Object hotGoods(@RequestParam(value = "city",required = false,defaultValue = "湛江") String city){
        /**
         *
         * 功能描述: 首页的热门团购请求，返回指定团购的信息
         *
         * @param: city
         * @return: 指定商品信息
         * @auther: 张华健
         * @date:  2018/11/30
         */
        return goodsService.findHotGoodsByCity(city);
    }
    @RequestMapping(value = "/newGoods",method = RequestMethod.GET)
    public Object newGoods(@RequestParam(value = "city",required = false,defaultValue = "湛江") String city){
        /**
         *
         * 功能描述: 首页的最新热卖团购请求，返回最新上架的团购
         *
         * @param: city
         * @return:
         * @auther: 张华健
         * @date:  2018/11/30
         */
        return goodsService.findNewGoodsByCity(city);
    }
    @RequestMapping(value = "/goodsByCategory",method = RequestMethod.GET)
    public Object goodsByCategory(@RequestParam(value = "city",required = false,defaultValue = "湛江") String city,
                                  @RequestParam(value = "category",required = false) String category){
        /**
         *
         * 功能描述: 首页请求，按照一级分类返回团购信息
         *
         * @param: city，category
         * @return:
         * @auther: 张华健
         * @date:  2018/11/30
         */
        return goodsService.findGoodsByCityAndFirstCategory(city,category);
    }

}
