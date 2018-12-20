package cn.tgw.goods.controller;

import cn.tgw.goods.elasticsearchRepository.GoodsEsRepository;
import cn.tgw.goods.model.EsGoodsVo;
import cn.tgw.goods.model.EsResult;
import cn.tgw.goods.model.GoodsVO;
import cn.tgw.goods.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsEsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsEsRepository goodsEsRepository;

    @RequestMapping("/addAllGoodsToEs")
    public Object addAllGoodsToEs(){
        List<GoodsVO> allGoodsVO = goodsService.findAllGoodsVO();
        for (int i = 0; i < allGoodsVO.size(); i++) {
            GoodsVO goodsVO = allGoodsVO.get(i);
            EsGoodsVo esGoodsVo=new EsGoodsVo();
            BeanUtils.copyProperties(goodsVO,esGoodsVo);
            goodsEsRepository.save(esGoodsVo);
        }
        return allGoodsVO;
    }

    /**
     * 商品搜索，输入框可以输入商品名称或者店家名称
     * @param goodsTitle  输入的内容
     *       分页只用于搜索全部
     * @param page 起始页
     * @param size  每页的大小
     * @return
     */
   @RequestMapping("/xiaojian/findByShopNameOrGoodsTitle")
    public  EsResult findByShopNameOrGoodsTitle(String goodsTitle, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                               @RequestParam(value = "size",defaultValue = "10")Integer size){
        Pageable pageable=PageRequest.of(page,size);
       // Page<EsGoodsVo> esGoodsVos = goodsEsRepository.findByShopNameLikeOrGoodsTitleLike(content, content, pageable);
       EsResult esResult=null;
       if (StringUtils.isEmpty(goodsTitle)){
           Page<EsGoodsVo> all = goodsEsRepository.findAll(pageable);
           esResult=new EsResult(all.getSize(),all.getContent());
           return esResult;
       }
       //.fuzzyQuery("goodsTitle",content);
       //QueryBuilder queryBuilder=QueryBuilders.wildcardQuery("goodsTitle","*"+content+"*");
       //Page<EsGoodsVo> search = goodsEsRepository.search(queryBuilder, pageable);
       //Page<EsGoodsVo> search = goodsEsRepository.findByGoodsTitleIsLike(goodsTitle,pageable);
       List<EsGoodsVo> search = goodsEsRepository.findByGoodsTitleIsLikeOrShopNameIsLikeOrderByIdAsc(goodsTitle, goodsTitle);
       //Page<EsGoodsVo> search = goodsEsRepository.findByGoodsTitleIsLikeOrShopNameIsLike(goodsTitle, goodsTitle, pageable);
       esResult=new EsResult(search.size(),search);
       return esResult;
    }

}


