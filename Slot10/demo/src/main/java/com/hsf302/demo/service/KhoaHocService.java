package com.hsf302.demo.service;

import com.hsf302.demo.model.KhoaHoc;
import com.hsf302.demo.repository.KhoaHocRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KhoaHocService {

    private final KhoaHocRepository repo;

    // Constructor Injection (Spring tự động inject khi class chỉ có 1 constructor)
    public KhoaHocService(KhoaHocRepository repo) {
        this.repo = repo;
    }

    // 1. Lấy danh sách hoặc tìm kiếm theo từ khóa, sắp xếp tăng dần theo ID
    public List<KhoaHoc> search(String keyword) {
        Sort sort = Sort.by("id");
        if (keyword == null || keyword.isBlank()) {
            return repo.findAll(sort);
        }
        return repo.findByTenKhoaHocContainingIgnoreCase(keyword.trim(), sort);
    }

    // 2. Tìm theo ID trả về Optional (tránh lỗi 500 khi ID không tồn tại)
    public Optional<KhoaHoc> findById(Long id) {
        return repo.findById(id);
    }

    // 3. Lưu (Thêm mới nếu id == null, Cập nhật nếu id != null)
    @Transactional
    public KhoaHoc save(KhoaHoc kh) {
        return repo.save(kh);
    }

    // 4. Xóa an toàn có kiểm tra tồn tại trước
    @Transactional
    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}