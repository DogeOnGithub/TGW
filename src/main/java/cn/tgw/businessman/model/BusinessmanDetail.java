package cn.tgw.businessman.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class BusinessmanDetail {
    private Integer id;

    private Integer tgwBusinessmanId;

    private String shopName;

    private String shopLocation;

    private String shopDesc;

    private LocalTime shopTimeOpen;

    private LocalTime shopTimeClose;

    private String phoneNumber;

    private String contactPhoneNumber;

    private Integer tgwUserId;

    private String shopNotice;

    private Byte shopSettleStatus;
}