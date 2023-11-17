package com.inspur.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inspur.edu.entity.Course;
import com.inspur.edu.entity.CoursePublishVo;
import com.inspur.edu.entity.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getCoursePublishVoById(String id);

    CourseWebVo selectInfoWebById(@Param("courseId") String courseId);
}
