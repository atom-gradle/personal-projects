package com.ganzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ganzhi.domain.TaskType;
import com.ganzhi.util.ResponseResult;
import org.springframework.stereotype.Service;


/**
 * (TaskType)表服务接口
 *
 * @author makejava
 * @since 2025-05-18 18:16:17
 */
@Service
public interface TaskTypeService extends IService<TaskType> {

    ResponseResult getAll(Integer id);
}

