package com.se.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.se.common.core.domain.AjaxResult;
import com.se.common.utils.file.FileUtils;
import com.se.common.utils.uuid.UUID;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.se.common.core.domain.AjaxResult.error;
import static com.se.common.core.domain.AjaxResult.success;

@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "uVc0IekrwazTQY67u9JqYJwJQk6lhe-DY40WRFH4";
    String SECRET_KEY = "bGD3f475nqVzuKKaF3fZ5EhWv0VZc4uoT2xD2Ylt";
    // 要上传的空间
    String bucketname = "csmineral";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    //华东 zone0 华北 zone1 华南 zone2
    Configuration cfg = new Configuration(Zone.zone2());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    //  域名 up-z2.qiniup.com
    private static String QINIU_IMAGE_DOMAIN = "http://sl0ntpo8c.hn-bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public AjaxResult saveImage(MultipartFile file)   {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return error("文件名异常");
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1) ;
            if(!FileUtils.isAvaliableType(fileExt))
            {
                return error("文件格式错误");
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = null;
            try {
                res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            } catch (IOException e) {
                return error("七牛云文件解析失败"+e);
            }
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return success( QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key"));
            } else {
//                logger.error("七牛异常:" + res.bodyString());
                return error("七牛异常" + res.bodyString());
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            return error("七牛异常" +  e.getMessage());
//            logger.error("七牛异常:" + e.getMessage());
        }
    }
}
