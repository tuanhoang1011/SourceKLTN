package development.mobile.quanlygoimon.code.quanly.quanlynhomhang;

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
import development.mobile.quanlygoimon.code.entity.NhomHang;

public class QuanLyNhomHangChiTietFragment extends Fragment {

    private Button btnLuu, btnSua;
    private EditText edtMaNH, edtTenNH;
    private TextView tvTitle;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private NhomHang nh = new NhomHang();
    private String pushkeyNHHienTai;

    public QuanLyNhomHangChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlnh, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tv_title_chitiet_quanly_qlnh);
        edtMaNH = (EditText) view.findViewById(R.id.edt_manh_chitiet_quanly_qlnh);
        edtTenNH = (EditText) view.findViewById(R.id.edt_tennh_chitiet_quanly_qlnh);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlnh);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlnh);

        edtMaNH.setEnabled(false);
        setFocusEditText(false);
        alertErrorDataInput();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("nh") != null) {
                nh = (NhomHang) bundle.getSerializable("nh");
                loadNH(nh);
                btnSua.setEnabled(true);
                tvTitle.setText("Chi tiết nhóm hàng");
                setVisibleMaNH(View.VISIBLE);
            }
            else if (bundle.getString("btnThemNH") != null) {
                tvTitle.setText("Thêm mới nhóm hàng");
                setFocusEditText(true);
                setVisibleMaNH(View.GONE);
                btnSua.setText("Hủy");
            }
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSua.getText().toString().equals("Sửa")) {
                    setFocusEditText(true);
                    edtMaNH.setEnabled(false);
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Thêm")) {
                    setFocusEditText(true);
                    setVisibleMaNH(View.INVISIBLE);
                    clearDataInputNH();
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Hủy")) {
                    if (tvTitle.getText().equals("Chi tiết nhóm hàng")) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        loadNH(nh);
                    } else if (tvTitle.getText().equals("Thêm mới nhóm hàng")) {
                        setFocusEditText(false);
                        btnSua.setText("Thêm");
                        btnSua.setEnabled(true);
                        clearDataInputNH();
                    }
                    setVisibleMaNH(View.VISIBLE);
                    clearAlertErrorDataInput();
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitle.getText().equals("Chi tiết nhóm hàng")) {
                    boolean check = true;

                    String regexTenNH = "^[\\p{L}\\s\\d]+$";
                    if (!Pattern.matches(regexTenNH, edtTenNH.getText())) {
                        edtTenNH.setError("Vui lòng nhập mã nhóm hàng là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                        check = false;
                    }

                    if (check) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        NhomHang updateNH = new NhomHang(edtTenNH.getText().toString());
                        suaThongTinNhomHang(updateNH);
                    }
                } else if (tvTitle.getText().equals("Thêm mới nhóm hàng")) {
                    myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean check = true;

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if (child.child("tenNhomHang").getValue(String.class).equals( edtTenNH.getText().toString())) {
                                    edtTenNH.setError("Tên nhóm hàng trùng. Vui lòng nhập lại");
                                    check = false;
                                    break;
                                }
                            }

                            String regexTenNH = "^[\\p{L}\\s\\d]+$";
                            if (!Pattern.matches(regexTenNH, edtTenNH.getText())) {
                                edtTenNH.setError("Vui lòng nhập tên nhóm hàng là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                                check = false;
                            }

                            if (check) {
                                setFocusEditText(false);
                                btnSua.setEnabled(true);
                                btnSua.setText("Thêm");
                                NhomHang newNH = new NhomHang(edtTenNH.getText().toString().trim());

                                themNhomHang(newNH);
                                clearDataInputNH();
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

    private void themNhomHang(final NhomHang newNH){
        myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int maxMaNH = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int id = child.child("maNhomHang").getValue(Integer.class);
                    if (id > maxMaNH) {
                        maxMaNH = id;
                    }
                }

                newNH.setMaNhomHang(maxMaNH + 1);
                myRef.child("NhomHang").push().setValue(newNH).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Đã thêm nhóm hàng " + newNH.getTenNhomHang(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void suaThongTinNhomHang(NhomHang updateNH){
        updateNH.setMaNhomHang(nh.getMaNhomHang());
        Map<String, Object> nhMapValues = updateNH.toMap();
        final String tenNH = updateNH.getTenNhomHang();

        myRef.child("NhomHang").child(pushkeyNHHienTai).updateChildren(nhMapValues).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Đã cập nhật thông tin nhóm hàng " + tenNH, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alertErrorDataInput() {
        edtTenNH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regex = "^[\\p{L}\\s\\d]+$";
                if (!Pattern.matches(regex, edtTenNH.getText())) {
                    edtTenNH.setError("Vui lòng nhập tên nhóm hàng là chữ hoặc số và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                }
            }
        });
    }

    private void loadNH(NhomHang nh) {
        edtMaNH.setText(nh.getMaNhomHang() + "");
        edtTenNH.setText(nh.getTenNhomHang().toString());

        pushkeyNHHienTai = nh.getPushKey();
    }

    private void setFocusEditText(boolean b) {
        edtMaNH.setFocusable(b);
        edtMaNH.setFocusableInTouchMode(b);
        edtTenNH.setFocusable(b);
        edtTenNH.setFocusableInTouchMode(b);

        edtMaNH.setEnabled(b);
        btnSua.setEnabled(b);
        btnLuu.setEnabled(b);
    }

    private void clearAlertErrorDataInput() {
        edtMaNH.setError(null);
        edtTenNH.setError(null);
    }

    private void setVisibleMaNH(int visibleMaNH) {
        edtMaNH.setVisibility(visibleMaNH);
    }

    private void clearDataInputNH() {
        edtMaNH.setText("");
        edtTenNH.setText("");
    }
}
