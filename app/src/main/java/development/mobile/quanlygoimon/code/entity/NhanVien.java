package development.mobile.quanlygoimon.code.entity;

public class NhanVien {
    private int maNhanVien;
    private String tenNhanVien;
    private String ngaySinh;
    private String diaChi;
    private String soDienThoai;
    private String chucVu;
    private boolean dangNhap;

    public NhanVien() {
    }

    public NhanVien(int maNhanVien, String tenNhanVien, String ngaySinh, String diaChi, String soDienThoai, String chucVu, boolean dangNhap) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.dangNhap = dangNhap;
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

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isDangNhap() {
        return dangNhap;
    }

    public void setDangNhap(boolean dangNhap) {
        this.dangNhap = dangNhap;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien=" + maNhanVien +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", dangNhap=" + dangNhap +
                '}';
    }
}
