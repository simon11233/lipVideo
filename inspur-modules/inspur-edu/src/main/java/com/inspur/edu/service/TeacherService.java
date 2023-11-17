package com.inspur.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inspur.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.edu.entity.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author simon
 * @since 2023-09-07
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 查询预约列表
     *
     * @param teacher 教师
     * @return 教师集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);
    /**
     * 新增教师列表
     *
     * @param teacher 预约
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> pageListWeb(Page<Teacher> pageParam);

}
