package com.hsf302.demo.controller;

import com.hsf302.demo.model.NguoiDung;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dangky")
public class DangKyController {

    // 1. GET /dangky – Hiển thị form đăng ký rỗng
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("nguoiDung", new NguoiDung());
        return "dangky/form";
    }

    // 2. POST /dangky – Kiểm tra hợp lệ (@Valid) và xử lý kết quả
    @PostMapping
    public String xuLy(@Valid @ModelAttribute("nguoiDung") NguoiDung nguoiDung,
                       BindingResult result,              // BẮT BUỘC đứng ngay liền sau tham số @Valid
                       RedirectAttributes ra) {
        // Nếu có lỗi validation -> trả trực tiếp về VIEW (KHÔNG redirect) để giữ lại dữ liệu đã nhập + lỗi
        if (result.hasErrors()) {
            return "dangky/form";
        }

        // Hợp lệ -> Lưu tên vào Flash Attribute và Redirect (PRG Pattern)
        ra.addFlashAttribute("hoTen", nguoiDung.getHoTen());
        return "redirect:/dangky/thanh-cong";
    }

    // 3. GET /dangky/thanh-cong – Trang thông báo đăng ký thành công
    @GetMapping("/thanh-cong")
    public String thanhCong() {
        return "dangky/thanh-cong";
    }
}