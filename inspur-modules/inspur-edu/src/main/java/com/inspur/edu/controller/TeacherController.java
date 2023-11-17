package com.inspur.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inspur.core.domain.R;
import com.inspur.core.exception.GuliException;
import com.inspur.core.utils.ExceptionUtil;
import com.inspur.core.web.controller.BaseController;
import com.inspur.core.web.domain.AjaxResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inspur.edu.entity.Course;
import com.inspur.edu.entity.Teacher;
import com.inspur.edu.entity.TeacherQuery;
import com.inspur.edu.service.CourseService;
import com.inspur.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author simon
 * @since 2023-09-07
 */
@Api(description="课程管理")
//@CrossOrigin
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("/{id}")
    public R removeBtId(@PathVariable String id) {
        return R.ok().success(teacherService.removeById(id));
    }

    @ApiOperation(value = "讲师信息列表")
    @GetMapping("/list")
    public R list(Teacher teacher){
        return R.ok().data("rows", teacherService.selectTeacherList(teacher));
    }

    @ApiOperation(value = "所有讲师")
    @GetMapping("/getList")
    public R getList(){
        return R.ok().data("rows", teacherService.list());
    }
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                TeacherQuery teacherQuery){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }
    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.save(teacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id",value = "讲师ID",required = true)@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item",teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(@ApiParam(name="id",value = "讲师ID",required = true)@PathVariable String id,
           @ApiParam(name = "teacher",value = "讲师对象",required = true)@RequestBody Teacher teacher){
            teacher.setId(id);
            teacherService.updateById(teacher);
            return R.ok();
    }

    @ApiOperation(value = "门户分页获取数据")
    @GetMapping(value = "/cms/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<Teacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "/cms/{id}")
    public R getByTeacherId(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        List<Course> courseList = courseService.selectByTeacherId(id);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}

