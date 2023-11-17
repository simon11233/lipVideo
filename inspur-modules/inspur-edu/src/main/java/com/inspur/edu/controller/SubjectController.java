package com.inspur.edu.controller;

import com.inspur.core.domain.R;
import com.inspur.edu.entity.SubjectNestedVo;
import com.inspur.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="课程分类管理")
@RestController
//@CrossOrigin
@RequestMapping("edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.importSubjectData(file,subjectService);
        return R.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public R nestList(){
        List<SubjectNestedVo> subjectNestedVos = subjectService.nestList();
        return R.ok().data("items",subjectNestedVos);
    }
}
