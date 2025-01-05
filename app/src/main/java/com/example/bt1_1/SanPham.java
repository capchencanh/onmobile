package com.example.bt1_1;

public class SanPham {
    private int id;
    private String ten;
    private double gia;
    private String moTa;

    public SanPham(int id, String ten, double gia, String moTa) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return this.ten;  // Trả về tên sản phẩm khi hiển thị
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
