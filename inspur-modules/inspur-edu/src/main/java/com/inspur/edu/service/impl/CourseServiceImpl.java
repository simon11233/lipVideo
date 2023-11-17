package com.inspur.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inspur.core.exception.GuliException;
import com.inspur.core.utils.StringUtils;
import com.inspur.edu.entity.*;
import com.inspur.edu.mapper.CourseMapper;
import com.inspur.edu.service.ChapterService;
import com.inspur.edu.service.CourseDescriptionService;
import com.inspur.edu.service.CourseService;
import com.inspur.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        course.setStatus(Course.COURSE_NORMAL);
        BeanUtils.copyProperties(courseInfoVo,course);
        boolean save = this.save(course);
        if (!save){
            throw new GuliException(20001,"课程信息保存失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if(!resultDescription){
            throw new GuliException(20001,"课程详情信息保存失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoFormById(String id) {
        Course course = this.getById(id);
        if(course==null){
            throw new GuliException(20001,"数据不存在");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if(courseDescription==null){
            throw new GuliException(20001,"数据不存在");
        }
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        boolean result = this.updateById(course);
          if(!result){
              throw new GuliException(20001,"课程保存失败");
          }
        CourseDescription courseDescription = new CourseDescription();
          courseDescription.setDescription(courseInfoVo.getDescription());
          courseDescription.setId(courseInfoVo.getId());
        boolean save = courseDescriptionService.save(courseDescription);
        if(!save){
            throw new GuliException(20001,"课程保存失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public IPage pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if(courseQuery==null){
            return baseMapper.selectPage(pageParam,wrapper);
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.ge("subject_id", subjectId);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public boolean removeCourseById(String courseId) {
        videoService.removeByCourseId(courseId);
        chapterService.removeByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        Integer result = baseMapper.deleteById(courseId);
        return null != result && result > 0;
    }

    @Override
    public List<Course> selectByTeacherId(String teacherId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        wrapper.orderByDesc("gmt_modified");
        List<Course> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam, CourseQueryVo courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        try {
            if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
                wrapper.eq("subject_parent_id",courseQuery.getSubjectParentId());
            }

            if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
                wrapper.eq("subject_id", courseQuery.getSubjectId());
            }
            if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
                wrapper.orderByDesc("gmt_create");
            }
            if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
                wrapper.orderByAsc("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseMapper.selectPage(pageParam, wrapper);
        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String courseId) {
        this.updatePageViewCount(courseId);
        return baseMapper.selectInfoWebById(courseId);
    }

    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
