package com.inspur.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.edu.entity.Subject;
import com.inspur.edu.entity.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface SubjectService extends IService<Subject> {


    void importSubjectData(MultipartFile file,SubjectService subjectService);

    List<SubjectNestedVo> nestList();
}
