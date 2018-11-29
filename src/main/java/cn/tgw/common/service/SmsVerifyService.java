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

}
