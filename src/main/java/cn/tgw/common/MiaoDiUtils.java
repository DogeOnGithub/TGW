package cn.tgw.common;

import cn.tgw.config.TjSanshaoMiaoDiConfig;
import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * @Project:tgw
 * @Description:miao di tools
 * @Author:TjSanshao
 * @Create:2018-11-28 15:27
 *
 **/
public class MiaoDiUtils {

    private static String operation = "/industrySMS/sendSMS";

    private static String accountSid = TjSanshaoMiaoDiConfig.ACCOUNT_SID;

    /*
     * @Description:根据短信内容以及发送目标发送验证码
     * @Param:[smsContent, to]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:15:32
     **/
    public static void executeWithSmsContent(String smsContent, String to) throws UnsupportedEncodingException {
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){

        }
        String url = TjSanshaoMiaoDiConfig.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
                + HttpUtils.createCommonParam();

        // 提交请求
        String result = HttpUtils.miaoDiPost(url, body);
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
    public static void executeWithTemplateId(String templateId, String to, String code, int timeout) throws UnsupportedEncodingException {
        String url = TjSanshaoMiaoDiConfig.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&to=" + to + "&templateid=" + templateId + "&param=" + code + "," + timeout
                + HttpUtils.createCommonParam();

        // 提交请求
        String result = HttpUtils.miaoDiPost(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }

    /*
     * @Description:测试execute方法
     * @Param:[args]
     * @Return:void
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:15:39
     **/
    public static void main(String[] args){
//        try {
//            MiaoDiUtils.executeWithTemplateId("982267297", "13420120424", 30);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        System.out.println(JSON.parseObject("{\"respCode\":\"00000\",\"respDesc\":\"请求成功。\",\"failCount\":\"0\",\"failList\":[],\"smsId\":\"262d62f342c242aabed94ccec6e3b4c7\"}").getString("respCode"));;
    }
}
