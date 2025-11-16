package com.ganzhi.controller;

import com.ganzhi.domain.User;

import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ganzhi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }

    //获取用户个人信息
    @GetMapping ("/getUserInfo")
    public ResponseResult getUserInfo() {
        return userService.getUserInfo();
    }

}
