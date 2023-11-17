package com.inspur.edu.controller;

import com.inspur.core.domain.R;
import com.inspur.core.web.controller.BaseController;
import com.inspur.edu.entity.Chapter;
import com.inspur.edu.entity.ChapterVo;
import com.inspur.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(description="课程章节管理")
//@CrossOrigin
@RestController
@RequestMapping("/edu/chapter")
public class ChapterAdminController extends BaseController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
    @ApiParam(name = "courseId", value = "课程ID", required = true)
    @PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items",chapterVoList);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter) {
        chapter.setId(id);
        chapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        boolean result = chapterService.removeChapterById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}
