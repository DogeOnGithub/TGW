package cn.tgw.goods.service.serviceImpl;

import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.GoodsVO;
import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Object findGoodsByCityAndFirstCategory(String city, String firstCategory) {
        Map<String,Object> param = new HashMap<>();
        //更正city，用于sql搜索
        String currentCity = "%"+city+"%";
        param.put("city",currentCity);
        param.put("type",3);
        param.put("firstCategoryName",firstCategory);
        List<GoodsVO> indexGoodsByCityAndTypeAndFirstCategoryName = goodsMapper.findIndexGoodsByCityAndTypeAndFirstCategoryName(param);
        return indexGoodsByCityAndTypeAndFirstCategoryName;
    }


}
