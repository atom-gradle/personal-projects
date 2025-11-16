package com.ganzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ganzhi.domain.Task;
import com.ganzhi.util.ResponseResult;


/**
 * (Task)表服务接口
 *
 * @author makejava
 * @since 2025-05-18 18:16:55
 */
public interface TaskService extends IService<Task> {

    ResponseResult getTask(Integer typeId);
}

