package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // ===== TODO 7 & 8 =====
    Optional<Course> findByCode(String code);

    // ===== TODO 8 =====
    List<Course> findBySemesterOrderByCodeAsc(String semester);
    long countBySemester(String semester);

}