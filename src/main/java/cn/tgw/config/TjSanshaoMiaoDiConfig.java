package cn.tgw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * @Project:tgw
 * @Description:TjSanshao's miaodi configuration
 * @Author:TjSanshao
 * @Create:2018-11-28 15:17
 *
 **/
@Configuration
@ConfigurationProperties(prefix = "miaodi.config")
public class TjSanshaoMiaoDiConfig {

    //url前半部分
    private String baseUrl = "https://api.miaodiyun.com/20150822";

    /**
     * 开发者注册后系统自动生成的账号，可在官网登录后查看
     */
    private String accountId = "7e32f1d5bb2d4d3987fb3850f962e965";

    /**
     * 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
     */
    private String authToken = "36a302d744684be48c7468fb0b358cb0";

    /**
     * 响应数据类型, JSON或XML
     */
    private String respDataType = "json";

    private String operation = "/industrySMS/sendSMS";

    private String templateId = "982267297";

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    private int timeout = 30;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRespDataType() {
        return respDataType;
    }

    public void setRespDataType(String respDataType) {
        this.respDataType = respDataType;
    }
}
