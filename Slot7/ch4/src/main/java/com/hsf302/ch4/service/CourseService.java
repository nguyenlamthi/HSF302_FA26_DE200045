package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    // ===== TODO 6 =====
    long count();
    List<Course> findAllOrderByCode();
    Optional<Course> findById(Long id);

}