package com.inspur.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
      * 文件上传至阿里云
      * @param file
      * @return
      */
     String upload(MultipartFile file,String host);
}
