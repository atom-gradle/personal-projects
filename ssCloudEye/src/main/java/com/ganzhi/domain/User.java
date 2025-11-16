package com.ganzhi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String name;

    private String studentId;

    private String student_id;

    private String phone;
}

/*
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | int          | NO   | PRI | NULL    | auto_increment |
| username   | varchar(50)  | YES  | UNI | NULL    |                |
| password   | varchar(50)  | YES  |     | NULL    |                |
| name       | varchar(50)  | YES  |     | NULL    |                |
| student_id | varchar(500) | YES  |     | NULL    |                |
| phone      | text         | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+

+----+----------+----------+--------+---------------+-------------+
| id | username | password | name   | student_id    | phone       |
+----+----------+----------+--------+---------------+-------------+
|  1 | wym      | 20041015 | 王一鸣 | 2023317240219 | 13163103789 |
|  2 | man      | 123      | kobe   | 202111056     | 1518547892  |
|  3 | admin    | 123      | king   | 911           | 15465332564 |
+----+----------+----------+--------+---------------+-------------+

*/