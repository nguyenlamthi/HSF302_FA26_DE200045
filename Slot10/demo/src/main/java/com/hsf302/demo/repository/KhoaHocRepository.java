package com.hsf302.demo.repository;

import com.hsf302.demo.model.KhoaHoc;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhoaHocRepository extends JpaRepository<KhoaHoc, Long> {

    // Tự động sinh câu lệnh: WHERE upper(ten_khoa_hoc) LIKE upper('%keyword%') ORDER BY ...
    List<KhoaHoc> findByTenKhoaHocContainingIgnoreCase(String keyword, Sort sort);
}