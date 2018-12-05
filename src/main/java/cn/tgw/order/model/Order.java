package cn.tgw.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Integer id;

    private Integer tgwGoodsId;

    private Integer tgwUserId;

    private Date orderCreateTime;

    private Byte sellStatus;

    private Integer count;

    private BigDecimal total;

    private Byte orderStatus;

    private String paySerialsNumber;

    private Integer tgwBusinessmanId;
}