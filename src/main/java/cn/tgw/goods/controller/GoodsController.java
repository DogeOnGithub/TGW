package cn.tgw.goods.controller;


import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.goods.mapper.GoodsImageMapper;
import cn.tgw.goods.mapper.GoodsMapper;
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
import java.util.List;
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
    @RequestMapping(value = "xiaojian/addGoods",method = RequestMethod.POST)
    public Object addGoods(Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage, @RequestParam(value = "multipartFile",required = false)MultipartFile multipartFile){

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

    /**
     *
     * 功能描述: 根据商家Id返回该商家所有团购
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/06
     */
    @RequestMapping(value = "xiaojian/goodsByTgwBusinessId",method = RequestMethod.GET)
    public Object findGoodsByBusniessmanId(Goods goods){

        Map<String,Object> result = new HashMap<>();
        result.put("status","error");
        result.put("Msg","出错");
        List<Object> resultList = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithBussinessId(goods);
        if(resultList!=null){
            result.put("status","success");
            result.put("Msg","success");
            result.put("result",resultList);
        }else{
            result.put("Msg","暂无团购");
        }
        return result;
    }

    /**
     *
     * 功能描述: 根据团购Id返回该团购所有信息
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/06
     */
    @RequestMapping(value = "xiaojian/goodsById",method = RequestMethod.GET)
    public Object findGoodsById(int id){

        Map<String,Object> result = new HashMap<>();
        result.put("status","error");
        result.put("Msg","出错");
        Map<String, Object> resultMap = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(id);
        if(resultMap!=null){
            result.put("status","success");
            result.put("Msg","success");
            result.put("result",resultMap);
        }else{
            result.put("Msg","暂无团购");
        }
        return result;
    }


    /**
     *
     * 功能描述: 商家修改团购信息接口
     *
     * @param: Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage,MultipartFile multipartFile
     * @return: Map result
     * @auther: 张华健
     * @date:  2018/12/06
     */
    @RequestMapping(value = "xiaojian/updateGoods",method = RequestMethod.POST)
    public Object updateGoods(Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage){

        Map<String,Object> result = new HashMap<>();
        result.put("status","error");
        result.put("Msg","修改出错，请稍后重试");
        try {
            String res = goodsService.updateGoodsByGoodsId(goods, goodsDetail, goodsImage);
        } catch (Exception e) {
//            e.printStackTrace();
            return result;
        }
        result.put("status","success");
        result.put("Msg","修改成功");
        return result;
    }

    /**
     *
     * 功能描述:
     *
     * @param: 修改图片时上传图片的接口，接口返回一个url，跟随修改表单提交。
     * @return: result
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "xiaojian/updateGoodsImage",method = RequestMethod.POST)
    public Object updateImages(@RequestParam(value = "multipartFile",required = false)MultipartFile multipartFile){
        String returnUrl = null;
        Map<String,Object> result = new HashMap<>();
        try {
            returnUrl = QiniuUtil.uploadImg(multipartFile);
        } catch (IOException e) {
            result.put("status","error");
            result.put("Msg","图片上传错误，请重试");
        }
        result.put("status","success");
        result.put("Msg","success");
        result.put("newUrl",returnUrl);
        return result;
    }

    /**
     *
     * 功能描述: 修改商品状态，接口参数为id 以及isOnline,下架：isOnline=0
     *                                               上架：isOnline=1
     *                                               逻辑删除：isOnline=2
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "xiaojian/deleteGoods",method = RequestMethod.PUT)
    public Object deleteGoods(Goods goods){
        Map<String,Object> result = new HashMap<>();
        result.put("status","error");
        result.put("Msg","unknown error");
        String res = goodsService.updateIsOnline(goods);
        if(res.equals("success")){
            result.put("status",res);
            result.put("Msg","success");
        }
        return result;
    }

}
