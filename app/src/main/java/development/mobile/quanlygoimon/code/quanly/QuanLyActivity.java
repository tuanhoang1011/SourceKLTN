package development.mobile.quanlygoimon.code.quanly;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.dangnhap.LoginActivity;
import development.mobile.quanlygoimon.code.entity.ChucNangQuanLy;
import development.mobile.quanlygoimon.code.quanly.quanlyban.QuanLyBanActivity;
import development.mobile.quanlygoimon.code.quanly.quanlykhuvuc.QuanLyKhuVucActivity;
import development.mobile.quanlygoimon.code.quanly.quanlymonan.QuanLyMonAnActivity;
import development.mobile.quanlygoimon.code.quanly.quanlynhanvien.QuanLyNhanVienActivity;
import development.mobile.quanlygoimon.code.quanly.quanlynhomhang.QuanLyNhomHangActivity;

public class QuanLyActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private TextView titleToolbarTxtView, dangXuatTxtView;
    private String maNV, tenNV, pushKeyNV;

    private GridViewQuanLyAdapter gridViewQuanLyAdapter = null;
    private List<ChucNangQuanLy> chucNangQuanLyList = null;
    private GridView grdViewQuanLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_quanLyAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleToolbarTxtView = (TextView) findViewById(R.id.toolbarTitle_quanLyAct);
        dangXuatTxtView = (TextView) findViewById(R.id.dangXuatTxtView_quanLyAct);
        grdViewQuanLy = (GridView) findViewById(R.id.grdView_quanly);
        readPreferences();

        chucNangQuanLyList = new ArrayList<ChucNangQuanLy>();
        chucNangQuanLyList.add(new ChucNangQuanLy(R.drawable.icon_nhanvien, "Quản lý nhân viên"));
        chucNangQuanLyList.add(new ChucNangQuanLy(R.drawable.icon_khuvuc, "Quản lý khu vực"));
        chucNangQuanLyList.add(new ChucNangQuanLy(R.drawable.icon_ban, "Quản lý bàn"));
        chucNangQuanLyList.add(new ChucNangQuanLy(R.drawable.icon_nhomhang, "Quản lý nhóm hàng"));
        chucNangQuanLyList.add(new ChucNangQuanLy(R.drawable.icon_monan, "Quản lý món ăn"));

        gridViewQuanLyAdapter = new GridViewQuanLyAdapter(this, R.layout.item_chucnang_gridview_quanly, chucNangQuanLyList);
        grdViewQuanLy.setAdapter(gridViewQuanLyAdapter);

        grdViewQuanLy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (chucNangQuanLyList.get(i).getTenChucNang().equals("Quản lý nhân viên")) {
                    Intent intent = new Intent(QuanLyActivity.this, QuanLyNhanVienActivity.class);
                    startActivity(intent);
                } else if (chucNangQuanLyList.get(i).getTenChucNang().equals("Quản lý khu vực")) {
                    Intent intent = new Intent(QuanLyActivity.this, QuanLyKhuVucActivity.class);
                    startActivity(intent);
                } else if (chucNangQuanLyList.get(i).getTenChucNang().equals("Quản lý bàn")) {
                    Intent intent = new Intent(QuanLyActivity.this, QuanLyBanActivity.class);
                    startActivity(intent);
                } else if (chucNangQuanLyList.get(i).getTenChucNang().equals("Quản lý nhóm hàng")) {
                    Intent intent = new Intent(QuanLyActivity.this, QuanLyNhomHangActivity.class);
                    startActivity(intent);
                } else if (chucNangQuanLyList.get(i).getTenChucNang().equals("Quản lý món ăn")) {
                    Intent intent = new Intent(QuanLyActivity.this, QuanLyMonAnActivity.class);
                    startActivity(intent);
                }
            }
        });

        dangXuatTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyActivity.this);

                final EditText input = new EditText(QuanLyActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.requestFocus();
                builder.setView(input);
                builder.setTitle("Vui lòng nhập mật khẩu quản lý để đăng xuất!");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child("NhanVien").orderByChild("matKhau").equalTo(input.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    SharedPreferences sharePre = getSharedPreferences("mySharePre", MODE_PRIVATE);
                                    sharePre.edit().clear().apply();

                                    myRef.child("NhanVien").child(pushKeyNV).child("dangNhap").setValue(false);

                                    Intent intent = new Intent(QuanLyActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(QuanLyActivity.this, "Mật khẩu quản lý không chính xác!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(QuanLyActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    public void readPreferences() {
        SharedPreferences sharePre = getSharedPreferences("mySharePre", MODE_PRIVATE);

        maNV = sharePre.getString("maNhanVien", "");
        tenNV = sharePre.getString("tenNhanVien", "");
        pushKeyNV = sharePre.getString("pushKeyNhanVien", "");
        titleToolbarTxtView.setText(maNV + "-" + tenNV);
    }
}
