package com.ganzhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ganzhi.domain.User;
import com.ganzhi.mapper.UserMapper;
import com.ganzhi.util.RedisCache;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ganzhi.service.UserService;

import java.io.Serializable;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //0.不许为空
        if (user.getPassword()==null || user.getUsername()==null){
            return ResponseResult.okResult(400,"用户名或密码不能为空");
        }

        //1.通过username获取用户
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User user1 = userMapper.selectOne(queryWrapper);

        //2.判断
        if (Objects.isNull(user1)){
            return ResponseResult.okResult(400,"用户名错误");
        }
        if (!user1.getPassword().equals(user.getPassword())){
            return ResponseResult.okResult(400,"密码错误");
        }

        //3.添加token
        StpUtil.login(user1.getId());
        String token = StpUtil.getTokenValueByLoginId(user1.getId());

        return ResponseResult.okResult(token);
    }

    @Override
    public ResponseResult getUserInfo() {
        Object id =StpUtil.getLoginId();
        //获取redis中的个人信息
        User user = redisCache.getCacheObject((String) id);
        //如果不存在获取数据库中的
        if (Objects.isNull(user)) {
            user = getById((Serializable) id);
        }
        return ResponseResult.okResult(user);
    }
}
