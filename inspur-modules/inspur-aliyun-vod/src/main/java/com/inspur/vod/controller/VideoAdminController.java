package com.inspur.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.github.pagehelper.Page;
import com.inspur.core.domain.R;
import com.inspur.vod.service.VideoService;
import com.inspur.vod.util.AliyunVodSDKUtils;
import com.inspur.vod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="阿里云视频点播微服务")
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/admin/vod/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    /**
     *
     * @param  file 上传文件
     */
    @PostMapping("upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                        @RequestParam("file") MultipartFile file){
        String videoId = videoService.uploadVideo(file);
        return R.ok().message("上传成功").data("videoId",videoId);
    }

    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "视频Id",required = true)
                         @PathVariable String videoId){
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    @DeleteMapping("delete-batch")
    public R removeVideoList(@ApiParam(name = "videoId", value = "视频Id",required = true)
                         @RequestParam List videoIdList){
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    @GetMapping("{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
    }

}
