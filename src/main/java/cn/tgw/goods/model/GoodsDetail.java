package cn.tgw.goods.model;

import java.util.Date;

public class GoodsDetail {
    private Integer id;

    private Integer tgwGoodsId;

    private String goodsDesc;

    private Integer termOfValidity;

    private Date creatGoodsTime;

    private Integer goodsRepertory;

    private Integer salesVolumn;

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

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

    public Integer getTermOfValidity() {
        return termOfValidity;
    }

    public void setTermOfValidity(Integer termOfValidity) {
        this.termOfValidity = termOfValidity;
    }

    public Date getCreatGoodsTime() {
        return creatGoodsTime;
    }

    public void setCreatGoodsTime(Date creatGoodsTime) {
        this.creatGoodsTime = creatGoodsTime;
    }

    public Integer getGoodsRepertory() {
        return goodsRepertory;
    }

    public void setGoodsRepertory(Integer goodsRepertory) {
        this.goodsRepertory = goodsRepertory;
    }

    public Integer getSalesVolumn() {
        return salesVolumn;
    }

    public void setSalesVolumn(Integer salesVolumn) {
        this.salesVolumn = salesVolumn;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "id=" + id +
                ", tgwGoodsId=" + tgwGoodsId +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", termOfValidity=" + termOfValidity +
                ", creatGoodsTime=" + creatGoodsTime +
                ", goodsRepertory=" + goodsRepertory +
                ", salesVolumn=" + salesVolumn +
                '}';
    }
}