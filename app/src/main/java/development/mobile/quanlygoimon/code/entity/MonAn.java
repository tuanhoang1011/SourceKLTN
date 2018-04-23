package development.mobile.quanlygoimon.code.entity;

import java.util.Objects;

public class MonAn {
    private int maMonAn;
    private int maNhomHang;
    private String tenMonAn;
    private double gia;
    private String loai;
    private boolean trangThai;
    private int soLuong;
    private String ghiChu;

    public MonAn() {
    }

    public MonAn(int maMonAn, int maNhomHang, String tenMonAn, double gia, String loai, boolean trangThai, int soLuong) {
        this.maMonAn = maMonAn;
        this.maNhomHang = maNhomHang;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.loai = loai;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
    }

    public int getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        this.maMonAn = maMonAn;
    }

    public int getMaNhomHang() {
        return maNhomHang;
    }

    public void setMaNhomHang(int maNhomHang) {
        this.maNhomHang = maNhomHang;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonAn monAn = (MonAn) o;
        return maMonAn == monAn.maMonAn;
    }

    @Override
    public int hashCode() {

        return Objects.hash(maMonAn);
    }

    @Override
    public String toString() {
        return "MonAn{" +
                "maMonAn=" + maMonAn +
                ", maNhomHang=" + maNhomHang +
                ", tenMonAn='" + tenMonAn + '\'' +
                ", gia=" + gia +
                ", loai='" + loai + '\'' +
                ", trangThai=" + trangThai +
                ", soLuong=" + soLuong +
                ", ghiChu=" + ghiChu +
                '}';
    }
}
