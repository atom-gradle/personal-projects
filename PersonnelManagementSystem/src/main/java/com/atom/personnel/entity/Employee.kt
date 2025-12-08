package com.atom.personnel.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.validator.constraints.Range
import java.time.LocalDate

@Table(name = "employee")
@Entity
data class Employee(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

        @Column(name = "name",length = 20,nullable = false)
    var name : String = "",

        @Column(name = "gender", length = 2)
    @Enumerated(EnumType.STRING)
    var gender : Gender = Gender.MALE,

        @Column(name = "age")
    @Range(min = 18,max = 65, message = "age must be between 18 and 65")
    var age : Int = 18,

        @Column(name = "phone",length = 11)
    var phone : String = "",

        @Column(name = "email", length = 30)
    var email : String = "",

        @Column(name = "address", length = 50)
    var address : String = "",



        @Column(name = "salary")
    var salary : Int = 0,

        @Column(name = "position", length = 20)
    @Enumerated(EnumType.STRING)
    var position : Position = Position.STAFF,

        @Column(name = "join_date")
    var join_date : LocalDate = LocalDate.now(),

        @Column(name = "performance", length = 10)
    @Enumerated(EnumType.STRING)
    var performance : Performance = Performance.D,

        @Column(name = "avatar_url")
    var avatar_url : String = "",

    // 关联到部门实体（一对一）
        @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    var department: Department? = null

)

/*
update employee set avatar_url =
"https://gips3.baidu.com/it/u=2423925798,763306885&fm=3074&app=3074&f=PNG?w=2048&h=2048"
where id = 5;
*/

