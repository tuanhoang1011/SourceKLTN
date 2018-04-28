package development.mobile.quanlygoimon.code.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PhieuDatTruoc implements Serializable{
    private String maDatTruoc;
    private int maNhanVien;
    private String tenNhanVien;
    private int soLuongBan;
    private int soLuongKhach;
    private String tenKhachHang;
    private String soDienThoai;
    private String ngayLap;
    private String thoiGianNhanBan;
    private double tienCoc;
    private ArrayList<ChiTietPhieuDatTruoc> chiTietPhieuDatTruocArrayList;
    private String pushkeyPDT;

    public PhieuDatTruoc() {
    }

    public PhieuDatTruoc(String maDatTruoc, int maNhanVien, String tenNhanVien, int soLuongBan, int soLuongKhach, String tenKhachHang, String soDienThoai, String ngayLap, String thoiGianNhanBan, double tienCoc, String pushkeyPDT) {
        this.maDatTruoc = maDatTruoc;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.soLuongBan = soLuongBan;
        this.soLuongKhach = soLuongKhach;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayLap = ngayLap;
        this.thoiGianNhanBan = thoiGianNhanBan;
        this.tienCoc = tienCoc;
        this.pushkeyPDT = pushkeyPDT;
    }

    public String getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(String maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getSoLuongKhach() {
        return soLuongKhach;
    }

    public void setSoLuongKhach(int soLuongKhach) {
        this.soLuongKhach = soLuongKhach;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getThoiGianNhanBan() {
        return thoiGianNhanBan;
    }

    public void setThoiGianNhanBan(String thoiGianNhanBan) {
        this.thoiGianNhanBan = thoiGianNhanBan;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public ArrayList<ChiTietPhieuDatTruoc> getChiTietPhieuDatTruocArrayList() {
        return chiTietPhieuDatTruocArrayList;
    }

    public void setChiTietPhieuDatTruocArrayList(ArrayList<ChiTietPhieuDatTruoc> chiTietPhieuDatTruocArrayList) {
        this.chiTietPhieuDatTruocArrayList = chiTietPhieuDatTruocArrayList;
    }

    public String getPushkeyPDT() {
        return pushkeyPDT;
    }

    public void setPushkeyPDT(String pushkeyPDT) {
        this.pushkeyPDT = pushkeyPDT;
    }

    @Override
    public String toString() {
        return "PhieuDatTruoc{" +
                "maDatTruoc='" + maDatTruoc + '\'' +
                ", maNhanVien=" + maNhanVien +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", soLuongBan=" + soLuongBan +
                ", soLuongKhach=" + soLuongKhach +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayLap='" + ngayLap + '\'' +
                ", thoiGianNhanBan='" + thoiGianNhanBan + '\'' +
                ", tienCoc=" + tienCoc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuDatTruoc that = (PhieuDatTruoc) o;
        return Objects.equals(maDatTruoc, that.maDatTruoc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(maDatTruoc);
    }
}
