package development.mobile.quanlygoimon.code.entity;

public class ChucNangQuanLy {
    private int iconChucNang;
    private String tenChucNang;

    public ChucNangQuanLy(int iconChucNang, String tenChucNang) {
        this.iconChucNang = iconChucNang;
        this.tenChucNang = tenChucNang;
    }

    public int getIconChucNang() {
        return iconChucNang;
    }

    public void setIconChucNang(int iconChucNang) {
        this.iconChucNang = iconChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}
