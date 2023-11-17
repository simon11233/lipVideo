package com.inspur.edu.mapper;

import com.inspur.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author simon
 * @since 2023-09-07
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    public List<Teacher> selectTeacherList(Teacher teacher);

    public int insertTeacher(Teacher teacher);
}
