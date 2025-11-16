package com.ganzhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ganzhi.domain.Task;
import com.ganzhi.mapper.TaskMapper;
import com.ganzhi.service.TaskService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Override
    public ResponseResult getTask(Integer typeId) {
        //获取用户id
        Object id = StpUtil.getLoginId();

        LambdaQueryWrapper<Task> queryWrapper=new LambdaQueryWrapper<>();
        //根据选择的task_type_id获取用户所有Task（如果存在）
        if (!Objects.isNull(typeId)){
            queryWrapper.eq(Task::getTaskTypeId,typeId);
        }
        //根据用户id得到自己的Task
        queryWrapper.like(Task::getUserId,id);
        List<Task> taskList = taskMapper.selectList(queryWrapper);

        return ResponseResult.okResult(taskList);

    }
}

