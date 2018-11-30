package cn.tgw.common.utils;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

/*
 * @Project:tgw
 * @Description:md5 password utils
 * @Author:TjSanshao
 * @Create:2018-11-30 11:07
 *
 **/
public class MD5Utils {

    public static String tgwMD5(String password){
        return DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(("TGWPasswordWithMd5" + password).getBytes()).getBytes());
    }

}
