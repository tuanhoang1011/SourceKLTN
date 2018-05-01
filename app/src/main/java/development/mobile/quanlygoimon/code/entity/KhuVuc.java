package development.mobile.quanlygoimon.code.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KhuVuc implements Serializable{
    private int maKhuVuc;
    private String tenKhuVuc;
    private String pushkeyKV;

    public KhuVuc() {
    }

    public KhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
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

    public String getPushkeyKV() {
        return pushkeyKV;
    }

    public void setPushkeyKV(String pushkeyKV) {
        this.pushkeyKV = pushkeyKV;
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "maKhuVuc=" + maKhuVuc +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maKhuVuc", maKhuVuc);
        result.put("tenKhuVuc", tenKhuVuc);

        return result;
    }
}
