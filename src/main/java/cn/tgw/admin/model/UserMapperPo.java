package cn.tgw.admin.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserMapperPo {
    private Integer id;

    private String username;

    private String password;

    private Byte userStatus;

    private Byte sex;

    private String mobile;

    private String email;

    private Date regTime;

    private Date lastUpdateTime;

    private String userImageUrl;

    private String nickName;

}
