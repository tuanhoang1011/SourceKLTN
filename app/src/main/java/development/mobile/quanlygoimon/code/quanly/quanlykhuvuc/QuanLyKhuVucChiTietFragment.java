package development.mobile.quanlygoimon.code.quanly.quanlykhuvuc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.regex.Pattern;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.KhuVuc;

public class QuanLyKhuVucChiTietFragment extends Fragment {

    private Button btnLuu, btnSua;
    private EditText edtMaKV, edtTenKV;
    private TextView tvTitle;


    private KhuVuc kv = new KhuVuc();
    private String pushkeyKVHienTai;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public QuanLyKhuVucChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlkv, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tv_title_chitiet_quanly_qlkv);
        edtMaKV = (EditText) view.findViewById(R.id.edt_makv_chitiet_quanly_qlkv);
        edtTenKV = (EditText) view.findViewById(R.id.edt_tenkv_chitiet_quanly_qlkv);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlkv);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlkv);

        edtMaKV.setEnabled(false);
        setFocusEditText(false);
        alertErrorDataInput();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("kv") != null) {
                kv = (KhuVuc) bundle.getSerializable("kv");
                loadKV(kv);
                btnSua.setEnabled(true);
                tvTitle.setText("Chi tiết khu vực");
                setVisibleMaKV(View.VISIBLE);
            }
            else if (bundle.getString("btnThemKV") != null) {
                tvTitle.setText("Thêm mới khu vực");
                setFocusEditText(true);
                setVisibleMaKV(View.GONE);
                btnSua.setText("Hủy");
            }
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSua.getText().toString().equals("Sửa")) {
                    setFocusEditText(true);
                    edtMaKV.setEnabled(false);
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Thêm")) {
                    setFocusEditText(true);
                    setVisibleMaKV(View.INVISIBLE);
                    clearDataInputKV();
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Hủy")) {
                    if (tvTitle.getText().equals("Chi tiết khu vực")) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        loadKV(kv);
                    } else if (tvTitle.getText().equals("Thêm mới khu vực")) {
                        setFocusEditText(false);
                        btnSua.setText("Thêm");
                        btnSua.setEnabled(true);
                        clearDataInputKV();
                    }
                    setVisibleMaKV(View.VISIBLE);
                    clearAlertErrorDataInput();
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitle.getText().equals("Chi tiết khu vực")) {
                    boolean check = true;

                    String regexTenKV = "^[\\p{L}\\s\\d]+$";
                    if (!Pattern.matches(regexTenKV, edtTenKV.getText())) {
                        edtTenKV.setError("Vui lòng nhập mã khu vực là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                        check = false;
                    }

                    if (check) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        KhuVuc updateKV = new KhuVuc(edtTenKV.getText().toString());
                        suaThongTinKhuVuc(updateKV);
                    }
                } else if (tvTitle.getText().equals("Thêm mới khu vực")) {

                    myRef.child("KhuVuc").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean check = true;

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if (child.child("tenKhuVuc").getValue(String.class).equalsIgnoreCase( edtTenKV.getText().toString())) {
                                    edtTenKV.setError("Tên khu vực trùng. Vui lòng nhập lại");
                                    check = false;
                                    break;
                                }
                            }

                            String regexTenKV = "^[\\p{L}\\s\\d]+$";
                            if (!Pattern.matches(regexTenKV, edtTenKV.getText())) {
                                edtTenKV.setError("Vui lòng nhập tên khu vực là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                                check = false;
                            }

                            if (check) {
                                setFocusEditText(false);
                                btnSua.setEnabled(true);
                                btnSua.setText("Thêm");
                                KhuVuc newKV = new KhuVuc(edtTenKV.getText().toString().trim());

                                themKhuVuc(newKV);
                                clearDataInputKV();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return view;
    }

    private void themKhuVuc(final KhuVuc newKV){
        myRef.child("KhuVuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int maxMaKV = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int id = child.child("maKhuVuc").getValue(Integer.class);
                    if (id > maxMaKV) {
                        maxMaKV = id;
                    }
                }

                newKV.setMaKhuVuc(maxMaKV + 1);
                myRef.child("KhuVuc").push().setValue(newKV).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Đã thêm khu vực " + newKV.getMaKhuVuc(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void suaThongTinKhuVuc(KhuVuc updateKV){
        updateKV.setMaKhuVuc(kv.getMaKhuVuc());
        Map<String, Object> kvMapValues = updateKV.toMap();
        final int maKV = updateKV.getMaKhuVuc();

        myRef.child("KhuVuc").child(pushkeyKVHienTai).updateChildren(kvMapValues).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Đã cập nhật thông tin khu vực " + maKV, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alertErrorDataInput() {
        edtTenKV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regex = "^[\\p{L}\\s\\d]+$";
                if (!Pattern.matches(regex, edtTenKV.getText())) {
                    edtTenKV.setError("Vui lòng nhập mã khu vực là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                }
            }
        });
    }

    private void loadKV(KhuVuc kv) {
        edtMaKV.setText(kv.getMaKhuVuc() + "");
        edtTenKV.setText(kv.getTenKhuVuc().toString());

        pushkeyKVHienTai = kv.getPushkeyKV();
    }

    private void setFocusEditText(boolean b) {
        edtMaKV.setFocusable(b);
        edtMaKV.setFocusableInTouchMode(b);
        edtTenKV.setFocusable(b);
        edtTenKV.setFocusableInTouchMode(b);

        edtMaKV.setEnabled(b);
        btnSua.setEnabled(b);
        btnLuu.setEnabled(b);
    }

    private void clearAlertErrorDataInput() {
        edtMaKV.setError(null);
        edtTenKV.setError(null);
    }

    private void setVisibleMaKV(int visibleMaKV) {
        edtMaKV.setVisibility(visibleMaKV);
    }

    private void clearDataInputKV() {
        edtMaKV.setText("");
        edtTenKV.setText("");
    }
}
