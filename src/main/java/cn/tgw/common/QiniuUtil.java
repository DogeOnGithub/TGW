package cn.tgw.common;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**

* @Description:    图片服务器工具

* @Author:         梁智发

* @CreateDate:     2018/11/26 0026 17:10

* @UpdateUser:     梁智发

* @UpdateDate:     2018/11/26 0026 17:10

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Component
@org.springframework.context.annotation.Configuration
public class QiniuUtil {
    /**
     * 返回一串云端访问地址；如果返回"error"表示不成功，否则就是成功
     */

    private static String accessKey="YBEYWaqwXhGNpNIuEOsuSyLtQ0i0b34gobjSYsKL";


    private static String secretKey="3BCpdcLZZ3wXw-l5psmu-D8RHjnmmlJnciUTeVuK";


    private static String bucket="images";


    private static String path="pih7n7d5x.bkt.clouddn.com";

    /**
     * 将图片上传到七牛云
     *
     * @param multipartFile
     *
     * @return
     */
    public static String uploadImg(MultipartFile multipartFile) throws IOException {

        FileInputStream file = (FileInputStream) multipartFile.getInputStream();

        String key = multipartFile.getOriginalFilename();
        key= UUID.randomUUID().toString()+key;
        System.out.println(key);



        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(file, key, upToken, null, null);
                    //解析上传成功的结果
                    DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                    String return_path = path + "/" + putRet.key;
                    return return_path;
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                        return "error";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        return "error";
    }
}
