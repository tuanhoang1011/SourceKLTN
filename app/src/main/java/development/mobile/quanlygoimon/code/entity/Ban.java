package development.mobile.quanlygoimon.code.entity;

public class Ban {
    private String maBan;
    private String maKhuVuc;
    private String maHoaDon;
    private String trangThai;
    private String pushKey;
    private String pushKeyKhuVuc;

    public Ban() {
    }

    public Ban(String maBan, String maKhuVuc, String maHoaDon, String trangThai) {
        this.maBan = maBan;
        this.maKhuVuc = maKhuVuc;
        this.maHoaDon = maHoaDon;
        this.trangThai = trangThai;
    }

    public Ban(String maBan, String maKhuVuc, String maHoaDon, String trangThai, String pushKey) {
        this.maBan = maBan;
        this.maKhuVuc = maKhuVuc;
        this.maHoaDon = maHoaDon;
        this.trangThai = trangThai;
        this.pushKey = pushKey;
    }

    public Ban(String maBan, String maHoaDon, String trangThai) {
        this.maBan = maBan;
        this.maHoaDon = maHoaDon;
        this.trangThai = trangThai;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getPushKeyKhuVuc() {
        return pushKeyKhuVuc;
    }

    public void setPushKeyKhuVuc(String pushKeyKhuVuc) {
        this.pushKeyKhuVuc = pushKeyKhuVuc;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "maBan='" + maBan + '\'' +
                ", maKhuVuc='" + maKhuVuc + '\'' +
                ", maHoaDon='" + maHoaDon + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", pushKey='" + pushKey + '\'' +
                '}';
    }
}
