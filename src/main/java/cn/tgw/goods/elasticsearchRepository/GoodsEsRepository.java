package cn.tgw.goods.elasticsearchRepository;

import cn.tgw.goods.model.EsGoodsVo;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface GoodsEsRepository extends ElasticsearchRepository<EsGoodsVo,Integer> {


    Page<EsGoodsVo> findByShopNameLikeOrGoodsTitleLike(String shopName, String goodsTitle,Pageable pageable);

    @Override
    Page<EsGoodsVo> findAll(Pageable pageable);

    Page<EsGoodsVo>  findByShopNameIsLike(String shopName,Pageable pageable);

    Page<EsGoodsVo>  findByGoodsTitleIsLike(String goodsTitle,Pageable pageable);

    Page<EsGoodsVo> findByGoodsTitle(String goodsTitle,Pageable pageable);

    Page<EsGoodsVo> findByGoodsTitleLikeAndShopNameLike(String shopName, String goodsTitle,Pageable pageable);

    Page<EsGoodsVo> findByGoodsTitleOrShopName(String goodsTitle,String shopName,Pageable pageable);

    @Override
    Page<EsGoodsVo> search(QueryBuilder queryBuilder, Pageable pageable);

    List<EsGoodsVo> findByGoodsTitleIsLikeOrShopNameIsLikeOrderByIdAsc(String goodsTitle,String shopName);

    Page<EsGoodsVo> findByGoodsTitleIsLikeOrShopNameIsLike(String goodsTitle, String shopName, Pageable pageable);
}
