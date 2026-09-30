package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.dto.DepartmentSummary;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCode(String code);
    List<Department> findByStudentsIsEmpty();

    @Query("SELECT new com.hsf302.ch4.dto.DepartmentStatDTO(d.code, d.name, COUNT(s), AVG(s.gpa)) " +
            "FROM Department d LEFT JOIN d.students s " +
            "GROUP BY d.code, d.name " +
            "ORDER BY d.code")
    List<DepartmentStatDTO> getDepartmentStats();

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.code = :code")
    Optional<Department> findByCodeWithStudents(@Param("code") String code);

    @Query("SELECT d.name AS departmentName, COUNT(s) AS numberStudent " +
            "FROM Department d LEFT JOIN d.students s " +
            "GROUP BY d.id, d.name")
    List<DepartmentSummary> findStudentByDepartment();
}