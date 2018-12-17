package cn.tgw.goods.controller;


import cn.tgw.admin.model.SeckillResultInfo;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.admin.service.SecKillService;
import cn.tgw.common.utils.QiniuUtil;
import cn.tgw.common.utils.TGWStaticString;
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
import java.util.Date;
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

    @Autowired
    private SecKillService secKillService;



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
    public Object addGoods(Goods goods, GoodsDetail goodsDetail,MultipartFile image){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,"添加失败，请稍后重试");
        String addresult = goodsService.addGoodsAndGoodsDetailAndGoodsImage(goods, goodsDetail, image);
        if (!addresult.equals("success")){
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
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
    public Object updateGoods(Goods goods, GoodsDetail goodsDetail,MultipartFile multipartFile){

        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,"修改失败，请稍后重试");
        try {
            String res = goodsService.updateGoodsByGoodsId(goods, goodsDetail, multipartFile);
            if(res.equals("error")){
                return result;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
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
    public Object findGoodsByBusniessmanId(Integer businessmanId){

        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,"查询失败，请稍后重试");
        List<Object> resultList = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithBussinessId(businessmanId);
        if(resultList!=null){
            result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            result.put("resultGoods",resultList);
        }else{
            result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,"暂无团购");
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
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        Map<String, Object> resultMap = goodsService.findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(id);
        if(resultMap.get("goods")!=null){
            result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
            result.put("resultGoods",resultMap);
            result.put("isOnSecKill",false);
            SeckillResultInfo resultInfo = secKillService.findTgwSeckillBygoodsIdAndNowTime(id, new Date());
            if(resultInfo.getTgwSeckill()!=null){
                result.put("isOnSecKill",true);
                result.put("secKill",resultInfo);
            }
        }else{
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,"无此团购");
        }
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
            return result;
        }
        result.put("status","success");
        result.put("Msg","success");
        result.put("newUrl",returnUrl);
        return result;
    }

    /**
     *
     * 功能描述: 删除团购
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "xiaojian/deleteGoods",method = RequestMethod.GET)
    public Object deleteGoods(Integer goodsId){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        if(!goodsService.deleteGoods(goodsId)){
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        return result;
    }

    /**
     *
     * 功能描述: 上架团购
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "xiaojian/upGoods",method = RequestMethod.GET)
    public Object upGoods(Integer goodsId){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        if(!goodsService.upGoods(goodsId)){
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        return result;
    }

    /**
     *
     * 功能描述: 下架团购
     *
     * @param: Goods goods
     * @return:
     * @auther: 张华健
     * @date:  2018/12/10
     */
    @RequestMapping(value = "xiaojian/downGoods",method = RequestMethod.GET)
    public Object downGoods(Integer goodsId){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        if(!goodsService.downGoods(goodsId)){
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        return result;
    }

}
