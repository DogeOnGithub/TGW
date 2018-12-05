package cn.tgw.goods.controller;


import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 张华健
 * @Date: 2018/11/28 11:16
 * @Description:团购数据控制层
 */
@RestController
public class GoodsController {


    @Autowired
    private GoodsService goodsService;


    @RequestMapping(value = "xiaojian/hotGoods",method = RequestMethod.GET)
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
    @RequestMapping(value = "xiaojian/newGoods",method = RequestMethod.GET)
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
    @RequestMapping(value = "xiaojian/goodsByCategory",method = RequestMethod.GET)
    public Object goodsByCategory(@RequestParam(value = "city",required = false,defaultValue = "湛江") String city){
        /**
         *
         * 功能描述: 首页请求，按照一级分类返回团购信息
         *
         * @param: city，category
         * @return:
         * @auther: 张华健
         * @date:  2018/11/30
         */
        return goodsService.findGoodsByCityAndFirstCategory(city);
    }

    
    /**
     *
     * 功能描述: 商家新增团购接口
     *
     * @param: Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage, MultipartFile multipartFile
     * @return: result
     * @auther: 张华健
     * @date:  2018/12/04
     */
    @RequestMapping(value = "xiaojian/goods",method = RequestMethod.POST)
    public Object addGoods(Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage, @RequestParam(value = "multipartFile",required = false)MultipartFile multipartFile){

//        System.out.println(multipartFile);
//        System.out.println(goods);
//        System.out.println(goodsDetail);
//        System.out.println(goodsImage);


        String returnUrl = null;
        Map<String,Object> result = new HashMap<>();
        try {
            returnUrl = QiniuUtil.uploadImg(multipartFile);
        } catch (IOException e) {
            result.put("error","图片上传错误！请重试！");
            return result;
        }
        goodsImage.setImageUrl(returnUrl);

        String addresult = goodsService.addGoodsAndGoodsDetailAndGoodsImage(goods, goodsDetail, goodsImage);
        if (addresult.equals("success")){
            result.put(addresult,"添加团购成功");
        }else{
            result.put(addresult,"添加失败，清稍后重试");
        }
        return result;
    }

}
