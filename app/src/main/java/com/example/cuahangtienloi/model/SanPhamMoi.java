package com.example.cuahangtienloi.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    int Id;
    String TenSanPham;
    String Gia;
    String HinhAnh;
    String MoTa;
    int MaLoai;
    int SoLuong;

    public int getSoluong() {
        return SoLuong;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenSanpham() {
        return TenSanPham;
    }

    public void setTenSanpham(String tenSanpham) {
        TenSanPham = tenSanpham;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }
}
