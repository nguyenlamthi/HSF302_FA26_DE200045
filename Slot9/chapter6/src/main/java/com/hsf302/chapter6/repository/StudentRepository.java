package com.hsf302.chapter6.repository;

import com.hsf302.chapter6.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Dùng khi thêm mới: Kiểm tra email đã có người dùng chưa
    boolean existsByEmailIgnoreCase(String email);

    // Dùng khi cập nhật: Kiểm tra email đã bị sinh viên KHÁC lấy chưa
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    // Q1: Tìm kiếm theo tên HOẶC email (không phân biệt hoa thường)
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Sort sort);

    // Q2: Tìm kiếm CÓ PHÂN TRANG (nhận Pageable, trả về Page<Student>)
    Page<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
}