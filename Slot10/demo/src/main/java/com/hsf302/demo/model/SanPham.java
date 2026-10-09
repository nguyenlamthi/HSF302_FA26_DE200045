package com.hsf302.demo.model;

public class SanPham {
    private String ten;
    private Double gia;          // Dùng Wrapper type: ô input để trống sẽ nhận giá trị null
    private Integer soLuong;

    // BẮT BUỘC: Constructor không tham số để Spring DataBinder khởi tạo đối tượng
    public SanPham() {
    }

    public SanPham(String ten, Double gia, Integer soLuong) {
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    // Các hàm Getter & Setter (BẮT BUỘC để Thymeleaf và Spring binding dữ liệu)
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}