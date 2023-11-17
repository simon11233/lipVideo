package com.inspur.edu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inspur.core.domain.R;
import com.inspur.core.utils.StringUtils;
import com.inspur.core.web.controller.BaseController;
import com.inspur.edu.entity.*;
import com.inspur.edu.service.ChapterService;
import com.inspur.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description="课程管理")
//@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @ApiOperation(value = "新增课程")
    @PostMapping("save")
    public R saveCourseInfo(@ApiParam(name="CourseInfoForm",value = "课程基本信息",required = true)
                            @RequestBody CourseInfoVo courseInfoVo){
        String  id = courseService.saveCourseInfo(courseInfoVo);
        if(!StringUtils.isEmpty(id)){
            return R.ok().data("courseId",id);
        }else {
            return R.error().message("保存失败");
        }
    }
    @ApiOperation(value = "修改课程")
    @PostMapping("update")
    public R updateCourseInfo(@ApiParam(name="CourseInfoForm",value = "课程基本信息",required = true)
                            @RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok();
    }
    @ApiOperation(value = "根据ID查找课程")
    @GetMapping("getById/{id}")
    public R getById(@ApiParam(name="id",value = "课程ID",required = true)
                         @PathVariable String id){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoFormById(id);
        return  R.ok().data("item",courseInfoVo);
    }
    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
    @ApiParam(name = "id", value = "课程ID", required = true)
    @PathVariable String id){
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }
    @ApiOperation(value = "根据id发布课程")
    @GetMapping("publish-course/{id}")
    public R publishCourse(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            CourseQuery courseQuery){
        Page<Course> coursePage = new Page<>(page,limit);
        IPage iPage = courseService.pageQuery(coursePage, courseQuery);
        List records = iPage.getRecords();
        long total = iPage.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        boolean result = courseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
    @ApiOperation(value = "门户分页获取课程数据")
    @PostMapping(value = "/cms/{page}/{limit}")
    public R pageWebList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
            Page<Course> pageParam = new Page<>(page, limit);
            Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return R.ok().data(map);
    }

    @ApiOperation(value = "门户id获取详细课程信息")
    @GetMapping("/cms/{courseId}")
    public R selectInfoWebById(
            @ApiParam(name = "courseQuery", value = "查询对象", required = true)@PathVariable String courseId){
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);
        List<ChapterVo> chapterVos = chapterService.nestedList(courseId);
        return R.ok().data("course",courseWebVo).data("chapterList",chapterVos);
    }
}
