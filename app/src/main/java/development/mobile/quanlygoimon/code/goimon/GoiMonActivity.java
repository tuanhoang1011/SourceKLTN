package development.mobile.quanlygoimon.code.goimon;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.Ban;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;
import development.mobile.quanlygoimon.code.entity.HoaDon;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.goimon.DSMonAnFragment.SendMonAn;
import development.mobile.quanlygoimon.code.goimon.DSNhomHangFragment.SendTenNhomHang;
import development.mobile.quanlygoimon.code.goimon.ListViewMonDangGoiAdapter.SendTongTien;
import development.mobile.quanlygoimon.code.phucvuchonban.BanTrongFragment;
import development.mobile.quanlygoimon.code.phucvuchonban.PhucVuActivity;

public class GoiMonActivity extends AppCompatActivity implements SendTenNhomHang, SendMonAn, SendTongTien {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ArrayList<String> banSelected = new ArrayList<String>();
    private ArrayList<String> pushKeyBanSelected = new ArrayList<String>();
    private TextView dsBanTxtView, soBanTxtView, tongTienTxtView;
    private Button xacNhanBtn_goiMonAct;
    private double tongTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goimon);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dsBanTxtView = (TextView) findViewById(R.id.dsBanTxtView);
        tongTienTxtView = (TextView) findViewById(R.id.tongTienTxtView);
        soBanTxtView = (TextView) findViewById(R.id.soBanTxtView);
        xacNhanBtn_goiMonAct = (Button) findViewById(R.id.xacNhanBtn_goiMonAct);
        xacNhanBtn_goiMonAct.setEnabled(false);

        tongTienTxtView.setText(NumberFormat.getCurrencyInstance().format(tongTien));
        getDataFromPhucVuActivity();

        xacNhanBtn_goiMonAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pushKeyHD = myRef.child("HoaDon").push().getKey();
                ArrayList<String> stringLst = readPreferences();
                HoaDon hd = new HoaDon();
                final String maHoaDon = new SimpleDateFormat("yyyyMMdd HHmmss SSSSSSS").format(new Date());

                hd.setMaHoaDon(maHoaDon);
                hd.setMaNhanVien(Integer.parseInt(stringLst.get(0)));
                hd.setTenNhanVien(stringLst.get(1));
                hd.setNgayLap(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
                hd.setTienCoc(0);
                hd.setTongTien(tongTien);
                hd.setDaThanhToan(false);
                myRef.child("HoaDon").child(pushKeyHD).setValue(hd);

                int i = 1;
                for(String s : banSelected){
                    myRef.child("HoaDon").child(pushKeyHD).child("danhSachBanPhucVu").child("b" + i).setValue(s);
                    i++;
                }

                DSMonDangGoiFragment dsMonDangGoiFrag = (DSMonDangGoiFragment) getSupportFragmentManager().findFragmentById(R.id.monDangGoiFrag);
                List<MonAn> monAnLst_DSMonDGFrag = dsMonDangGoiFrag.monAnLst_DSMonDGFrag;
                for(MonAn monAn : monAnLst_DSMonDGFrag){
                    ChiTietHoaDon chiTietHD = new ChiTietHoaDon();
                    chiTietHD.setMaMonAn(monAn.getMaMonAn());
                    chiTietHD.setTenMonAn(monAn.getTenMonAn());
                    chiTietHD.setSoLuong(monAn.getSoLuong());
                    chiTietHD.setGhiChu(monAn.getGhiChu());
                    chiTietHD.setGia(monAn.getGia());
                    chiTietHD.setThoiGianGoi(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                    chiTietHD.setLoai(monAn.getLoai());
                    chiTietHD.setTrangThai("Đang chờ");
                    myRef.child("HoaDon").child(pushKeyHD).child("chiTietHoaDon").push().setValue(chiTietHD);
                }

                myRef.child("KhuVuc").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int n = 0;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            for (String s : pushKeyBanSelected) {
                                if(child.child("danhSachBan").child(s).getValue() != null){
                                    myRef.child("KhuVuc").child(child.getKey()).child("danhSachBan")
                                            .child(s).child("maHoaDon").setValue(maHoaDon);
                                    myRef.child("KhuVuc").child(child.getKey()).child("danhSachBan")
                                            .child(s).child("trangThai").setValue("Đang phục vụ");
                                    n++;
                                }
                            }
                            if(pushKeyBanSelected.size() == n){
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(GoiMonActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(GoiMonActivity.this, PhucVuActivity.class);
                startActivity(intent);
            }
        });

        tongTienTxtView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tongTienTxtView.getText().toString().length() > 3) {
                    xacNhanBtn_goiMonAct.setEnabled(true);
                } else {
                    xacNhanBtn_goiMonAct.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getDataFromPhucVuActivity() {
        banSelected = getIntent().getStringArrayListExtra("DSBan");
        pushKeyBanSelected = getIntent().getStringArrayListExtra("DSPushKeyBan");
        int sizeLst = banSelected.size();
        for (int i = 0; i < sizeLst; i++) {
            if (i < sizeLst - 1) {
                dsBanTxtView.setText(dsBanTxtView.getText() + banSelected.get(i).toString() + ", ");
            } else {
                dsBanTxtView.setText(dsBanTxtView.getText() + banSelected.get(i).toString());
            }
        }
        soBanTxtView.setText(sizeLst + "");
    }

    @Override
    public void sendMonAn(MonAn monAn) {
        tongTien += monAn.getGia();
        tongTienTxtView.setText(NumberFormat.getCurrencyInstance().format(tongTien));
        DSMonDangGoiFragment dsMonDangGoiFrag = (DSMonDangGoiFragment) getSupportFragmentManager().findFragmentById(R.id.monDangGoiFrag);
        dsMonDangGoiFrag.getMonAnFromDSMonAnFrag(monAn);
    }

    @Override
    public void sendTenNhomHang(String tenNhomHang) {
        DSMonAnFragment dsMonAnFrag = (DSMonAnFragment) getSupportFragmentManager().findFragmentById(R.id.monAnFrag);
        dsMonAnFrag.getTenNhomHangFromDSNhomHangFrag(tenNhomHang);
    }

    @Override
    public void sendTongTien(double tongTienMonAnMoi) {
        tongTien += tongTienMonAnMoi;
        tongTienTxtView.setText(NumberFormat.getCurrencyInstance().format(tongTien));
    }

    public ArrayList<String> readPreferences() {
        SharedPreferences sharePre = this.getSharedPreferences("mySharePre", MODE_PRIVATE);
        String maNV = sharePre.getString("maNhanVien", "");
        String tenNV = sharePre.getString("tenNhanVien", "");
        ArrayList<String> stringLst = new ArrayList<>();

        stringLst.add(maNV);
        stringLst.add(tenNV);

        return stringLst;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Xác nhận");
        builder.setMessage("Nếu quay lại, các món đang chọn sẽ bị hủy. Bạn có muốn quay lại màn hình trước?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
    }
}
