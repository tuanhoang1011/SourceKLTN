package development.mobile.quanlygoimon.code.entity;

public class PhieuDatTruoc {
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

    public PhieuDatTruoc() {
    }

    public PhieuDatTruoc(String maDatTruoc, int maNhanVien, String tenNhanVien, int soLuongBan, int soLuongKhach, String tenKhachHang, String soDienThoai, String ngayLap, String thoiGianNhanBan, double tienCoc) {
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
}
