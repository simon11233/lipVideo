package com.inspur.system.controller;


import com.inspur.core.web.controller.BaseController;
import com.inspur.core.web.domain.AjaxResult;
import com.inspur.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author simon
 * @date 2023/9/1 15:12:32
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     *
     */
    @GetMapping(value = "/{username}")
    public AjaxResult getName(@PathVariable("username") String userName) {
        return  success(userService.getUserByName(userName));
    }
}
