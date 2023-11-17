package com.inspur.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inspur.edu.entity.CourseDescription;
import com.inspur.edu.mapper.CourseDescriptionMapper;
import com.inspur.edu.service.CourseDescriptionService;
import org.springframework.stereotype.Service;

@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
