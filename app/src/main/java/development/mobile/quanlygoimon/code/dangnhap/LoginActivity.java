package development.mobile.quanlygoimon.code.dangnhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.bep.BepActivity;
import development.mobile.quanlygoimon.code.entity.NhanVien;
import development.mobile.quanlygoimon.code.phache.PhaCheActivity;
import development.mobile.quanlygoimon.code.phucvuchonban.PhucVuActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText maNVEditTxt, passEditTxt;
    private Button loginBtn;
    private String maNV, password, pushKey, chucVu, tenNV;
    private boolean dangNhap = false;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        readPreferences();
        setOrientation();

        maNVEditTxt = (EditText) findViewById(R.id.maNVEditTxt);
        passEditTxt = (EditText) findViewById(R.id.passEditTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maNV = maNVEditTxt.getText().toString().trim();
                password = passEditTxt.getText().toString().trim();

                if (maNV.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ mã nhân viên và mật khẩu của quản lý!", Toast.LENGTH_SHORT).show();
                } else {
                    myRef.child("NhanVien").orderByChild("maNhanVien").equalTo(Integer.parseInt(maNV)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    pushKey = child.getKey();
                                    dangNhap = child.child("dangNhap").getValue(Boolean.class);
                                    chucVu = child.child("chucVu").getValue(String.class);
                                    tenNV = child.child("tenNhanVien").getValue(String.class);
                                }

                                myRef.child("NhanVien").orderByChild("matKhau").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() != null) {

                                            boolean isHandset = getResources().getBoolean(R.bool.isHandset);

                                            if ((isHandset && chucVu.equals("PhucVu")) || (!isHandset && !chucVu.equals("PhucVu"))) {
                                                if (dangNhap) {
                                                    Toast.makeText(LoginActivity.this, "Nhân viên này hiện đang đăng nhập. Vui lòng đăng xuất trước khi đăng nhập lại!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    myRef.child("NhanVien").child(pushKey).child("dangNhap").setValue(true);
                                                    writePreferences();
                                                    if (chucVu.trim().equals("PhucVu")) {
                                                        Intent intent = new Intent(LoginActivity.this, PhucVuActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    if (chucVu.trim().equals("Bep")) {
//                                                        myRef.child("NhanVien").push()
//                                                                .setValue(new NhanVien(5555, "Teo", "", "", "", "PhaChe", false));
                                                        Intent intent = new Intent(LoginActivity.this, BepActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    if (chucVu.trim().equals("PhaChe")) {
                                                        Intent intent = new Intent(LoginActivity.this, PhaCheActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            } else {
                                                if (chucVu.equals("PhucVu")) {
                                                    Toast.makeText(LoginActivity.this, "Nhân viên " + chucVu + " chỉ có thể đăng nhập trên các thiết bị di động!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Nhân viên " + chucVu + " chỉ có thể đăng nhập trên các thiết bị máy tính bảng!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Mật khẩu quản lý không chính xác!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Mã nhân viên không tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void writePreferences() {
        SharedPreferences sharePre = getSharedPreferences("mySharePre", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();

        editor.putBoolean("isLoggedIn", true);
        editor.putString("pushKeyNhanVien", pushKey);
        editor.putString("maNhanVien", maNV);
        editor.putString("tenNhanVien", tenNV);
        editor.putString("chucVu", chucVu);
        editor.apply();
    }

    public void readPreferences() {
        SharedPreferences sharePre = this.getSharedPreferences("mySharePre", MODE_PRIVATE);
        boolean isLoggedIin = sharePre.getBoolean("isLoggedIn", false);
        String chucVu = sharePre.getString("chucVu", "");

        if (isLoggedIin) {
            if(chucVu.equals("PhucVu")){
                Intent intent = new Intent(LoginActivity.this, PhucVuActivity.class);
                startActivity(intent);
                finish();
            }
            else if(chucVu.equals("Bep")){
                Intent intent = new Intent(LoginActivity.this, BepActivity.class);
                startActivity(intent);
                finish();
            }
            else if(chucVu.equals("PhaChe")){
                Intent intent = new Intent(LoginActivity.this, PhaCheActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void setOrientation() {

        boolean isHandset = getResources().getBoolean(R.bool.isHandset);

        if (isHandset) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
