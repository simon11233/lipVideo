package com.inspur.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.edu.entity.Chapter;
import com.inspur.edu.entity.ChapterVo;
import java.util.List;

public interface ChapterService extends IService<Chapter> {
    List<ChapterVo> nestedList(String courseId);
    boolean removeChapterById(String id);
    boolean removeByCourseId(String courseId);
}
