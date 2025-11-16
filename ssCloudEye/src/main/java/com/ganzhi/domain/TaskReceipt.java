package com.ganzhi.domain;

import java.util.Date;

import java.io.Serializable;

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
@TableName("task_receipt")
public class TaskReceipt  {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer taskId;

    private Date createTime;

}
/*
+-------------+----------+------+-----+---------+----------------+
| Field       | Type     | Null | Key | Default | Extra          |
+-------------+----------+------+-----+---------+----------------+
| id          | int      | NO   | PRI | NULL    | auto_increment |
| user_id     | int      | YES  |     | NULL    |                |
| task_id     | int      | YES  | MUL | NULL    |                |
| create_time | datetime | YES  |     | NULL    |                |
 */
