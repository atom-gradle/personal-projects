package com.ganzhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ganzhi.domain.TaskReceipt;
import com.ganzhi.mapper.TaskReceiptMapper;
import com.ganzhi.service.TaskReceiptService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Objects;

@Service("taskReceiptService")
public class TaskReceiptServiceImpl extends ServiceImpl<TaskReceiptMapper, TaskReceipt> implements TaskReceiptService {

    @Autowired
    private TaskReceiptMapper taskReceiptMapper;

    @Override
    public ResponseResult create(Integer taskId) {
        //判断taskId是否为空
        if (taskId==null){
            return ResponseResult.okResult(400,"任务id不能为空");
        }

        //获取用户id
        Object id = StpUtil.getLoginId();

        //判断是否重复获取
        LambdaQueryWrapper<TaskReceipt> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskReceipt::getUserId,id);
        queryWrapper.eq(TaskReceipt::getTaskId,taskId);
        TaskReceipt taskReceipt = taskReceiptMapper.selectOne(queryWrapper);
        if (!Objects.isNull(taskReceipt)){
            return new ResponseResult(400,"禁止重复创建回执id");
        }

        //上传回执信息，返回回执id（用于数据收集）
        TaskReceipt receipt = new TaskReceipt(null, Integer.parseInt((String) id), taskId, new Date());
        taskReceiptMapper.insert(receipt);
        int tr_id = receipt.getId(); // 这里获取自增ID

        return ResponseResult.okResult(tr_id);
    }

    @Override
    public ResponseResult get(Integer taskId) {
        //判断taskId是否为空
        if (taskId==null) {
            return ResponseResult.okResult(400,"任务id不能为空");
        }

        //获取用户id
        Object id = StpUtil.getLoginId();

        //获取tr_id并返回
        LambdaQueryWrapper<TaskReceipt> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskReceipt::getTaskId,taskId);
        queryWrapper.eq(TaskReceipt::getUserId,id);
        TaskReceipt taskReceipt = taskReceiptMapper.selectOne(queryWrapper);
            //非空判断
        if (Objects.isNull(taskReceipt)){
            return ResponseResult.okResult(400,"搜索不到");
        }
        Integer tr_id=taskReceipt.getId();

        return ResponseResult.okResult(tr_id);

    }

}
