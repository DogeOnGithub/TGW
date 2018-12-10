package cn.tgw.goods.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsResult implements Serializable {
    private static final long serialVersionUID = 4422585645765456175L;
    private Integer total;
    private Object data;

    public EsResult(Integer total, Object data) {
        this.total = total;
        this.data = data;
    }
}
