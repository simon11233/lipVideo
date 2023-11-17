package com.inspur.oss;

import com.aliyun.oss.OSSClient;
import org.junit.Test;

public class OssTest {
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    String accessKeyId ="LTAI5t6kM4yWVqL2YBvdHZwJ";
    String accessSecret = "kXQQoDTOEgymkqERbVuyigL6Rn7jgj";
    String bucketName = "inspur-file";

    @Test
    public void testCreateBucket(){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        ossClient.createBucket(bucketName);
        ossClient.shutdown();
    }

    @Test
    public void testExist(){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessSecret);
        boolean have = ossClient.doesBucketExist(bucketName);
        System.out.println(have);
    }



}
