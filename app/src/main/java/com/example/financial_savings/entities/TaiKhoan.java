package com.example.financial_savings.entities;

public class TaiKhoan {
    private String email;
    private String matKhau;
    private int code;

    public TaiKhoan() {
    }

    public TaiKhoan(String email, String matKhau, int code) {
        this.email = email;
        this.matKhau = matKhau;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "email='" + email + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", code=" + code +
                '}';
    }
}
