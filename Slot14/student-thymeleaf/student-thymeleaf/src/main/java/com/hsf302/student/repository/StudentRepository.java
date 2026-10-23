package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 19.1: Tìm theo mã SV HOẶC họ tên chứa từ khóa, không phân biệt hoa thường, sắp xếp id tăng dần
    // Tương đương SQL: WHERE UPPER(student_code) LIKE UPPER(%code%) OR UPPER(full_name) LIKE UPPER(%name%) ORDER BY id ASC
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(String code, String name);

    // TODO 19.2: Kiểm tra mã SV đã tồn tại (dùng khi THÊM MỚI)
    // Tương đương SQL: SELECT COUNT(*) > 0 FROM Students WHERE UPPER(student_code) = UPPER(?)
    boolean existsByStudentCodeIgnoreCase(String studentCode);

    // TODO 19.3: Kiểm tra mã SV đã tồn tại ở sinh viên KHÁC (dùng khi CẬP NHẬT – bỏ qua chính nó)
    // Tương đương SQL: SELECT COUNT(*) > 0 FROM Students WHERE UPPER(student_code) = UPPER(?) AND id <> ?
    boolean existsByStudentCodeIgnoreCaseAndIdNot(String studentCode, Long id);
}