package com.inspur.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.edu.entity.*;

import java.util.List;
import java.util.Map;

public interface CourseService extends IService<Course> {

     String saveCourseInfo(CourseInfoVo courseInfoVo);
     CourseInfoVo getCourseInfoFormById(String id);
     void updateCourseInfoById(CourseInfoVo courseInfoVo);
     CoursePublishVo getCoursePublishVoById(String id);
     boolean publishCourseById(String id);
     IPage pageQuery(Page<Course> pageParam, CourseQuery courseQuery);
     boolean removeCourseById(String courseId);
     List<Course> selectByTeacherId(String teacherId);

     Map<String,Object> pageListWeb(Page<Course> pageParam, CourseQueryVo courseQuery);

     CourseWebVo selectInfoWebById(String courseId);
     void updatePageViewCount(String id);
}
