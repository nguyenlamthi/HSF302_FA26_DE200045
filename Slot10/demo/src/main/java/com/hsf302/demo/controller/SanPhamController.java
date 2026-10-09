package com.hsf302.demo.controller;

import com.hsf302.demo.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {

    // Danh sách lưu tạm trong RAM, thread-safe khi nhiều request truy cập đồng thời
    private final List<SanPham> danhSach = new CopyOnWriteArrayList<>();

    // 1. GET /sanpham/them – Hiển thị form thêm mới rỗng
    @GetMapping("/them")
    public String showForm(Model model) {
        // BẮT BUỘC: Cung cấp đối tượng rỗng có key "sanPham" cho form binding
        model.addAttribute("sanPham", new SanPham());
        return "sanpham/form";      // -> templates/sanpham/form.html
    }

    // 2. POST /sanpham/them – Nhận dữ liệu từ form, lưu và REDIRECT
    @PostMapping("/them")
    public String xuLyForm(@ModelAttribute("sanPham") SanPham sanPham,
                           RedirectAttributes ra) {
        danhSach.add(sanPham);

        // Flash attribute: Lưu tạm vào Session và tự xóa sau 1 lần redirect
        ra.addFlashAttribute("thongBao", "Thêm sản phẩm thành công!");

        // PRG Pattern: Redirect sang URL GET /sanpham/ket-qua (HTTP 302)
        return "redirect:/sanpham/ket-qua";
    }

    // 3. GET /sanpham/ket-qua – Hiển thị danh sách kết quả sau khi thêm
    @GetMapping("/ket-qua")
    public String ketQua(Model model) {
        model.addAttribute("danhSach", danhSach);
        return "sanpham/ket-qua";  // -> templates/sanpham/ket-qua.html
    }
}