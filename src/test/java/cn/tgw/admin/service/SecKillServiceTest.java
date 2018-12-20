package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSeckillMapper;
import cn.tgw.admin.model.TgwSeckill;
import cn.tgw.goods.mapper.GoodsImageMapper;
import cn.tgw.goods.model.GoodsImage;
import cn.tgw.user.mapper.UserDetailMapper;
import cn.tgw.user.mapper.UserMapper;
import cn.tgw.user.model.UserDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecKillServiceTest {



    @Autowired
    TgwSeckillMapper tgwSeckillMapper;

    @Autowired
    UserDetailMapper userDetailMapper;

    @Autowired
    GoodsImageMapper goodsImageMapper;


    @Test
    public void insertSeckill() {
        TgwSeckill tgwSeckill = new TgwSeckill();
        tgwSeckill.setSeckillEnd(new Date());
        tgwSeckill.setSeckillCreattime(new Date());
        tgwSeckill.setTgwGoodsId(2);
        tgwSeckill.setSeckillRepertory(30);
        tgwSeckill.setSeckillPrice(new BigDecimal(20));
        int insert = tgwSeckillMapper.insert(tgwSeckill);
    }

    @Test
    public void updateImgs(){
       /* List<UserDetail> allUserDetail = userDetailMapper.findAllUserDetail();
        for (int i = 0; i < allUserDetail.size(); i++) {
            UserDetail userDetail = allUserDetail.get(i);
            String userImageUrl = userDetail.getUserImageUrl();

            if (!StringUtils.isEmpty(userImageUrl)){
                userImageUrl=userImageUrl.replace("pih7n7d5x.bkt.clouddn.com","zhifa.daiqee.com");
                userDetailMapper.updateUserImgs(userImageUrl,userDetail.getId());
            }

        }*/
        List<GoodsImage> allGoodsImage = goodsImageMapper.findAllGoodsImage();
        for (int i = 0; i < allGoodsImage.size(); i++) {
            GoodsImage goodsImage = allGoodsImage.get(i);
            String imageUrl = goodsImage.getImageUrl();
            if (!StringUtils.isEmpty(imageUrl)){
                imageUrl=imageUrl.replace("pih7n7d5x.bkt.clouddn.com","zhifa.daiqee.com");
                goodsImageMapper.updateGoodsImgs(imageUrl,goodsImage.getId());
            }

        }

    }
}