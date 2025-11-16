package com.ganzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ganzhi.domain.TaskReceipt;
import com.ganzhi.util.ResponseResult;


/**
 * (TaskReceipt)表服务接口
 *
 * @author makejava
 * @since 2025-05-18 18:17:15
 */

public interface TaskReceiptService extends IService<TaskReceipt> {

    ResponseResult create(Integer taskId);

    ResponseResult get(Integer taskId);
}

