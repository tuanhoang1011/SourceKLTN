package development.mobile.quanlygoimon.code.entity;

public class KhuVuc {
    private int maKhuVuc;
    private String tenKhuVuc;

    public KhuVuc() {
    }

    public KhuVuc(int maKhuVuc, String tenKhuVuc) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(int maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "maKhuVuc=" + maKhuVuc +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                '}';
    }
}
