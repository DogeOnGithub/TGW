package cn.tgw.common.service;

//手机验证码业务接口
public interface SmsVerifyService {

    /*
     * @Description:测试异步业务方法
     * @Param:[]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:09:28
     **/
    void testAsync();

    /*
     * @Description:异步发送验证码，并记录到数据库中
     * @Param:[mobile, code]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:09:41
     **/
    public void sendMsgCodeAsync(String mobile, String code);

    /*
     * @Description:判断手机号是否可以发送验证码，比如今天的是否达到今天的限制次数
     * @Param:[mobile]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:10:55
     **/
    public boolean enableSend(String mobile);

    /*
     * @Description:校验手机验证码
     * @Param:[mobile, code]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:15:36
     **/
    public boolean checkCode(String mobile, String code);

    public void codeUsed(String mobile);

    public void verifyCodeQueueListener(String mobile);

}
