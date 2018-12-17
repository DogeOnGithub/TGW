package cn.tgw.order.model;

import lombok.Data;

import java.util.Date;

/*
 * @Project:tgw
 * @Description:query param model
 * @Author:TjSanshao
 * @Create:2018-12-14 08:42
 *
 **/
@Data
public class OrderQueryModel {

    private Integer businessmanId;

    private Date start;

    private Date end;

}
