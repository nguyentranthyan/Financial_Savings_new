package com.example.financial_savings.entities;

import java.sql.Date;

public class SoGiaoDich {
    private String maGiaoDich;
    private double soTien;
    private String ghiChu;
    private Date ngayGiaoDich;

    private String masv;
    private String maSuKien;
    private String maDanhMuc;
    private String maVi;
    private String maTietKiem;

    public SoGiaoDich() {
    }

    public SoGiaoDich(String maGiaoDich, double soTien, String ghiChu, Date ngayGiaoDich, String masv, String maSuKien, String maDanhMuc, String maVi, String maTietKiem) {
        this.maGiaoDich = maGiaoDich;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
        this.ngayGiaoDich = ngayGiaoDich;
        this.masv = masv;
        this.maSuKien = maSuKien;
        this.maDanhMuc = maDanhMuc;
        this.maVi = maVi;
        this.maTietKiem = maTietKiem;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getMaSuKien() {
        return maSuKien;
    }

    public void setMaSuKien(String maSuKien) {
        this.maSuKien = maSuKien;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getMaVi() {
        return maVi;
    }

    public void setMaVi(String maVi) {
        this.maVi = maVi;
    }

    public String getMaTietKiem() {
        return maTietKiem;
    }

    public void setMaTietKiem(String maTietKiem) {
        this.maTietKiem = maTietKiem;
    }

    @Override
    public String toString() {
        return "SoGiaoDich{" +
                "maGiaoDich='" + maGiaoDich + '\'' +
                ", soTien=" + soTien +
                ", ghiChu='" + ghiChu + '\'' +
                ", ngayGiaoDich=" + ngayGiaoDich +
                ", masv='" + masv + '\'' +
                ", maSuKien='" + maSuKien + '\'' +
                ", maDanhMuc='" + maDanhMuc + '\'' +
                ", maVi='" + maVi + '\'' +
                ", maTietKiem='" + maTietKiem + '\'' +
                '}';
    }
}
