package com.ganzhi.controller;

import com.ganzhi.service.TaskReceiptService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskReceipt")
public class TaskReceiptController  {

    @Autowired
    private TaskReceiptService taskReceiptService;

    //创建任务回执
    @PostMapping("/create")
    public ResponseResult create(@RequestParam Integer taskId){
        return taskReceiptService.create(taskId);
    }


    //获取trId
    @GetMapping("/get")
    public ResponseResult get(@RequestParam Integer taskId){
        return taskReceiptService.get(taskId);
    }
}

