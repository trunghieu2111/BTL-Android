package com.example.cuahangtienloi.model;

import java.util.List;

public class LoaiSpModel {
    boolean success;
    String message;
    List<CategoryProduct> result;

    public List<CategoryProduct> getResult() {
        return result;
    }

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

    public void setResult(List<CategoryProduct> result) {
        this.result = result;
    }
}
