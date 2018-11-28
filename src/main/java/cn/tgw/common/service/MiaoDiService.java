package cn.tgw.common.service;

public interface MiaoDiService {

    /*
     * @Description:根据template id 发送验证码
     * @Param:[templateId, to, Code, timeout]
     * @Return:java.lang.String
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:21:30
     **/
    public String executeWithTemplateId(String to, String code) throws Exception;

    /*
     * @Description:生成指定长度的验证码
     * @Param:[length]
     * @Return:java.lang.String
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:21:31
     **/
    public String generateCode(int length);

}
