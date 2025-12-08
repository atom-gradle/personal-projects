package com.atom.personnel.dto.response;

import java.io.Serializable;

public class DepartmentResponse implements Serializable {

    private String departmentName;
    private String createdTime;
    private String managerName;
    private Long employeeCount;

    public DepartmentResponse() {}

    public DepartmentResponse(String departmentName, String createdTime, String managerName, Long employeeCount) {
        this.departmentName = departmentName;
        this.createdTime = createdTime;
        this.managerName = managerName;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getManagerName() {
        return managerName;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return "DepartmentResponse{" +
                "departmentName='" + departmentName + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", managerName='" + managerName + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
