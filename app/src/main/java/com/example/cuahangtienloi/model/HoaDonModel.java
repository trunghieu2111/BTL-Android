package com.example.cuahangtienloi.model;

import java.util.List;

public class HoaDonModel {
    boolean success;
    String message;
    List<GetHoaDon> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetHoaDon> getResult() {
        return result;
    }

    public void setResult(List<GetHoaDon> result) {
        this.result = result;
    }
}
