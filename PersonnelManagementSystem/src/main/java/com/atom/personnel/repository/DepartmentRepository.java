package com.atom.personnel.repository;

import com.atom.personnel.dto.response.DepartmentResponseProjection;
import com.atom.personnel.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(value = "SELECT * FROM department",nativeQuery = true)
    List<Department> findAll();

    Optional<Department> findByName(String name);

    @Query(value = "select d.name AS departmentName,d.created_at AS createdTime," +
            "e.name AS managerName,d.employee_amount AS employeeCount " +
            "from department d left join employee e on d.manager_id = e.id",
    nativeQuery = true)
    List<DepartmentResponseProjection> getAllDepartments();

    boolean existsByName(String name);

    Integer findIdByName(String name);

}
