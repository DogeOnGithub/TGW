package cn.tgw.goods.service;

import cn.tgw.goods.mapper.GoodsMapper;
import cn.tgw.goods.model.Goods;
import cn.tgw.goods.model.GoodsDetail;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.goods.model.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String addGoodsAndGoodsDetailAndGoodsImage(Goods goods, GoodsDetail goodsDetail, MultipartFile multipartFile);
    public Map<String,Object> findGoodsAndGoodsDetailAndGoodsImageWithGoodsId(int id);
    public List<Object> findGoodsAndGoodsDetailAndGoodsImageWithBussinessId(int businessmanId);
    public String updateGoodsByGoodsId(Goods goods,GoodsDetail goodsDetail,MultipartFile multipartFile) throws Exception;
    public Boolean deleteGoods(int goodsId);
    public Boolean upGoods(int goodsId);
    public Boolean downGoods(int goodsId);
    public List<GoodsVO> findAllGoodsVO();

    public String updateGoodsRepertory(int goodsId,int num);

    public String addGoodsSalesVolumn(int goodsId,int num);

    public List<Goods> findGoodsByBusinessmanId(int businessmanId);

    public List<Goods> findGoodsByBusinessmanIdWithIsOnline(int businessmanId);

    public Map<String,Object> findGoodsBySearchOptionAndCity(String searchOption,String city, int num, int size);

}
