package development.mobile.quanlygoimon.code.entity;

public class NhomHang {
    private int maNhomHang;
    private String tenNhomHang;

    public NhomHang() {
    }

    public NhomHang(int maNhomHang, String tenNhomHang) {
        this.maNhomHang = maNhomHang;
        this.tenNhomHang = tenNhomHang;
    }

    public int getMaNhomHang() {
        return maNhomHang;
    }

    public void setMaNhomHang(int maNhomHang) {
        this.maNhomHang = maNhomHang;
    }

    public String getTenNhomHang() {
        return tenNhomHang;
    }

    public void setTenNhomHang(String tenNhomHang) {
        this.tenNhomHang = tenNhomHang;
    }

    @Override
    public String toString() {
        return "NhomHang{" +
                "maNhomHang=" + maNhomHang +
                ", tenNhomHang='" + tenNhomHang + '\'' +
                '}';
    }
}
