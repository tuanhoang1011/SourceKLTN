package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import development.mobile.quanlygoimon.code.R;

public class QuanLyNhanVienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
