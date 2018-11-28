package cn.tgw.common.model;

import lombok.Data;

import java.util.Date;

/*
 * @Project:tgw
 * @Description:message verify
 * @Author:TjSanshao
 * @Create:2018-11-28 17:42
 *
 **/
@Data
public class SmsVerify {

    private int id;
    private String mobile;
    private String code;
    private Byte status;
    private Date sendTime;
    private Byte times;

}
