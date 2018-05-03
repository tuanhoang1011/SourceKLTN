package development.mobile.quanlygoimon.code.quanly.quanlyban;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.Ban;
import development.mobile.quanlygoimon.code.entity.KhuVuc;

public class QuanLyBanActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;
    public static final int RESPONSE_CODE = 1;

    private Button btnThemBan;
    private Spinner snKhuVuc;
    private ListView lvDSBan;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ValueEventListener listener;

    private List<Ban> banArrayList = null;
    private QuanLyBanDanhSachAdapter quanLyBanDanhSachAdapter = null;
    private ArrayAdapter<String> tenKhuVucArrayAdapter = null;
    private ArrayList<String> tenKhuVucArrayList = null;
    private ArrayList<KhuVuc> khuVucArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ban);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnThemBan = (Button) findViewById(R.id.btn_themban_quanly_qlban);
        snKhuVuc = (Spinner) findViewById(R.id.sn_khuvuc_quanly_qlban);
        lvDSBan  = (ListView) findViewById(R.id.lv_dsban_quanly_qlban);

        tenKhuVucArrayList = new ArrayList<>();
        khuVucArrayList = new ArrayList<>();
        tenKhuVucArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tenKhuVucArrayList);
        tenKhuVucArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snKhuVuc.setAdapter(tenKhuVucArrayAdapter);

        banArrayList = new ArrayList<Ban>();
        quanLyBanDanhSachAdapter = new QuanLyBanDanhSachAdapter(this, R.layout.item_list_dsban_quanly_qlban, banArrayList);
        lvDSBan.setAdapter(quanLyBanDanhSachAdapter);

        getAllKhuVuc();

        snKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                getAllBanTheoKhuVuc(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnThemBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyBanActivity.this, QuanLyBanThemBan.class);
                intent.putExtra("tang", snKhuVuc.getSelectedItem().toString());
                intent.putExtra("pushkeykv", khuVucArrayList.get(snKhuVuc.getSelectedItemPosition()).getPushkeyKV());

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void getAllKhuVuc(){
        myRef.child("KhuVuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tenKhuVucArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    tenKhuVucArrayList.add(child.child("tenKhuVuc").getValue(String.class));
                    KhuVuc kv = child.getValue(KhuVuc.class);
                    kv.setPushkeyKV(child.getKey());
                    khuVucArrayList.add(kv);
                }
                tenKhuVucArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuanLyBanActivity.this, "Lỗi. Không thể lấy dữ liệu khu vực: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllBanTheoKhuVuc(final int position){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                banArrayList.clear();
                for (DataSnapshot child : dataSnapshot.child("danhSachBan").getChildren()) {
                    Ban ban = child.getValue(Ban.class);
                    ban.setPushKey(child.getKey());
                    ban.setPushKeyKhuVuc(khuVucArrayList.get(position).getPushkeyKV());
                    banArrayList.add(ban);
                }
                quanLyBanDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuanLyBanActivity.this, "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        };
        myRef.child("KhuVuc").child(khuVucArrayList.get(position).getPushkeyKV()).addValueEventListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESPONSE_CODE) {
            Bundle bundle = data.getBundleExtra("data");
            String maBan = bundle.getString("ma");
            final Ban newBan = new Ban(maBan, "","", "Trống");

            myRef.child("KhuVuc").child(khuVucArrayList.get(snKhuVuc.getSelectedItemPosition()).getPushkeyKV()).child("danhSachBan").push().setValue(newBan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getAllBanTheoKhuVuc(snKhuVuc.getSelectedItemPosition());
                    Toast.makeText(getBaseContext(), "Đã thêm bàn " + newBan.getMaBan() + "cho khu vực " + snKhuVuc.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            myRef.child("KhuVuc").child(khuVucArrayList.get(snKhuVuc.getSelectedItemPosition()).getPushkeyKV()).removeEventListener(listener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (listener != null) {
            myRef.child("KhuVuc").child(khuVucArrayList.get(snKhuVuc.getSelectedItemPosition()).getPushkeyKV()).removeEventListener(listener);
        }
    }
}
