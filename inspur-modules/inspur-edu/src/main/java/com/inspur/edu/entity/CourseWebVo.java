package com.inspur.edu.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseWebVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private BigDecimal price;

    private Integer lessonNum;

    private String cover;

    private Integer buyCount;

    private Integer viewCount;

    private String description;

    private String teacherId;

    private String teacherName;

    private String avatar;

    private String subjectLevelOneId;

    private String subjectLevelOne;

    private String subjectLevelTwoId;

    private String subjectLevelTwo;


}
