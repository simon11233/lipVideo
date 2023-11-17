package com.inspur.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inspur.core.exception.GuliException;
import com.inspur.edu.entity.Subject;
import com.inspur.edu.entity.ExcelSubjectData;
import com.inspur.edu.entity.SubjectNestedVo;
import com.inspur.edu.entity.SubjectVo;
import com.inspur.edu.listener.SubjectExcelListener;
import com.inspur.edu.mapper.SubjectMapper;
import com.inspur.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void importSubjectData(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<SubjectNestedVo> nestList() {
        ArrayList<SubjectNestedVo> nestArrayList =  new ArrayList<>();
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        wrapper.orderByAsc("sort","id");
        List<Subject> subjects = baseMapper.selectList(wrapper);

        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id",0);
        wrapper1.orderByAsc("sort","id");
        List<Subject> subjects1 = baseMapper.selectList(wrapper1);

        int sizi = subjects.size();
        for (int i=0;i<sizi;i++){
            Subject subject = subjects.get(i);
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject,subjectNestedVo);
            nestArrayList.add(subjectNestedVo);
            int size2 = subjects1.size();
            ArrayList<SubjectVo> subjectVoList = new ArrayList<>();
            for(int j=0;j<size2;j++){
                Subject subject2 = subjects1.get(j);
                if(subject.getId().equals(subject2.getParentId())){
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subject2,subjectVo);
                    subjectVoList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoList);
        }
        return nestArrayList;
    }
}
