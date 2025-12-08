package com.atom.personnel.dto.response;

public interface EmployeeDtoProjection {
    Long getId();
    String getName();
    String getGender();
    Integer getAge();
    String getPhone();
    String getEmail();
    String getAddress();
    Integer getSalary();
    String getPosition();
    String getPerformance();
    String getAvatarUrl();
    String getDepartment();
    String getJoinDate();
}