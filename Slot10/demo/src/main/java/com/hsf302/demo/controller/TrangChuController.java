package com.hsf302.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {

    @GetMapping("/")
    public String trangChu() {
        return "trang-chu";     // -> templates/trang-chu.html
    }
}