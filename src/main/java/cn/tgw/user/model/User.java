package cn.tgw.user.model;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private Byte userStatus;

}