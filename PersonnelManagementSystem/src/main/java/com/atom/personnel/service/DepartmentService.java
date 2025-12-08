package com.atom.personnel.service;

import com.atom.personnel.dto.response.DepartmentResponse;
import com.atom.personnel.dto.response.DepartmentResponseProjection;
import com.atom.personnel.entity.Department;
import com.atom.personnel.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<DepartmentResponse> getAllDepartments() {
        List<DepartmentResponseProjection> projs = departmentRepository.getAllDepartments();
        List<DepartmentResponse> resps = convertDtos(projs);
        return resps;
    }

    private List<DepartmentResponse> convertDtos(List<DepartmentResponseProjection> projs) {
        List<DepartmentResponse> resps = new ArrayList<>(projs.size());
        for (DepartmentResponseProjection proj : projs) {
            DepartmentResponse resp = new DepartmentResponse();
            resp.setDepartmentName(proj.getDepartmentName());
            resp.setCreatedTime(proj.getCreatedTime().toString());
            resp.setManagerName(proj.getManagerName());
            resp.setEmployeeCount(proj.getEmployeeCount());
            resps.add(resp);
        }
        return resps;
    }

    public Map<String,String> fetchChartData() {
        return departmentRepository.findAll().stream().collect(Collectors.toMap(Department::getName,dpt -> String.format("%d",dpt.getEmployee_amount())));
    }


}
