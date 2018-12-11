package cn.tgw.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TgwSeckill {
    private Integer id;

    private Integer tgwGoodsId;

    private Integer seckillRepertory;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date seckillCreattime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date seckillEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTgwGoodsId() {
        return tgwGoodsId;
    }

    public void setTgwGoodsId(Integer tgwGoodsId) {
        this.tgwGoodsId = tgwGoodsId;
    }

    public Integer getSeckillRepertory() {
        return seckillRepertory;
    }

    public void setSeckillRepertory(Integer seckillRepertory) {
        this.seckillRepertory = seckillRepertory;
    }

    public Date getSeckillCreattime() {
        return seckillCreattime;
    }

    public void setSeckillCreattime(Date seckillCreattime) {
        this.seckillCreattime = seckillCreattime;
    }

    public Date getSeckillEnd() {
        return seckillEnd;
    }

    public void setSeckillEnd(Date seckillEnd) {
        this.seckillEnd = seckillEnd;
    }
}