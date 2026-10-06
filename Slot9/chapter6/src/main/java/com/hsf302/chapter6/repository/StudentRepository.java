package com.hsf302.chapter6.repository;

import com.hsf302.chapter6.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Dùng khi thêm mới: Kiểm tra email đã có người dùng chưa
    boolean existsByEmailIgnoreCase(String email);

    // Dùng khi cập nhật: Kiểm tra email đã bị sinh viên KHÁC lấy chưa
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}