package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    long count();
    Optional<Student> findById(Long id);
    List<Student> findAllOrderByGpaDesc();
    Page<Student> findPage(int pageIndex, int size, String sortField);
    Optional<Student> findByStudentCode(String studentCode);   // TODO 8a
    boolean isEmailExisted(String email);                      // TODO 8b
    long countActive();
}
