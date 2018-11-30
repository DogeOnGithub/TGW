package cn.tgw.goods.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: 张华健
 * @Date: 2018/11/29 09:22
 * @Description:用以接收数据库查询出来的和Goods有关的所有数据
 */
@Data
public class GoodsVO {
    private Integer id;

    private String goodsTitle;

    private BigDecimal oringinalPrice;

    private BigDecimal discountPrice;

    private Integer tgwBussinessmanId;

    private String goodsCategory;

    private Integer isOnline;

    private Integer dId;

    private String goodsDesc;

    private Integer termOfValidity;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creatGoodsTime;

    private Integer goodsRepertory;

    private Integer salesVolumn;

    private Integer iId;

    private String imageUrl;

    private Integer isMain;

    private String shopLocation;

    private String shopName;

    private Integer firstCategoryId;

    private String firstCategoryName;

}
