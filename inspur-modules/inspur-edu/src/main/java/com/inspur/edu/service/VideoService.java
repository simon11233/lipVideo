package com.inspur.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.edu.entity.Teacher;
import com.inspur.edu.entity.Video;
import com.inspur.edu.entity.VideoInfoForm;

import java.util.Map;

public interface VideoService extends IService<Video> {
    boolean getCountByChapterId(String chapterId);

    void saveVideoInfo(VideoInfoForm videoInfoForm);
    VideoInfoForm getVideoInfoFormById(String id);
    void updateVideoInfoById(VideoInfoForm videoInfoForm);
    boolean removeVideoById(String id);
    boolean removeByCourseId(String courseId);
    void removeVideo(String courseId);

    Map<String, Object> pageListWeb(Page<Video> pageParam);
}
