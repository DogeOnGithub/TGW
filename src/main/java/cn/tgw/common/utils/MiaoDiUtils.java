package cn.tgw.common.utils;

import cn.tgw.common.utils.HttpUtils;
import cn.tgw.config.TjSanshaoMiaoDiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * @Project:tgw
 * @Description:miao di tools
 * @Author:TjSanshao
 * @Create:2018-11-28 15:27
 *
 **/
@Component
public class MiaoDiUtils {

    @Autowired
    private TjSanshaoMiaoDiConfig config;

    @Autowired
    private HttpUtils miaodiHttpUtils;

    /*
     * @Description:根据短信内容以及发送目标发送验证码
     * @Param:[smsContent, to]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:15:32
     **/
    public void executeWithSmsContent(String smsContent, String to) throws UnsupportedEncodingException {
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){

        }
        String url = config.getBaseUrl() + config.getOperation();
        String body = "accountSid=" + config.getAccountId() + "&to=" + to + "&smsContent=" + tmpSmsContent
                + miaodiHttpUtils.createCommonParam();

        // 提交请求
        String result = miaodiHttpUtils.miaoDiPost(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }

    /*
     * @Description:根据信息模板以及发送目标发送验证码
     * @Param:[templateId, to, code, timeout]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:16:35
     **/
    public String executeWithTemplateId(String to, String code) throws UnsupportedEncodingException {
        String url = config.getBaseUrl() + config.getOperation();
        String body = "accountSid=" + config.getAccountId() + "&to=" + to + "&templateid=" + config.getTemplateId() + "&param=" + code + "," + config.getTimeout()
                + miaodiHttpUtils.createCommonParam();

        // 提交请求
        String result = miaodiHttpUtils.miaoDiPost(url, body);

        return result;
    }
}
