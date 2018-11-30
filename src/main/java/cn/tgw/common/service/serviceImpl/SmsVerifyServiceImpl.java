package cn.tgw.common.service.serviceImpl;

import cn.tgw.common.utils.MiaoDiUtils;
import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import cn.tgw.common.service.SmsVerifyService;
import cn.tgw.config.TjSanshaoMiaoDiConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/*
 * @Project:tgw
 * @Description:mobile verify code service impl
 * @Author:TjSanshao
 * @Create:2018-11-29 09:21
 *
 **/
@Service
public class SmsVerifyServiceImpl implements SmsVerifyService {

    //注入秒嘀工具类
    @Autowired
    private MiaoDiUtils miaoDiUtils;

    @Autowired
    private TjSanshaoMiaoDiConfig config;

    //注入tgw_sms_verify表持久化Mapper
    @Autowired
    private SmsVerifyMapper smsVerifyMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Async
    public void testAsync() {
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("async completed");
    }

    /*
     * @Description:异步发送验证码，并且在发送成功后记录验证码和手机号到数据库
     * @Param:[mobile, code]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:09:44
     **/
    @Override
    @Async
    public void sendMsgCodeAsync(String mobile, String code) {
        try {
            String result = miaoDiUtils.executeWithTemplateId(mobile, code);

            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getString("respCode").equals("00000")){
                //发送成功

                //发送成功后，将消息发送到消息队列，30分钟后监听到消息队列有消息后，将数据库中的status字段设置为过期
                rabbitTemplate.convertAndSend("tgw.verifycode.firstIn.exchange", "", mobile);

                //查询数据库中是否有mobile记录
                SmsVerify smsVerify = smsVerifyMapper.selectByMobile(mobile);
                if (smsVerify != null){
                    //mobile已存在，判断最后发送时间
                    Date sendTime = smsVerify.getSendTime();

                    Calendar lastSend = Calendar.getInstance();
                    lastSend.setTime(sendTime);

                    Calendar nowSend = Calendar.getInstance();
                    nowSend.setTime(new Date());

                    if (lastSend.get(Calendar.DATE) < nowSend.get(Calendar.DATE)){
                        //今天没有发送过验证码
                        smsVerify.setCode(code);
                        smsVerify.setStatus(new Byte("0"));
                        smsVerify.setSendTime(new Date());
                        smsVerify.setTimes(new Byte("1"));
                        smsVerifyMapper.updateCodeSmsVerify(smsVerify);
                    }else{
                        //今天发送过验证码
                        smsVerify.setCode(code);
                        smsVerify.setStatus(new Byte("0"));
                        smsVerify.setSendTime(new Date());
                        smsVerify.setTimes(new Byte(String.valueOf(smsVerify.getTimes().intValue() + 1)));
                        smsVerifyMapper.updateCodeSmsVerify(smsVerify);
                    }
                }else{
                    //手机号第一次发送验证码
                    SmsVerify newSmsVerify = new SmsVerify();
                    newSmsVerify.setMobile(mobile);
                    newSmsVerify.setCode(code);
                    newSmsVerify.setStatus(new Byte("0"));
                    newSmsVerify.setSendTime(new Date());
                    newSmsVerify.setTimes(new Byte("1"));
                    smsVerifyMapper.insertSmsVerify(newSmsVerify);
                }
            }else{
                //TODO 发送失败，记录到日志
                System.out.println("send false");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
     * @Description:判断手机号是否可以发送验证码
     * @Param:[mobile]
     * @Return:boolean
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:10:57
     **/
    @Override
    public boolean enableSend(String mobile) {
        SmsVerify smsVerify = smsVerifyMapper.selectByMobile(mobile);
        if (smsVerify == null){
            //新手机号，可以直接发送
            return true;
        }else{
            //已存在的手机号
            if (smsVerify.getTimes() < 5){
                //限制次数为5次，少于5次可以发送
                return true;
            }else{
                //已发送过5次，不可再发送
                return false;
            }
        }
    }

    @Override
    public boolean checkCode(String mobile, String code) {
        SmsVerify smsVerify = smsVerifyMapper.selectByMobile(mobile);
        if (smsVerify == null){
            //如果手机号不存在数据库中
            return false;
        }

        if (!smsVerify.getCode().equals(code)){
            //输入的验证码和数据库中的不一致
            return false;
        }

        if (smsVerify.getStatus() != 0){
            //验证码的状态不是未使用
            return false;
        }

        return true;
    }

    @Override
    @Async
    public void codeUsed(String mobile){
        SmsVerify smsVerify = new SmsVerify();
        smsVerify.setStatus(new Byte("1"));
        smsVerify.setMobile(mobile);
        smsVerifyMapper.updateCodeStatusSmsVerify(smsVerify);
    }

    /*
     * @Description:该方法监听消息队列，将数据库中的验证码设置为过期
     * @Param:[mobile]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-29
     * @Time:16:39
     **/
    @RabbitListener(queues = {"tgw.verifycode.queue"})
    public void verifyCodeQueueListener(String mobile){
        SmsVerify smsVerify = smsVerifyMapper.selectByMobile(mobile);

        if (smsVerify == null){
            return;
        }

        //2表示验证码已过期
        smsVerify.setStatus(new Byte("2"));

        smsVerifyMapper.updateCodeStatusSmsVerify(smsVerify);
    }
}
