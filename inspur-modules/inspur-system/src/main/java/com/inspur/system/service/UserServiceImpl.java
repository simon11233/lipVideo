package com.inspur.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inspur.system.domain.User;
import com.inspur.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByName(String userName) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username",userName));
    }


    @Override
    public boolean insertUser(User user) {
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
