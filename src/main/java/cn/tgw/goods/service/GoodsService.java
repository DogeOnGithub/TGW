package cn.tgw.goods.service;

import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.goods.model.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 张华健
 * @Date: 2018/11/29 08:24
 * @Description:商品信息Service
 */

public interface GoodsService {

    public Object findHotGoodsByCity(String city);
    public Object findNewGoodsByCity(String city);
    public Object findGoodsByCityAndFirstCategory(String city);
    public String addGoodsAndGoodsDetailAndGoodsImage(Goods goods, GoodsDetail goodsDetail, GoodsImage goodsImage);
    public Map<String,Object> findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(int id);
    public List<Object> findGoodsAndGoodsDetailAndGoodsImageWithBussinessId(Goods goods);
    public String updateGoodsByGoodsId(Goods goods,GoodsDetail goodsDetail,GoodsImage goodsImage) throws Exception;
    public String updateIsOnline(Goods goods);
    public List<GoodsVO> findAllGoodsVO();

    public String updateGoodsRepertory(int goodsId,int num);

    public String addGoodsSalesVolumn(int goodsId,int num);

    public List<Goods> findGoodsByBusinessmanId(int businessmanId);
}
