package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienActivity extends AppCompatActivity {

    Button btnThemMoiNV;
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
        svIcon  = (SearchView) findViewById(R.id.sv_searchicon_quanly_qlnv);
        svIcon.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));

        btnThemMoiNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                QuanLyNhanVienChiTietFragment quanLyNhanVienChiTietFragment = new QuanLyNhanVienChiTietFragment();
                Bundle bundle = new Bundle();

//                String []dataConfig = {"Thêm mới nhân viên", ""};
//                bundle.putCharSequenceArray("dataConfig", dataConfig);
                bundle.putString("btnThemNV", "btnThemNV");
                quanLyNhanVienChiTietFragment.setArguments(bundle);

                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlnv, quanLyNhanVienChiTietFragment);
                ft_receive.commit();
            }
        });

        svIcon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("onQueryTextSubmit", "onQueryTextSubmit: ");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("onQueryTextChange", "onQueryTextChange: ");
                return false;
            }
        });
    }
}
