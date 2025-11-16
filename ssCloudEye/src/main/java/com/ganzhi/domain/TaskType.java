package com.ganzhi.domain;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TaskType)表实体类
 *
 * @author makejava
 * @since 2025-05-18 18:16:16
 */
@SuppressWarnings("serial")
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("task_type")
public class TaskType  {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String taskType;
}

/*
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| id        | int          | NO   | PRI | NULL    | auto_increment |
| task_type | varchar(100) | YES  |     | NULL    |                |
+-----------+--------------+------+-----+---------+----------------+
 */
