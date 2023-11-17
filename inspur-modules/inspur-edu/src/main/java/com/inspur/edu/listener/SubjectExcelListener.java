package com.inspur.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inspur.core.exception.GuliException;
import com.inspur.edu.entity.Subject;
import com.inspur.edu.entity.ExcelSubjectData;
import com.inspur.edu.service.SubjectService;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectService subjectService;
    public SubjectExcelListener (){}
    public SubjectExcelListener(SubjectService subjectService){
        this.subjectService = subjectService;
    }
    @Override
    public void invoke(ExcelSubjectData user, AnalysisContext analysisContext) {
        if(user==null){
            throw  new GuliException(20001,"添加失败");
        }
        Subject existOneSubject = this.existOneSubject(subjectService,user.getOneSubjectName());
        if(existOneSubject==null){
            existOneSubject = new Subject();
            existOneSubject.setTitle(user.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();


        Subject existTwoSubject = this.existTwoSubject(subjectService,user.getOneSubjectName(),pid);
        if(existTwoSubject==null){
            existTwoSubject = new Subject();
            existTwoSubject.setTitle(user.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }
    @Override
     public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
         System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        return subjectService.getOne(wrapper);
    }
    private Subject existTwoSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        return subjectService.getOne(wrapper);
    }


}
