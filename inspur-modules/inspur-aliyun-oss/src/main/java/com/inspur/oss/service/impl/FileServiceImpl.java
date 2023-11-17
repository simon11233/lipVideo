package com.inspur.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.inspur.oss.service.FileService;
import com.inspur.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file,String host) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
//        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String uploadUrl = null;
        try {
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if(!ossClient.doesBucketExist(bucketName)){
                //创建bucket
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            InputStream inputStream = file.getInputStream();
            String filePath = new DateTime().toString("yyyy/MM/dd");

            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newName = uuid+fileType;
//            String fileUrl = fileHost+"/"+filePath+"/"+newName;
            String fileUrl = host+"/"+filePath+"/"+newName;
            ossClient.putObject(bucketName,fileUrl,inputStream);
            ossClient.shutdown();
            //获取url地址
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadUrl;
    }
}
