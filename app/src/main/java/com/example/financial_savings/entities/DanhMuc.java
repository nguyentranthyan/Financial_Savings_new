package com.example.financial_savings.entities;

public class DanhMuc {
    private String maDanhMuc;
    private String tenDanhMuc;
    private String bieuTuong;
    private String loaiDanhMuc;

    public DanhMuc() {
    }

    public DanhMuc(String maDanhMuc, String tenDanhMuc, String bieuTuong, String loaiDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.bieuTuong = bieuTuong;
        this.loaiDanhMuc = loaiDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getBieuTuong() {
        return bieuTuong;
    }

    public void setBieuTuong(String bieuTuong) {
        this.bieuTuong = bieuTuong;
    }

    public String getLoaiDanhMuc() {
        return loaiDanhMuc;
    }

    public void setLoaiDanhMuc(String loaiDanhMuc) {
        this.loaiDanhMuc = loaiDanhMuc;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", bieuTuong='" + bieuTuong + '\'' +
                ", loaiDanhMuc='" + loaiDanhMuc + '\'' +
                '}';
    }
}
