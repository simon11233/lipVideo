package com.inspur.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inspur.core.domain.R;
import com.inspur.core.utils.JwtUtils;
import com.inspur.core.utils.StringUtils;
import com.inspur.ucenter.entity.Comment;
import com.inspur.ucenter.entity.UcenterMember;
import com.inspur.ucenter.service.CommentService;
import com.inspur.ucenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ucenter/comment")
//@CrossOrigin
public class CommentFrontController {
    @Autowired
    private CommentService service;

    @Autowired
    private UcenterMemberService ucenterMemberService;
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            String courseId){
        Page<Comment> pageParam = new Page<>(page, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        service.page(pageParam,wrapper);
        List<Comment> records = pageParam.getRecords();
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
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("save")
    public R save(@RequestBody Comment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        UcenterMember uc = ucenterMemberService.getById(memberId);
        comment.setAvatar(uc.getAvatar());
        comment.setNickName(uc.getNickname());
        service.save(comment);
        return R.ok();
    }
}
