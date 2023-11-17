package com.inspur.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inspur.core.domain.R;
import com.inspur.edu.entity.Course;
import com.inspur.edu.entity.Teacher;
import com.inspur.edu.service.CourseService;
import com.inspur.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/edu/index")
public class IndexController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    @Cacheable(value = "index", key = "'selectList'")
    public R index(){
        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 8");
        List<Course> courseList = courseService.list(wrapper1);

        QueryWrapper<Teacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapper2);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }


}
