package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class QuanLyNhanVienActivity extends AppCompatActivity {

    Button btnThemMoiNV;
//    EditText edtSearchInput;
    SearchView svIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_qlNhanVienAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnThemMoiNV = (Button) findViewById(R.id.btn_themmoi_quanly_qlnv);
//        edtSearchInput = (EditText) findViewById(R.id.edt_search_quanly_qlnv);
        svIcon  = (SearchView) findViewById(R.id.sv_searchicon_quanly_qlnv);
    }
}
