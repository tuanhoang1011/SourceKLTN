package development.mobile.quanlygoimon.code.bep;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.dangnhap.LoginActivity;
import development.mobile.quanlygoimon.code.phucvuchonban.PagerAdapter;
import development.mobile.quanlygoimon.code.phucvuchonban.PhucVuActivity;

public class BepActivity extends AppCompatActivity {

    private ViewPager pagerBep;
    private PagerAdapterBep adapterBep;
    private TabLayout tabLayoutBep;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private TextView titleToolbarTxtView, dangXuatTxtView;
    private String maNV, tenNV, pushKeyNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bep);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_BepAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleToolbarTxtView = (TextView) findViewById(R.id.toolbarTitle_bepAct);
        dangXuatTxtView = (TextView) findViewById(R.id.dangXuatTxtView_bepAct);
        readPreferences();

        pagerBep = (ViewPager) findViewById(R.id.view_pager_bep);
        tabLayoutBep = (TabLayout) findViewById(R.id.tab_layout_bep);
        FragmentManager manager = getSupportFragmentManager();
        adapterBep = new PagerAdapterBep(manager);
        pagerBep.setAdapter(adapterBep);
        tabLayoutBep.setupWithViewPager(pagerBep);
        pagerBep.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutBep));

        dangXuatTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BepActivity.this);

                final EditText input = new EditText(BepActivity.this);
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

                                    Intent intent = new Intent(BepActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(BepActivity.this, "Mật khẩu quản lý không chính xác!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(BepActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
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
