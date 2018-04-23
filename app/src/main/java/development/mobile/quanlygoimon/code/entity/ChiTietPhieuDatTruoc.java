package development.mobile.quanlygoimon.code.entity;

public class ChiTietPhieuDatTruoc {
    private String maDatTruoc;
    private int maMonAn;
    private String tenMonAn;
    private int soLuong;
    private String ghiChu;
    private double gia;
    private String loai;

    public ChiTietPhieuDatTruoc() {
    }

    public String getMaDatTruoc() {
        return maDatTruoc;
    }

    public void setMaDatTruoc(String maDatTruoc) {
        this.maDatTruoc = maDatTruoc;
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

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDatTruoc{" +
                "maDatTruoc='" + maDatTruoc + '\'' +
                ", maMonAn=" + maMonAn +
                ", tenMonAn='" + tenMonAn + '\'' +
                ", soLuong=" + soLuong +
                ", ghiChu='" + ghiChu + '\'' +
                ", gia=" + gia +
                ", loai='" + loai + '\'' +
                '}';
    }
}
