package com.atom.personnel.entity

import jakarta.annotation.Generated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table(name = "department")
@Entity
data class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", length = 10, nullable = false)
    var name: String = "",

    @Column(name = "employee_amount")
    var employee_amount: Int = 0,

    @Column(name = "created_at")
    var createdAt: LocalDate = LocalDate.now(),

    // 关联到员工实体（多对一）
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    var manager : Employee?= null

)

/*
-- 批量插入部门数据
INSERT INTO department (id, name, employee_amount, created_at, manager_id) VALUES
(1, '技术部', 15, '2020-03-15 09:00:00', 2),
(2, '市场部', 10, '2019-06-20 10:30:00', 8),
(3, '人力资源部', 8, '2021-01-10 08:45:00', 19),
(4, '财务部', 6, '2018-09-05 14:20:00', 29),
(5, '销售部', 12, '2020-11-30 11:15:00', 39),
(6, '研发部', 18, '2019-04-22 13:45:00', 6),
(7, '行政部', 5, '2022-02-18 16:00:00', 13),
(8, '产品部', 9, '2021-08-12 10:00:00', 23),
(9, '设计部', 7, '2020-05-25 15:30:00', 32),
(10, '运营部', 11, '2021-03-08 09:45:00', 43);
 */
