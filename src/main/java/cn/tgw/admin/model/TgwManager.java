package cn.tgw.admin.model;

import lombok.Data;

@Data
public class TgwManager {
    private Integer id;

    private String tgwManagerName;

    private String managerPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTgwManagerName() {
        return tgwManagerName;
    }

    public void setTgwManagerName(String tgwManagerName) {
        this.tgwManagerName = tgwManagerName == null ? null : tgwManagerName.trim();
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword == null ? null : managerPassword.trim();
    }
}