package cn.tgw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * @Project:tgw
 * @Description:ali pay configuration
 * @Author:TjSanshao
 * @Create:2018-12-06 09:22
 *
 **/
@Configuration
@ConfigurationProperties(prefix = "alipay.config")
public class AlipayConfiguration {

    //应用ID
    private String app_id = "2016091900549543";

    //商户私钥
    private String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCAGF8DNvhkYl81IgraHu75uAFzXfep5Dn+1Lds7yNF/6qSl5D3RHssoCM5PKQ2ezworqbuniK522JEVh4XZWvK46gwIMT3BoEWObRGJQMeKJ3q75BMe6/KqNS7yvrMtfK+r7c2ZBwLR/uWsXiaja70x/3LnaNUa2UPMHxlNHeqgkfNcb2Fwv+0mgcZPhjfMjMGWqd10fFEKJG1ZCm31mP1sugTCLLKP7d7VN2yv2Hdhq+w2dRHawOlOaRxTXljlRNt3OzswTd5h5rFYC+1CCqPyn41TCJPFvGmcwPDiI9gjlsVzrmJqfo7tWqqm7w9oFmK944JLc0RqLu3iZWbvAWBAgMBAAECggEASVIp6IQpkARC7P3sakf0kFoD8o5h+gwaKkSGLcOAVEvwzfIV14OKYdPT9apBcLH/kGXQRZuc0vfBes40cT3rgnnnRo0rUR6W+yrShqge7b9gDR9NYIoX6I/VHR0li9HF/eJEo8yPxOFSIEK7fvQEpjZnjopP+D103knpmmB68szGnWaambcAuvg9iwjbhgGJknsAKHNpkk525Jqu+FiUbkE3po8cdSdRV1OskP0m/XAXGMsHFnE6H0cygaN5qdbjMSAVKj8hQGs1BQbkkOTlUwtNfoONbM1MagPenek43qjwkkXdn6uX49grOcprWTl506oMgkKsTef4IyZHgXY7wQKBgQDcCToLXMJRRBtkZQwQP1v6zqx2o5p8uAFw7AUHyqrBtp03iz7fWmt4OLzs8DNO7IAvaHx5ITTJh6dxaqlnhNAIKc1QxKvB5W1n8WBl8QKl5gm+QNVkNlul88JsZWmtG8Bs+xfgs2/5o8Jm2OK4YCff4PWEsZA+W2wwID2JYa82uQKBgQCVCCUpP7W2orcDsh/8hptgZFyvCJT5r0b7iEOLdsyX3+ba4/BEYJCNsbomw1A7WdpZ3pZP/sImDHJdjJ2eaF8Df+2+7lPnspOX0cErz9hMhR2lNjV2Mz70mOmY4Jh11Niixrl+3JCbDwFw5i11CrCLGWbgxQj5lVIvUZ48NA9hCQKBgQDHZd0h+tdlSbEBE8kQ2DoEWVJU2QHgz+cOa/RLEVAY5+IlOZyqT4hglBoxJaN5fDzVuXxDUOJDVxlYYG5QDz1uiGM1NGTp+31f2o+fEf9yU7q0nJBEljYLd5DTJjvT0fhZSlpjGHj643oCNb2PlCPOcFBOBZNPzw2Ft1HMs6dSkQKBgBu/74N1vHUsaR2EDlasDcRuC5//3hV74KC3Z7RRQ5lH9KlvZe6leGUZ4cqT9Z2HhpPTUXPFwQrqqGTo6PR1BLQXHwK9R9xNsRPE1847nk/pPXLk3CGNOzy6OEziDSFrp4bvr9yCX16v9Zm7El3HxsWvzisuJRSsDCiJbwhQvUhZAoGBALnoHL/yBPtsl0YbQBeyeSrSJd4UHS44KLg5aVgYFt7BbdfwsAbd+RrA8uUB2tI0TyJyYrNAc+kiM9VFYglxvJuDCZLep9psczIYYCtpvvmO60c5u4p82QbIs5NS8/tX6s2SPcm3Ka8JtPzTq/jTZkyyzH9+1aDZrHY6zl9CDcio";

    //支付宝公钥
    private String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    //公网地址前缀
    private String commonUrlPrefix = "http://tjsanshao.free.idcfengye.com";

    //服务器异步通知页面路径
    private String notify_url = "/alipay/notify";

    //页面跳转同步通知页面路径
    private String return_url = "/alipay/return";

    //签名方式
    private String sign_type = "RSA";

    //字符编码格式
    private String charset = "utf-8";

    //支付宝网关
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getCommonUrlPrefix() {
        return commonUrlPrefix;
    }

    public void setCommonUrlPrefix(String commonUrlPrefix) {
        this.commonUrlPrefix = commonUrlPrefix;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }
}
