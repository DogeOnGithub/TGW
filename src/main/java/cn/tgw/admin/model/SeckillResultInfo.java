package cn.tgw.admin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeckillResultInfo implements Serializable {
    private static final long serialVersionUID = -3910981422257208817L;

    private  String secKillUrl;
    private  TgwSeckill tgwSeckill;

    public SeckillResultInfo(String secKillUrl, TgwSeckill tgwSeckill) {
        this.secKillUrl = secKillUrl;
        this.tgwSeckill = tgwSeckill;
    }
}
