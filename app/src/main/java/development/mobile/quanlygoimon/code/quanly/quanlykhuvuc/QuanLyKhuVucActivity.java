package development.mobile.quanlygoimon.code.quanly.quanlykhuvuc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import development.mobile.quanlygoimon.code.R;

public class QuanLyKhuVucActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khu_vuc);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
