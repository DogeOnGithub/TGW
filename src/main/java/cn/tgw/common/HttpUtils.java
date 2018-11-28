package cn.tgw.common;

import cn.tgw.config.TjSanshaoMiaoDiConfig;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Project:tgw
 * @Description:http request tools
 * @Author:TjSanshao
 * @Create:2018-11-28 15:13
 *
 **/
public class HttpUtils {

    /*
     * @Description:该方法用于使用秒嘀，构造通用参数timestamp、sig和respDataType
     * @Param:[]
     * @Return:java.lang.String
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:15:19
     **/
    public static String createCommonParam() throws UnsupportedEncodingException {
        //构建自定义格式的时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = simpleDateFormat.format(new Date());

        //秒嘀签名
        String sig = DigestUtils.md5DigestAsHex((TjSanshaoMiaoDiConfig.ACCOUNT_SID + TjSanshaoMiaoDiConfig.AUTH_TOKEN + timeStamp).getBytes("UTF-8"));

        return "&timestamp=" + timeStamp + "&sig=" + sig + "&respDataType=" + TjSanshaoMiaoDiConfig.RESP_DATA_TYPE;
    }

    /*
     * @Description:发送post请求
     * @Param:[url, body]
     * @Return:java.lang.String
     * @Author:TjSanshao
     * @Date:2018-11-28
     * @Time:15:23
     **/
    public static String miaoDiPost(String url, String body){
        System.out.println("url:" + System.lineSeparator() + url);
        System.out.println("body:" + System.lineSeparator() + body);

        String result = "";
        try
        {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 设置连接参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 提交数据
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            // 读取返回数据
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            boolean firstLine = true; // 读第一行不加换行符
            while ((line = in.readLine()) != null)
            {
                if (firstLine)
                {
                    firstLine = false;
                } else
                {
                    result += System.lineSeparator();
                }
                result += line;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