/*
-- 插入员工数据（50条）
INSERT INTO employee (id, name, gender, age, phone, email, address, salary, position, tenure, performance, head_image_url) VALUES
(1, '张三', 'MALE', 28, '13800138001', 'zhangsan@company.com', '北京市朝阳区', 15000, 'STAFF', 3, 'A', NULL),
(2, '李四', 'MALE', 32, '13800138002', 'lisi@company.com', '上海市浦东新区', 18000, 'MANAGER', 5, 'B', NULL),
(3, '王五', 'FEMALE', 26, '13800138003', 'wangwu@company.com', '广州市天河区', 12000, 'STAFF', 2, 'C', NULL),
(4, '赵六', 'MALE', 35, '13800138004', 'zhaoliu@company.com', '深圳市南山区', 22000, 'SENIOR_MANAGER', 8, 'A', NULL),
(5, '孙七', 'FEMALE', 29, '13800138005', 'sunqi@company.com', '杭州市西湖区', 16000, 'STAFF', 4, 'B', NULL),
(6, '周八', 'MALE', 40, '13800138006', 'zhouba@company.com', '成都市武侯区', 28000, 'DIRECTOR', 12, 'A', NULL),
(7, '吴九', 'FEMALE', 24, '13800138007', 'wujiu@company.com', '武汉市江汉区', 10000, 'INTERN', 1, 'C', NULL),
(8, '郑十', 'MALE', 31, '13800138008', 'zhengshi@company.com', '南京市鼓楼区', 19000, 'MANAGER', 6, 'B', NULL),
(9, '钱一', 'FEMALE', 27, '13800138009', 'qianyi@company.com', '西安市雁塔区', 13000, 'STAFF', 3, 'A', NULL),
(10, '孙二', 'MALE', 38, '13800138010', 'suner@company.com', '重庆市渝中区', 25000, 'SENIOR_MANAGER', 10, 'A', NULL),
(11, '李雷', 'MALE', 30, '13800138011', 'lilei@company.com', '北京市海淀区', 17000, 'STAFF', 4, 'B', NULL),
(12, '韩梅梅', 'FEMALE', 28, '13800138012', 'hanmeimei@company.com', '上海市徐汇区', 16500, 'STAFF', 3, 'A', NULL),
(13, '刘明', 'MALE', 33, '13800138013', 'liuming@company.com', '广州市越秀区', 20000, 'MANAGER', 7, 'B', NULL),
(14, '陈静', 'FEMALE', 29, '13800138014', 'chenjing@company.com', '深圳市福田区', 14500, 'STAFF', 3, 'C', NULL),
(15, '杨帆', 'MALE', 42, '13800138015', 'yangfan@company.com', '杭州市滨江区', 32000, 'DIRECTOR', 15, 'A', NULL),
(16, '黄蓉', 'FEMALE', 26, '13800138016', 'huangrong@company.com', '成都市锦江区', 12500, 'STAFF', 2, 'B', NULL),
(17, '郭靖', 'MALE', 34, '13800138017', 'guojing@company.com', '武汉市武昌区', 21000, 'SENIOR_MANAGER', 9, 'A', NULL),
(18, '林平', 'MALE', 25, '13800138018', 'linping@company.com', '南京市玄武区', 11000, 'INTERN', 1, 'C', NULL),
(19, '张伟', 'MALE', 31, '13800138019', 'zhangwei@company.com', '西安市碑林区', 18500, 'MANAGER', 5, 'B', NULL),
(20, '王芳', 'FEMALE', 28, '13800138020', 'wangfang@company.com', '重庆市江北区', 14000, 'STAFF', 3, 'A', NULL),
(21, '赵勇', 'MALE', 36, '13800138021', 'zhaoyong@company.com', '北京市东城区', 23000, 'SENIOR_MANAGER', 8, 'A', NULL),
(22, '刘婷', 'FEMALE', 27, '13800138022', 'liuting@company.com', '上海市长宁区', 15500, 'STAFF', 4, 'B', NULL),
(23, '陈强', 'MALE', 39, '13800138023', 'chenqiang@company.com', '广州市荔湾区', 26500, 'MANAGER', 11, 'B', NULL),
(24, '杨雪', 'FEMALE', 30, '13800138024', 'yangxue@company.com', '深圳市宝安区', 17500, 'STAFF', 5, 'C', NULL),
(25, '周涛', 'MALE', 45, '13800138025', 'zhoutao@company.com', '杭州市萧山区', 35000, 'DIRECTOR', 18, 'A', NULL),
(26, '吴敏', 'FEMALE', 29, '13800138026', 'wumin@company.com', '成都市青羊区', 16000, 'STAFF', 4, 'B', NULL),
(27, '郑军', 'MALE', 32, '13800138027', 'zhengjun@company.com', '武汉市洪山区', 19500, 'MANAGER', 6, 'A', NULL),
(28, '孙丽', 'FEMALE', 24, '13800138028', 'sunli@company.com', '南京市秦淮区', 10500, 'INTERN', 1, 'C', NULL),
(29, '钱刚', 'MALE', 37, '13800138029', 'qiangang@company.com', '西安市新城区', 24000, 'SENIOR_MANAGER', 9, 'B', NULL),
(30, '李娜', 'FEMALE', 31, '13800138030', 'lina@company.com', '重庆市南岸区', 18000, 'STAFF', 5, 'A', NULL),
(31, '王磊', 'MALE', 28, '13800138031', 'wanglei@company.com', '北京市西城区', 14500, 'STAFF', 3, 'B', NULL),
(32, '张敏', 'FEMALE', 33, '13800138032', 'zhangmin@company.com', '上海市静安区', 20500, 'MANAGER', 7, 'A', NULL),
(33, '刘洋', 'MALE', 26, '13800138033', 'liuyang@company.com', '广州市海珠区', 12000, 'STAFF', 2, 'C', NULL),
(34, '陈露', 'FEMALE', 35, '13800138034', 'chenlu@company.com', '深圳市龙岗区', 22500, 'SENIOR_MANAGER', 8, 'B', NULL),
(35, '杨光', 'MALE', 41, '13800138035', 'yangguang@company.com', '杭州市余杭区', 30000, 'DIRECTOR', 14, 'A', NULL),
(36, '周慧', 'FEMALE', 27, '13800138036', 'zhouhui@company.com', '成都市金牛区', 13500, 'STAFF', 3, 'A', NULL),
(37, '吴刚', 'MALE', 30, '13800138037', 'wugang@company.com', '武汉市硚口区', 17000, 'MANAGER', 5, 'B', NULL),
(38, '郑洁', 'FEMALE', 25, '13800138038', 'zhengjie@company.com', '南京市建邺区', 11500, 'INTERN', 1, 'C', NULL),
(39, '孙伟', 'MALE', 34, '13800138039', 'sunwei@company.com', '西安市莲湖区', 21500, 'MANAGER', 7, 'A', NULL),
(40, '钱芳', 'FEMALE', 29, '13800138040', 'qianfang@company.com', '重庆市沙坪坝区', 15000, 'STAFF', 4, 'B', NULL),
(41, '李强', 'MALE', 38, '13800138041', 'liqiang@company.com', '北京市丰台区', 25500, 'SENIOR_MANAGER', 10, 'A', NULL),
(42, '王静', 'FEMALE', 32, '13800138042', 'wangjing@company.com', '上海市虹口区', 19000, 'STAFF', 6, 'B', NULL),
(43, '张鹏', 'MALE', 40, '13800138043', 'zhangpeng@company.com', '广州市白云区', 27500, 'MANAGER', 12, 'A', NULL),
(44, '刘霞', 'FEMALE', 28, '13800138044', 'liuxia@company.com', '深圳市盐田区', 15500, 'STAFF', 3, 'C', NULL),
(45, '陈峰', 'MALE', 43, '13800138045', 'chenfeng@company.com', '杭州市富阳区', 33000, 'DIRECTOR', 16, 'A', NULL),
(46, '杨琳', 'FEMALE', 30, '13800138046', 'yanglin@company.com', '成都市成华区', 16500, 'STAFF', 4, 'B', NULL),
(47, '周健', 'MALE', 33, '13800138047', 'zhoujian@company.com', '武汉市汉阳区', 20000, 'MANAGER', 6, 'A', NULL),
(48, '吴婷', 'FEMALE', 26, '13800138048', 'wuting@company.com', '南京市雨花台区', 12500, 'INTERN', 2, 'C', NULL),
(49, '郑伟', 'MALE', 36, '13800138049', 'zhengwei@company.com', '西安市未央区', 23500, 'SENIOR_MANAGER', 9, 'B', NULL),
(50, '孙燕', 'FEMALE', 31, '13800138050', 'sunyan@company.com', '重庆市九龙坡区', 18500, 'STAFF', 5, 'A', NULL);
 */
