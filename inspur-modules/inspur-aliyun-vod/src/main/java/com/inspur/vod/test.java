package com.inspur.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.google.gson.Gson;
import com.inspur.vod.util.AliyunVodSDKUtils;
import org.junit.Test;

import java.util.List;


public class test {
    private final String accessKeyId = "LTAI5tEUs31wUpgVujdLCFmw";
    private final String accessKeySecret = "sRSuMGmjWgcCOj6gae4igIUIdIKlz2";

//获取视频播放凭证
    @Test
    public void testGetVideoPlayAuth() throws ClientException {
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("23b02ba0785771ee80554531959c0102");
        response = client.getAcsResponse(request);
        //输出请求结果
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");

    }

    //获取视频播放地址
    @Test
    public void testGetPlayInfo() throws ClientException {
        IAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("23b02ba0785771ee80554531959c0102");
        GetPlayInfoResponse response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        playInfoList.forEach(playInfo -> System.out.println("视频地址: "+playInfo.getPlayURL()));
    }
    @Test
    public void VideoTest(){
        //1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "The Finals";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "E:/商城视频/final.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        // 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(2*1024 * 1024L);
        // 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）
        request.setTaskNum(1);
        //是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，
        //可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。注意: 断点续传开启后，
        // 会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploadVideo = new UploadVideoImpl();
        UploadVideoResponse response = uploadVideo.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情
况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
             System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }
}
