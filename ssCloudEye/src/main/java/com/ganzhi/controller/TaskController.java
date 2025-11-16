package com.ganzhi.controller;

import com.ganzhi.service.TaskService;
import com.ganzhi.util.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //获取用户任务
    @GetMapping("/getTask")
    public ResponseResult getTask(@RequestParam(required = false) Integer typeId){
        return taskService.getTask(typeId);
    }

}
