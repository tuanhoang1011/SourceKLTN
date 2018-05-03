package development.mobile.quanlygoimon.code.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NhanVien implements Serializable{
    private int maNhanVien;
    private String tenNhanVien;
    private String ngaySinh;
    private String diaChi;
    private String soDienThoai;
    private String chucVu;
    private boolean dangNhap;
    private String pushkeyNV;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(int maNhanVien, String tenNhanVien, String ngaySinh, String diaChi, String soDienThoai, String chucVu, boolean dangNhap, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.matKhau = matKhau;
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

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getPushkeyNV() {
        return pushkeyNV;
    }

    public void setPushkeyNV(String pushkeyNV) {
        this.pushkeyNV = pushkeyNV;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maNhanVien", maNhanVien);
        result.put("tenNhanVien", tenNhanVien);
        result.put("ngaySinh", ngaySinh);
        result.put("diaChi", diaChi);
        result.put("soDienThoai", soDienThoai);
        result.put("chucVu", chucVu);
        result.put("dangNhap", dangNhap);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapQuanLy() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maNhanVien", maNhanVien);
        result.put("tenNhanVien", tenNhanVien);
        result.put("ngaySinh", ngaySinh);
        result.put("diaChi", diaChi);
        result.put("soDienThoai", soDienThoai);
        result.put("chucVu", chucVu);
        result.put("dangNhap", dangNhap);
        result.put("matKhau", matKhau);

        return result;
    }
}
