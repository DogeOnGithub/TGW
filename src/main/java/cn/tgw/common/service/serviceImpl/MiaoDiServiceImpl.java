package cn.tgw.common.service.serviceImpl;

import cn.tgw.common.utils.MiaoDiUtils;
import cn.tgw.common.service.MiaoDiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/*
 * @Project:tgw
 * @Description:miaodi service impl,该类中的方法均是同步方法，如果要异步发送验证码，使用SmsVerifyService
 * @Author:TjSanshao
 * @Create:2018-11-28 21:32
 *
 **/
@Service
public class MiaoDiServiceImpl implements MiaoDiService {

    @Autowired
    private MiaoDiUtils miaoDiUtils;

    @Override
    public String executeWithTemplateId(String to, String code) throws Exception {
        return miaoDiUtils.executeWithTemplateId(to, code);
    }

    @Override
    public String generateCode(int length) {

        //生成验证码的字符集
        String sources = "0123456789";

        Random random = new Random();
        StringBuffer code = new StringBuffer();

        //生成指定长度的验证码
        for (int i = 0; i < length; i++){
            code.append(sources.charAt(random.nextInt(9)));
        }

        return code.toString();
    }
}
