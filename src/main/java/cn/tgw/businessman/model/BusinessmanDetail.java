package cn.tgw.businessman.model;

import lombok.Data;

import java.util.Date;

@Data
public class BusinessmanDetail {
    private Integer id;

    private Integer tgwBusinessmanId;

    private String shopName;

    private String shopLocation;

    private String shopDesc;

    private Date shopTimeOpen;

    private Date shopTimeClose;

    private String phoneNumber;

    private String contactPhoneNumber;

    private Byte shopStatus;

    private Integer tgwUserId;

    private String shopNotice;
}