package cn.tgw.goods.model;

public class GoodsImage {
    private Integer id;

    private String imageUrl;

    private Integer tgwGoodsId;

    private Integer isMain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getTgwGoodsId() {
        return tgwGoodsId;
    }

    public void setTgwGoodsId(Integer tgwGoodsId) {
        this.tgwGoodsId = tgwGoodsId;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    @Override
    public String toString() {
        return "GoodsImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", tgwGoodsId=" + tgwGoodsId +
                ", isMain=" + isMain +
                '}';
    }
}