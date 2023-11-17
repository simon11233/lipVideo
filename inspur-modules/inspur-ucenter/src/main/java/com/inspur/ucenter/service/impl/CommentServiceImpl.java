package com.inspur.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inspur.ucenter.entity.Comment;
import com.inspur.ucenter.mapper.CommentMapper;
import com.inspur.ucenter.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


}
