package com.ganzhi.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("task")
public class Task  {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String taskName;

    private Integer taskTypeId;

    private String algorithmDocker;

    private String uploadImage;

    private Date createTime;

    private String userId;

}

