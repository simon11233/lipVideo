package com.inspur.oss.controller;

import com.inspur.core.domain.R;
import com.inspur.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 */
@Api(description="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
        @PostMapping("upload")
    public R uoload(@ApiParam(name="file",value = "文件",required = true)
                    @RequestParam("file")MultipartFile file,
                    @ApiParam(name = "host", value = "文件上传路径", required = false)String  host){
        String upload = fileService.upload(file,host);
        return R.ok().message("上次成功").data("url",upload);
    }
}
