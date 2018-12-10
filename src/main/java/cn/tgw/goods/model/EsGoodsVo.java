package cn.tgw.goods.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(indexName = "tgwgoods",type = "goods")
public class EsGoodsVo implements Serializable {

    private static final long serialVersionUID = -7127701649863101608L;
    @Id
    private Integer id;

    private String goodsTitle;

    private BigDecimal oringinalPrice;

    private BigDecimal discountPrice;

    private Integer tgwBusinessmanId;

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
