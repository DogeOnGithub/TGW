package cn.tgw.goods.model;

import java.math.BigDecimal;

public class Goods {
    private Integer id;

    private String goodsTitle;

    private BigDecimal oringinalPrice;

    private BigDecimal discountPrice;

    private Integer tgwBusinessmanId;

    private String goodsCategory;

    private Integer isOnline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle == null ? null : goodsTitle.trim();
    }

    public BigDecimal getOringinalPrice() {
        return oringinalPrice;
    }

    public void setOringinalPrice(BigDecimal oringinalPrice) {
        this.oringinalPrice = oringinalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getTgwBusinessmanId() {
        return tgwBusinessmanId;
    }

    public void setTgwBusinessmanId(Integer tgwBusinessmanId) {
        this.tgwBusinessmanId = tgwBusinessmanId;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory == null ? null : goodsCategory.trim();
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", oringinalPrice=" + oringinalPrice +
                ", discountPrice=" + discountPrice +
                ", tgwBusinessmanId=" + tgwBusinessmanId +
                ", goodsCategory='" + goodsCategory + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}