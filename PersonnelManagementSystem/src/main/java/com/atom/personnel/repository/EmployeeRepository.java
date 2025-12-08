package com.atom.personnel.repository;

import com.atom.personnel.dto.response.EmployeeDtoProjection;
import com.atom.personnel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT e.id, e.name, e.gender, e.age, e.phone, e.email, " +
            "e.address, e.salary, e.position, e.performance, " +
            "e.avatar_url , e.join_date, d.name as department " +
            "FROM employee e " +
            "LEFT JOIN department d ON e.dept_id = d.id " +
            "limit ?1, ?2",
            countQuery = "SELECT COUNT(*) FROM employee e " +
                    "LEFT JOIN employee_department ed ON e.id = ed.emp_id",
            nativeQuery = true)
    List<EmployeeDtoProjection> getEmployees(int start, int size);

    @Query(value = "SELECT e.id, e.name, e.gender, e.age, e.phone, e.email, " +
            "e.address, e.salary, e.position, e.performance, " +
            "e.avatar_url , e.join_date, d.name as department " +
            "FROM employee e " +
            "LEFT JOIN department d ON e.dept_id = d.id " +
            "where e.name like ?3% " +
            "limit ?1, ?2",
            countQuery = "SELECT COUNT(*) FROM employee e where e.name like ?3% " +
                    "LEFT JOIN employee_department ed ON e.id = ed.emp_id",
            nativeQuery = true)
    List<EmployeeDtoProjection> searchEmployees(int page,int size,String search);

    @Query(value = "SELECT e.id, e.name, e.gender, e.age, e.phone, e.email, " +
            "e.address, e.salary, e.position, e.performance, " +
            "e.avatar_url , e.join_date, d.name as department " +
            "FROM employee e " +
            "LEFT JOIN department d ON e.dept_id = d.id " +
            "where e.id = ?1",
            nativeQuery = true)
    EmployeeDtoProjection getEmployee(Long id);

    @Query(value = "SELECT COUNT(*) from employee where name like ?1%",
            nativeQuery = true)
    Long countWithCondition(String searchedName);

    long count();

    void deleteById(Long id);

    boolean existsByName(String name);

    boolean existsById(Long id);

}
