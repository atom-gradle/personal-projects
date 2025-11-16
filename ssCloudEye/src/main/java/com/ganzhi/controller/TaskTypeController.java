package com.ganzhi.controller;

import com.ganzhi.service.TaskTypeService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskType")
public class TaskTypeController  {

    @Autowired
    private TaskTypeService taskTypeService;

    @GetMapping("/getType")
    public ResponseResult getAll(@RequestParam(required = false) Integer id){
        return taskTypeService.getAll(id);
    }

}

