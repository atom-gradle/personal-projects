package com.atom.personnel.controller;

import com.atom.personnel.dto.request.EmployeeDto;
import com.atom.personnel.entity.Employee;
import com.atom.personnel.entity.Gender;
import com.atom.personnel.entity.Performance;
import com.atom.personnel.entity.Position;
import com.atom.personnel.exception.EmployeeNotExistsException;
import com.atom.personnel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public Map<String,Object> getEmployees(
            @RequestParam int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        List<EmployeeDto> employeeResponses = employeeService.getEmployees(size * (page - 1),size,search);

        int total = employeeService.getEmployeeCount(search);
        int totalPages = (int)Math.ceil((double)total / size);
        return Map.of("employees",employeeResponses,
                "page",page - 1,
                "size",size,
                "totalPages",totalPages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        Optional<EmployeeDto> employeeOptional = employeeService.getEmployee(id);
        return employeeOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        // 1. Rebuild Employee Entity
        Employee employee = rebuildEmployee(employeeDto);

        String departmentName = employeeDto.getDepartment();

        return employeeService.updateEmployee(employee, departmentName)
                .map(e -> ResponseEntity.ok(e.getId()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if(!employeeService.existsById(id)) {
            throw new EmployeeNotExistsException("Employee with id " + id + " does not exist");
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = rebuildEmployee(employeeDto);
        employeeService.addEmployee(employee, employeeDto.getDepartment());
        return ResponseEntity.ok().build();
    }

    private Employee rebuildEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        if(employeeDto.getId() != null) {
            employee.setId(employeeDto.getId());
        }
        employee.setName(employeeDto.getName());
        employee.setGender(Gender.safeValueOf(employeeDto.getGender()));
        employee.setAge(employeeDto.getAge());
        employee.setPhone(employeeDto.getPhone());
        employee.setAddress(employeeDto.getAddress());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setPosition(Position.safeValueOf(employeeDto.getPosition()));
        employee.setJoin_date(LocalDate.parse(employeeDto.getJoin_date()));
        employee.setPerformance(Performance.safeValueOf(employeeDto.getPerformance()));
        employee.setAvatar_url(employeeDto.getAvatar_url());
        return employee;
    }


}
