package development.mobile.quanlygoimon.code.entity;

public class ChiTietHoaDon {
    private String maHoaDon;
    private int maMonAn;
    private String tenMonAn;
    private int soLuong;
    private String ghiChu;
    private double gia;
    private String thoiGianGoi;
    private String loai;
    private String trangThai;
    private String pushkeyHD;
    private String pushKeyCTHD;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maHoaDon, int maMonAn, String tenMonAn, int soLuong, String ghiChu, double gia, String thoiGianGoi, String loai, String trangThai, String pushkeyHD, String pushKeyCTHD) {
        this.maHoaDon = maHoaDon;
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.gia = gia;
        this.thoiGianGoi = thoiGianGoi;
        this.loai = loai;
        this.trangThai = trangThai;
        this.pushkeyHD = pushkeyHD;
        this.pushKeyCTHD = pushKeyCTHD;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
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

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getThoiGianGoi() {
        return thoiGianGoi;
    }

    public void setThoiGianGoi(String thoiGianGoi) {
        this.thoiGianGoi = thoiGianGoi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPushkeyHD() {
        return pushkeyHD;
    }

    public void setPushkeyHD(String pushkeyHD) {
        this.pushkeyHD = pushkeyHD;
    }

    public String getPushKeyCTHD() {
        return pushKeyCTHD;
    }

    public void setPushKeyCTHD(String pushKeyCTHD) {
        this.pushKeyCTHD = pushKeyCTHD;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maMonAn=" + maMonAn +
                ", tenMonAn='" + tenMonAn + '\'' +
                ", soLuong=" + soLuong +
                ", ghiChu='" + ghiChu + '\'' +
                ", gia=" + gia +
                ", thoiGianGoi='" + thoiGianGoi + '\'' +
                ", loai='" + loai + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", pushkeyHD='" + pushkeyHD + '\'' +
                ", pushKeyCTHD='" + pushKeyCTHD + '\'' +
                '}';
    }
}
