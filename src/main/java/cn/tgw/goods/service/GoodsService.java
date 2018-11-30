package cn.tgw.goods.service;

import cn.tgw.goods.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 张华健
 * @Date: 2018/11/29 08:24
 * @Description:商品信息Service
 */

public interface GoodsService {

    public Object findHotGoodsByCity(String city);
    public Object findNewGoodsByCity(String city);
    public Object findGoodsByCityAndFirstCategory(String city,String firstCategory);
}
