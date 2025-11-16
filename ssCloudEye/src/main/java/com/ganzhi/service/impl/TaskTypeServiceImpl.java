package com.ganzhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ganzhi.domain.TaskType;
import com.ganzhi.mapper.TaskTypeMapper;
import com.ganzhi.service.TaskTypeService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Service("taskTypeService")
public class TaskTypeServiceImpl extends ServiceImpl<TaskTypeMapper, TaskType> implements TaskTypeService {

    @Autowired
    private TaskTypeMapper taskTypeMapper;

    @Override
    public ResponseResult getAll(Integer id) {
        LambdaQueryWrapper<TaskType> queryWrapper= new LambdaQueryWrapper<>();
        if (!Objects.isNull(id)){
            queryWrapper.eq(TaskType::getId,id);
        }
        List<TaskType> taskTypes = taskTypeMapper.selectList(queryWrapper);

        return ResponseResult.okResult(taskTypes);

    }
}
