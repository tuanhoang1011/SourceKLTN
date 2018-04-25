package development.mobile.quanlygoimon.code.phucvuchonban;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.dangnhap.LoginActivity;

public class PhucVuActivity extends AppCompatActivity {
    private TextView titleToolbarTxtView, dangXuatTxtView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private String maNV, tenNV, pushKeyNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phucvu);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_phucVuAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleToolbarTxtView = (TextView) findViewById(R.id.toolbarTitle_phucVuAct);
        dangXuatTxtView = (TextView) findViewById(R.id.dangXuatTxtView_phucVuAct);
        readPreferences();

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        dangXuatTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhucVuActivity.this);

                final EditText input = new EditText(PhucVuActivity.this);
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

                                    Intent intent = new Intent(PhucVuActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(PhucVuActivity.this, "Mật khẩu quản lý không chính xác!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(PhucVuActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
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
