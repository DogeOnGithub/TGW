package cn.tgw.goods.elasticsearchRepository;

import cn.tgw.goods.model.EsGoodsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsEsRepositoryTest {

    @Autowired
    GoodsEsRepository goodsEsRepository;

    @Test
    public void findByShopNameOrGoodsTitle() {
        Pageable pageable=new PageRequest(1,10);
        String content="迪拜";
        Page<EsGoodsVo> esGoodsVos = goodsEsRepository.findByShopNameLikeOrGoodsTitleLike(content, content, pageable);
        List<EsGoodsVo> goodsVosContent = esGoodsVos.getContent();
        int totalPages = esGoodsVos.getTotalPages();
        long totalElements = esGoodsVos.getTotalElements();
        System.out.println(esGoodsVos);
    }
}