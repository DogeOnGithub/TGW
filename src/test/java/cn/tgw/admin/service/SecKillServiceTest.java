package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSeckillMapper;
import cn.tgw.admin.model.TgwSeckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecKillServiceTest {



    @Autowired
    TgwSeckillMapper tgwSeckillMapper;

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
}