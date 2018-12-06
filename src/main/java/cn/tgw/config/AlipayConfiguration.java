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
    private String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHpyyuRSfoN25p1I/TqVwimOXl2FLiwh0AyYMJ/iIK7v6a5X3zX09nrbUfgaKQqSmuv7tXTtNBpXx8FpgMbV4QPfo5+zHHkxJutY2FMz9EInXX7vPicCSR95fuVPfCpPmQvhoZmMxtMh765rSLegmJLAJkpOLHOBnNTc69zYZ9imHyRpXXZhiIPcsCzTwe6aSebngYMEuXiijWanMpNp8jxlSGgjlT7cNiNDY8/LVFJ1IWSnxK0srm6Tuanu4+G9uE+MXR8LUAmtozf0GGsrnV6wg7wBXrOWxlBqpqRHLSxWB6iP5lD66FANh8P9wXOCc9Do3nJ7garrPAkwJYrHBdAgMBAAECggEAP+q4Ni5zoTclxQHE7dk5YuYaK6Q+nNPSHy9kIS3XzjJHFmXEFcb8j8+fva9t0OwnKQe+vSvCHDETklJVaR0WURLWafuOKztdWk4LOYp3haOWoIOiVmraelSOskFHdGwGbZ0+k9VEFO1H18FGKaTvKpgJSBk1dNvz/rMbLc5/cO7sHj/BP2Qxf7vqvDIIR7m45HQ4H0O/pLcXFhjwTAlgWQ2y6PgavCb+jZsVCh4YHgWF7J+Nseuhoos3WzycldIMiJ+kRbySeMeCOZHouiuf1kOxmzWoHRZ7Ebh9QhvGdxkv/pA2LUwLwxGtJafhNtLbChuHmZf+9Z46/jMfpWLPIQKBgQD4AVpoi1qD39JYun1YjQN9urB4n4Jc0zVwDSum9LhN77i8SVMe92qkkbG8BQFsO7P7a/SzmbYsALJYY/qqZOEnRUD0IlcPLq0nj88EKiH+MTNRudVAn7ON2AQz4udBS0SIgsucsJ2ughFybWHXpMxEsU0HSJ0PnMYk1GlUO8VA9QKBgQDOFsww0k4qa5DQXfK8nQn68AcaNhKrZY2FtVHmgwa/9qRuJdeiSHsIy66s9lw2Figd6hHqTR3MJw4ZXtV2th4pEfjwmZcuNEwgEudTbuNDkdXdTBr9Fldqj42MX9fJjK5Uaf4EKW5EAlT5QMun2nBBN8pWw3GmiDdlo7iS7xSwyQKBgQDHKKcGr2nn3xTXZiOEufEkxYgzxbx1eLNAIYN9lqZvB+RtZ+DVTFEaiVmWqNpkpWk6rY4bsIOnwIN4EgnZThu1YprvsqgBLDWnQIg5SvBzZeVVawuC0vzpGqD9Xejh+WS2z3pdgUOPQSprnsVk3BWh0FQQVlKyl+5S4zUWjM0yBQKBgQCTQm0DUbLy0su2Z/sj1R6lhU+95fdvK3B1oztzeBqhnm6eGkI7vojTnx8gjiUTrL9bqL7GafYpw5pqfH4Rhk6E0EfOPCFvrYTbwkYzMuAHjzR/HXuHbqhmB0+Ah5ecd/61aMtOVABrbjMjq64aMZbWszPMzXjvIxRadyNTMqzZKQKBgCUYeanz5dMflg2Li4QPB5sm4LT/DpRoIQB7uC2BcUN4wh1+MBY65Dsx7wKZlykcYQ8Fg37TxpFLosNMd04/+9hdkOFYJWGkhOxwBSur5gmKcjwWTA1hUL2RCnnTe0x/hDrKNENZIlofHFHjokHZCW0VDys8dm6FywMNgt6pRzjw";

    //支付宝公钥
    private String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    //公网地址前缀
    private String commonUrlPrefix = "http://tjsanshao.free.idcfengye.com";

    //服务器异步通知页面路径
    private String notify_url = "/common/test/notify_url";

    //页面跳转同步通知页面路径
    private String return_url = "/common/test/return_url";

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
