package com.atom.personnel.service;

import com.atom.personnel.exception.EmployeeAlreadyExistsException;
import com.atom.personnel.exception.EmployeeNotExistsException;
import com.atom.personnel.exception.InvalidOperationException;
import com.atom.personnel.dto.request.EmployeeDto;
import com.atom.personnel.dto.response.EmployeeDtoProjection;
import com.atom.personnel.entity.Department;
import com.atom.personnel.entity.Employee;
import com.atom.personnel.repository.DepartmentRepository;
import com.atom.personnel.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeDto> getEmployees(int page, int size, String search) {
        //Pageable pageable = PageRequest.of(page, size);
        if (search == null || search.trim().isEmpty()) {
            List<EmployeeDtoProjection> projections = employeeRepository.getEmployees(page, size);
            // 转换 projection 到 dto
            return projections.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        // 如果有搜索条件，调用搜索方法
        return employeeRepository.searchEmployees(page, size, search)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDto> getEmployee(Long id) {
        EmployeeDtoProjection employeeDtoProjection = employeeRepository.getEmployee(id);
        EmployeeDto employeeDto = convertToDto(employeeDtoProjection);
        return Optional.of(employeeDto);
    }

    public Optional<Employee> updateEmployee(Employee employeeToUpdate, String departmentName) {
        if(employeeToUpdate == null) {
            throw new EmployeeNotExistsException();
        }
        Optional<Department> departmentOptional = departmentRepository.findByName(departmentName);
        employeeToUpdate.setDepartment(departmentOptional.orElseThrow(() ->
            new InvalidOperationException("Invalid department")
        ));
        return Optional.of(employeeRepository.save(employeeToUpdate));
    }

    public void addEmployee(Employee employeeToAdd,String departmentName) {
        if(employeeRepository.existsByName(employeeToAdd.getName())) {
            throw new EmployeeAlreadyExistsException("Employee " + employeeToAdd.getName()+" already exists!");
        }
        Optional<Department> departmentOptional = departmentRepository.findByName(departmentName);
        employeeToAdd.setDepartment(departmentOptional.orElseThrow(() ->
                new InvalidOperationException("Invalid department")
        ));

        employeeRepository.save(employeeToAdd);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteEmployee(Long id) {
        if(!employeeRepository.existsById(id)) {
            throw new EmployeeNotExistsException("Employee with id " + id + " does not exist!");
        }
        employeeRepository.deleteById(id);
    }

    public int getEmployeeCount(String searchedName) {
        if(searchedName == null || searchedName.trim().isEmpty()) {
            return Math.toIntExact(employeeRepository.count());
        }
        return Math.toIntExact(employeeRepository.countWithCondition(searchedName));
    }

    private EmployeeDto convertToDto(EmployeeDtoProjection proj) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(proj.getId());
        dto.setName(proj.getName());
        dto.setGender(proj.getGender());
        dto.setAge(proj.getAge());
        dto.setPhone(proj.getPhone());
        dto.setEmail(proj.getEmail());
        dto.setAddress(proj.getAddress());
        dto.setSalary(proj.getSalary());
        dto.setPosition(proj.getPosition());
        dto.setJoin_date(proj.getJoinDate());
        dto.setPerformance(proj.getPerformance());
        dto.setAvatar_url(proj.getAvatarUrl());
        dto.setDepartment(proj.getDepartment());
        return dto;
    }

}
