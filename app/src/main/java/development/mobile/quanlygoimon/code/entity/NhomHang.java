package development.mobile.quanlygoimon.code.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NhomHang implements Serializable{
    private int maNhomHang;
    private String tenNhomHang;
    private String pushKey;

    public NhomHang() {
    }

    public NhomHang(String tenNhomHang) {
        this.tenNhomHang = tenNhomHang;
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

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    @Override
    public String toString() {
        return "NhomHang{" +
                "maNhomHang=" + maNhomHang +
                ", tenNhomHang='" + tenNhomHang + '\'' +
                '}';
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maNhomHang", maNhomHang);
        result.put("tenNhomHang", tenNhomHang);

        return result;
    }
}
