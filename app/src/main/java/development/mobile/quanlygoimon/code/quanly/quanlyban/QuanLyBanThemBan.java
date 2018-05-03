package development.mobile.quanlygoimon.code.quanly.quanlyban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import development.mobile.quanlygoimon.code.R;

public class QuanLyBanThemBan extends AppCompatActivity {

    private Button btnThemBan, btnHuyThemBan;
    private EditText edtMaBan;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private String pushkeykv = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ban_them_ban);

        btnThemBan = (Button) findViewById(R.id.btn_okthemban_quanly_qlban_themban);
        btnHuyThemBan = (Button) findViewById(R.id.btn_huythemban_quanly_qlban_themban);
        edtMaBan  = (EditText) findViewById(R.id.edt_maban_quanly_qlban_thembanact);

        if (getIntent() != null) {
            setTitle("Thêm bàn cho tầng " + getIntent().getStringExtra("tang"));

            pushkeykv = getIntent().getStringExtra("pushkeykv");
        }

        btnThemBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child("KhuVuc").child(pushkeykv).child("danhSachBan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean check = true;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.child("maBan").getValue(String.class).equalsIgnoreCase( edtMaBan.getText().toString())) {
                                edtMaBan.setError("Mã bàn trùng. Vui lòng nhập lại");
                                check = false;
                                break;
                            }
                        }

                        String regexTenKV = "^B([\\p{L}\\s\\d]){1,5}$";
                        if (!Pattern.matches(regexTenKV, edtMaBan.getText())) {
                            edtMaBan.setError("Vui lòng nhập mã bàn là chữ hoặc số và không chứa ký tự đặc biệt tối đa 6 ký tự");
                            check = false;
                        }

                        if (check) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();

                            bundle.putString("ma", edtMaBan.getText().toString().trim());
                            intent.putExtra("data", bundle);

                            setResult(QuanLyBanActivity.RESPONSE_CODE, intent);

                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        btnHuyThemBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
