package cn.tgw.common.service.serviceImpl;

import cn.tgw.common.MiaoDiUtils;
import cn.tgw.common.mapper.SmsVerifyMapper;
import cn.tgw.common.model.SmsVerify;
import cn.tgw.common.service.SmsVerifyService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    //注入tgw_sms_verify表持久化Mapper
    @Autowired
    private SmsVerifyMapper smsVerifyMapper;

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
}
