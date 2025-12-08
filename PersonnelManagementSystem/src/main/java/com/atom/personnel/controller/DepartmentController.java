package com.atom.personnel.controller;

import com.atom.personnel.dto.response.DepartmentResponse;
import com.atom.personnel.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/charts/barchart")
    public ResponseEntity<List<Map<String,String>>> getDepartmentsChart() {
        Map<String,String> chartData = departmentService.fetchChartData();
        List<Map<String,String>> chartDataList = new ArrayList<>(chartData.size());
        for(Map.Entry<String,String> entry : chartData.entrySet()) {
            chartDataList.add(Map.of("department",entry.getKey(),"count",entry.getValue()));
        }
        return ResponseEntity.ok(chartDataList);
    }


}
