package com.ganzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ganzhi.domain.User;
import com.ganzhi.util.ResponseResult;
import org.springframework.stereotype.Service;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2025-05-11 15:05:52
 */
@Service
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult getUserInfo();

}

