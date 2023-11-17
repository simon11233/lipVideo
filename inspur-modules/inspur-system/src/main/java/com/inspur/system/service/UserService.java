package com.inspur.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.system.domain.User;

public interface UserService extends IService<User> {
     public User getUserByName(String userName);

     public boolean  insertUser(User user);
}
