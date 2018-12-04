package cn.tgw.goods.service.serviceImpl;

import cn.tgw.admin.mapper.TgwManagerMapper;
import cn.tgw.admin.model.TgwFirstCategory;
import cn.tgw.admin.service.TgwFirstCategoryService;
import cn.tgw.goods.mapper.GoodsDetailMapper;
import cn.tgw.goods.mapper.GoodsImageMapper;
import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.goods.model.GoodsVO;
import cn.tgw.goods.service.GoodsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 张华健
 * @Date: 2018/11/29 11:00
 * @Description:商品信息Service实现
 */
@Service

public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;
    @Autowired
    private GoodsImageMapper goodsImageMapper;
    @Autowired
    private TgwFirstCategoryService tgwFirstCategoryService;

    
    /**
     *
     * 功能描述: 下面三个方法都是用于首页数据的。
     * 参数type说明：
     *      1：查询热门团购展示
     *      2：查询最新上架团购展示
     *      3：按照分类查询团购展示
     *
     * @param:
     * @return: 
     * @auther: 张华健
     * @date:  2018/11/30
     */
    @Override
    public Object findHotGoodsByCity(String city) {

        Map<String,Object> param = new HashMap<>();
        //更正city，用于sql搜索
        String currentCity = "%"+city+"%";
        param.put("city",currentCity);
        param.put("type",1);
        param.put("firstCategoryName",null);
        List<GoodsVO> indexGoodsByCityAndTypeAndFirstCategoryName = goodsMapper.findIndexGoodsByCityAndTypeAndFirstCategoryName(param);
        return indexGoodsByCityAndTypeAndFirstCategoryName;
    }

    @Override
    public Object findNewGoodsByCity(String city) {
        Map<String,Object> param = new HashMap<>();
        //更正city，用于sql搜索
        String currentCity = "%"+city+"%";
        param.put("city",currentCity);
        param.put("type",2);
        param.put("firstCategoryName",null);
        List<GoodsVO> indexGoodsByCityAndTypeAndFirstCategoryName = goodsMapper.findIndexGoodsByCityAndTypeAndFirstCategoryName(param);
        return indexGoodsByCityAndTypeAndFirstCategoryName;
    }

    @Override
    public Object findGoodsByCityAndFirstCategory(String city) {
        Map<String,Object> param = new HashMap<>();
        //更正city，用于sql搜索
        String currentCity = "%"+city+"%";
        param.put("city",currentCity);
        param.put("type",3);


        JSONObject jsonObject = new JSONObject();



        List<TgwFirstCategory> tgwFirstCategories = tgwFirstCategoryService.allTgwFirstCategory();
        for (int i = 0; i < tgwFirstCategories.size(); i++) {
            TgwFirstCategory tgwFirstCategory = tgwFirstCategories.get(i);
            String categoryName = tgwFirstCategory.getCategoryName();
            param.put("firstCategoryName",categoryName);
            List<GoodsVO> indexGoodsByCityAndTypeAndFirstCategoryName = goodsMapper.findIndexGoodsByCityAndTypeAndFirstCategoryName(param);
            jsonObject.put(categoryName,indexGoodsByCityAndTypeAndFirstCategoryName);
        }

        return jsonObject;
    }


    /**
     *
     * 功能描述: 添加团购服务层处理
     *
     * @param: Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage
     * @return: String
     * @auther: 张华健
     * @date:  2018/12/04
     */
    @Transactional
    @Override
    public String addGoodsAndGoodsDetailAndGoodsImage(Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage) {

//        //随机生成团购ID
        String toHash = goods.getGoodsTitle()+goods.getGoodsCategory()+goodsDetail.getGoodsDesc()+Math.random();
        int gid = Math.abs(toHash.hashCode());
        goods.setId(gid);
        //设置团购为在售
        goods.setIsOnline(1);
        //插入到数据库
        int resultGoodsInsert = goodsMapper.insertSelective(goods);

        //在团购详情中的tgwGoodsId写上团购id
        goodsDetail.setTgwGoodsId(gid);
        //设置创建团购的时间，为当前时间
        goodsDetail.setCreatGoodsTime(new Date());
        //设置团购的销量，初始为0
        goodsDetail.setSalesVolumn(0);
        //插入到数据库
        int resultGoodsDetailInsert = goodsDetailMapper.insertSelective(goodsDetail);

        //在团购图片中的tgwGoodsId写上团购id
        goodsImage.setTgwGoodsId(gid);
        //插入到数据库
        int resultGoodsImageInsert = goodsImageMapper.insertSelective(goodsImage);

        //判断插入是否都执行，返回成功或者失败
        if(resultGoodsInsert==1&&resultGoodsDetailInsert==1&&resultGoodsImageInsert==1){
            return "success";
        }

        return "error";
    }


}
