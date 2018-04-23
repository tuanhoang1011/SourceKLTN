package development.mobile.quanlygoimon.code.entity;

public class HoaDon {
    private String maHoaDon;
    private int maNhanVien;
    private String tenNhanVien;
    private String ngayLap;
    private double tienCoc;
    private double tongTien;
    private boolean daThanhToan;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, int maNhanVien, String tenNhanVien, String ngayLap, double tienCoc, double tongTien, boolean daThanhToan) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngayLap = ngayLap;
        this.tienCoc = tienCoc;
        this.tongTien = tongTien;
        this.daThanhToan = daThanhToan;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
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

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien=" + maNhanVien +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", ngayLap='" + ngayLap + '\'' +
                ", tienCoc=" + tienCoc +
                ", tongTien=" + tongTien +
                ", daThanhToan=" + daThanhToan +
                '}';
    }
}
