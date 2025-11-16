package com.ganzhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ganzhi.domain.Task;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
