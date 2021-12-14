package com.example.cuahangtienloi.model;

import java.util.List;

public class GetHoaDon {
    int Id, TaiKhoanId;
    String TongTien;
    List<HDChiTiet> item;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTaiKhoanId() {
        return TaiKhoanId;
    }

    public void setTaiKhoanId(int taiKhoanId) {
        TaiKhoanId = taiKhoanId;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public List<HDChiTiet> getItem() {
        return item;
    }

    public void setItem(List<HDChiTiet> item) {
        this.item = item;
    }
}
