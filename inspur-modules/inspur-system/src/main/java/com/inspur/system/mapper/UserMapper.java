package com.inspur.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inspur.system.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
