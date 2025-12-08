package com.atom.personnel.dto.request;

import com.atom.personnel.entity.Gender;
import com.atom.personnel.entity.Position;

import java.io.Serializable;

public class EmployeeDto implements Serializable {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private String address;
    private Integer salary;
    private String position;
    private String join_date;
    private String performance;
    private String avatar_url;
    private String department;

    public EmployeeDto() {}

    public EmployeeDto(Long id, String name, Gender gender, Integer age, String phone, String email, String address, Integer salary, Position position, String join_date, String performance, String avatar_url, String department) {
        this.id = id;
        this.name = name;
        this.gender = gender.getDescription();
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.position = position.getDescription();
        this.join_date = join_date;
        this.performance = performance;
        this.avatar_url = avatar_url;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public String getJoin_date() {return join_date;}

    public String getPerformance() {
        return performance;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getDepartment() {
        return department;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", position='" + position + '\'' +
                ", join_date=" + join_date +
                ", performance='" + performance + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
