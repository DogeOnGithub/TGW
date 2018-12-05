package cn.tgw.businessman.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BusinessmanDetail {
    private Integer id;

    private Integer tgwBusinessmanId;

    private String shopName;

    private String shopLocation;

    private String shopDesc;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shopTimeOpen;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shopTimeClose;

    private String phoneNumber;

    private String contactPhoneNumber;

    private Byte shopStatus;

    private Integer tgwUserId;

    private String shopNotice;

    private Byte shopSettleStatus;
}