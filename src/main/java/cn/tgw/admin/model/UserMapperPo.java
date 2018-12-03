package cn.tgw.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date regTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastUpdateTime;

    private String userImageUrl;

    private String nickName;

}
