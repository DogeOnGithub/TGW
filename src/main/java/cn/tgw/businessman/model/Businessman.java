package cn.tgw.businessman.model;

import lombok.Data;

@Data
public class Businessman {
    private Integer id;

    private String username;

    private String password;

    private Byte status;
}