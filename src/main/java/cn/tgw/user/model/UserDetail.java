package cn.tgw.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetail {
    private Integer id;

    private Integer tgwUserId;

    private Byte sex;

    private String mobile;

    private String email;

    private Date regTime;

    private Date lastUpdateTime;

    private String userImageUrl;

}