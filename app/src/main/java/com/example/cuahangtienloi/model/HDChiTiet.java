package com.example.cuahangtienloi.model;

public class HDChiTiet {
    int Id, HoaDonId,SanPhamId,SoLuong;
    String Gia;
    String TenSP;

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    String HinhAnh;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getHoaDonId() {
        return HoaDonId;
    }

    public void setHoaDonId(int hoaDonId) {
        HoaDonId = hoaDonId;
    }

    public int getSanPhamId() {
        return SanPhamId;
    }

    public void setSanPhamId(int sanPhamId) {
        SanPhamId = sanPhamId;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }
}
