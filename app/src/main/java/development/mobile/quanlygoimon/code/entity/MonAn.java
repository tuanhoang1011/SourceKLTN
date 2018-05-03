package development.mobile.quanlygoimon.code.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MonAn implements Serializable{
    private int maMonAn;
    private String tenMonAn;
    private String loai;
    private double gia;
    private int maNhomHang;
    private boolean trangThai;
    private String urlAnh;
    private int soLuong;
    private String ghiChu;
    private String pushKey;
    private String pushKeyNhomHang;


    public MonAn() {
    }

    public MonAn(String tenMonAn, String loai, double gia, boolean trangThai, int maNhomHang, String urlAnh) {
        this.maMonAn = maMonAn;
        this.maNhomHang = maNhomHang;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.loai = loai;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.pushKey = pushKey;
    }

    public MonAn(int maMonAn, String tenMonAn, String loai, double gia, boolean trangThai, int maNhomHang, String urlAnh) {
        this.maMonAn = maMonAn;
        this.maNhomHang = maNhomHang;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.loai = loai;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.pushKey = pushKey;
    }

    public MonAn(int maMonAn, int maNhomHang, String tenMonAn, double gia, String loai, boolean trangThai, int soLuong, String ghiChu, String pushKey) {
        this.maMonAn = maMonAn;
        this.maNhomHang = maNhomHang;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.loai = loai;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.pushKey = pushKey;
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

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getPushKeyNhomHang() {
        return pushKeyNhomHang;
    }

    public void setPushKeyNhomHang(String pushKeyNhomHang) {
        this.pushKeyNhomHang = pushKeyNhomHang;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
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
                ", ghiChu='" + ghiChu + '\'' +
                ", pushKey='" + pushKey + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maMonAn", maMonAn);
        result.put("tenMonAn", tenMonAn);
        result.put("loai", loai);
        result.put("gia", gia);
        result.put("maNhomHang", maNhomHang);
        result.put("trangThai", trangThai);
        result.put("urlAnh", urlAnh);

        return result;
    }
}
