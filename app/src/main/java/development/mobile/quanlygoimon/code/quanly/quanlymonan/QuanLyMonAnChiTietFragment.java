package development.mobile.quanlygoimon.code.quanly.quanlymonan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.entity.NhomHang;

public class QuanLyMonAnChiTietFragment extends Fragment {

    private Button btnLuu, btnSua;
    private EditText edtMaMA, edtTenMA, edtGia;
    private Spinner snLoai, snMaNhomHang;
    private CheckBox ckTrangThai;
    private ImageView imgMonAn;
    private TextView tvTitle;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private String pushkeyMAHienTai;
    private MonAn ma = new MonAn();
    private ArrayList<String> loaiLst;
    private ArrayList<String> tenNhomHangArrayList;
    private ArrayList<NhomHang> nhomHangArrayList;
    private ArrayAdapter<String> tenNhomHangArrayAdapter = null;


    public QuanLyMonAnChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlma, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tv_title_chitiet_quanly_qlma);
        imgMonAn = (ImageView) view.findViewById(R.id.img_anhmonan_chitiet_quanly_qlma);
        edtMaMA = (EditText) view.findViewById(R.id.edt_mama_chitiet_quanly_qlma);
        edtTenMA = (EditText) view.findViewById(R.id.edt_tenma_chitiet_quanly_qlma);
        snLoai = (Spinner) view.findViewById(R.id.sn_loai_chitiet_quanly_qlma);
        edtGia = (EditText) view.findViewById(R.id.edt_gia_chitiet_quanly_qlma);
        ckTrangThai = (CheckBox) view.findViewById(R.id.ck_trangthai_chitiet_quanly_qlma);
        snMaNhomHang = (Spinner) view.findViewById(R.id.spn_manhomhang_chitiet_quanly_qlma);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlma);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlma);

        loaiLst = new ArrayList<>();

        loaiLst.add("Bếp");
        loaiLst.add("Pha chế");
        loaiLst.add("Ăn liền");

        ArrayAdapter<String> loaiAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, loaiLst);
        loaiAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snLoai.setAdapter(loaiAdapter);

        nhomHangArrayList = new ArrayList<>();
        tenNhomHangArrayList = new ArrayList<>();
        tenNhomHangArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tenNhomHangArrayList);
        tenNhomHangArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snMaNhomHang.setAdapter(tenNhomHangArrayAdapter);

        edtMaMA.setEnabled(false);
        setFocusEditText(false);
        alertErrorDataInput();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("ma") != null) {
                ma = (MonAn) bundle.getSerializable("ma");
                loadMA(ma);
                btnSua.setEnabled(true);
                tvTitle.setText("Chi tiết món ăn");
            }
            else if (bundle.getString("btnThemMA") != null) {
                tvTitle.setText("Thêm mới món ăn");
                setFocusEditText(true);
                edtMaMA.setEnabled(true);
                btnSua.setText("Hủy");
            }
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSua.getText().toString().equals("Sửa")) {
                    setFocusEditText(true);
                    edtMaMA.setEnabled(false);
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Thêm")) {
                    setFocusEditText(true);
                    clearDataInputMA();
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Hủy")) {
                    if (tvTitle.getText().equals("Chi tiết món ăn")) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        loadMA(ma);
                    } else if (tvTitle.getText().equals("Thêm mới món ăn")) {
                        setFocusEditText(false);
                        btnSua.setText("Thêm");
                        btnSua.setEnabled(true);
                        clearDataInputMA();
                    }
                    clearAlertErrorDataInput();
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitle.getText().equals("Chi tiết món ăn")) {
                    boolean check = true;

                    String regexTenMA = "^[\\p{L}\\s]+$";
                    if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                        edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                        check = false;
                    }

                    String regexGia = "^\\d$";
                    if (!Pattern.matches(regexGia, edtGia.getText())) {
                        edtGia.setError("Vui lòng nhập đúng định dạng số");
                        check = false;
                    }

                    if (check) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        MonAn updateMA = new MonAn(ma.getMaMonAn(), edtTenMA.getText().toString(), snLoai.getSelectedItem().toString(),
                                Double.parseDouble(edtGia.getText().toString()), ckTrangThai.isChecked(),
                                Integer.parseInt(snMaNhomHang.getSelectedItem().toString()), "");
                        suaThongTinMonAn(updateMA);
                    }
                } else if (tvTitle.getText().equals("Thêm mới món ăn")) {
                    myRef.child("MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean check = true;

                            String regexTenMA = "^[\\p{L}\\s]+$";
                            if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                                edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                                check = false;
                            }

                            String regexGia = "^\\d$";
                            if (!Pattern.matches(regexGia, edtGia.getText())) {
                                edtGia.setError("Vui lòng nhập đúng định dạng số");
                                check = false;
                            }

                            if (check) {
                                setFocusEditText(false);
                                btnSua.setEnabled(true);
                                btnSua.setText("Thêm");
                                MonAn newMA = new MonAn(ma.getMaMonAn(), edtTenMA.getText().toString(), snLoai.getSelectedItem().toString(),
                                        Double.parseDouble(edtGia.getText().toString()), ckTrangThai.isChecked(),
                                        Integer.parseInt(snMaNhomHang.getSelectedItem().toString()), "");

                                themMonAn(newMA);
                                clearDataInputMA();
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

    private void loadMA(MonAn ma) {
        edtMaMA.setText(ma.getMaMonAn() + "");
        edtTenMA.setText(ma.getTenMonAn().toString());

        for (int i = 0; i < loaiLst.size() - 1; i++) {
            if (ma.getLoai().equals(loaiLst.get(i))) {
                snLoai.setSelection(i);
                break;
            }
        }

        edtGia.setText(ma.getGia() + "");

        if (ma.isTrangThai())
            ckTrangThai.setChecked(true);
        else
            ckTrangThai.setChecked(false);

        pushkeyMAHienTai = ma.getPushKey();

        for (int i = 0; i < nhomHangArrayList.size() - 1; i++) {
            if (ma.getMaNhomHang() == nhomHangArrayList.get(i).getMaNhomHang()) {
                snMaNhomHang.setSelection(i);
                break;
            }
        }
    }

    private void themMonAn(MonAn newMA){
        final int maMA = newMA.getMaMonAn();

//        myRef.child("KhuVuc").child(khuVucArrayList.get(snKhuVuc.getSelectedItemPosition()).getPushkeyKV()).child("danhSachBan").push().setValue(newBan).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getActivity(), "Đã thêm món ăn " + maMA, Toast.LENGTH_LONG).show();
//            }
//        });

//        myRef.child("NhomHang").child("MonAn").push().setValue(newMA).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getActivity(), "Đã thêm món ăn " + maMA, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void suaThongTinMonAn(MonAn updateMA){
//        updateMA.setMaMonAn(ma.getMaMonAn());
//        Map<String, Object> maMapValues = updateMA.toMap();
//
//        myRef.child("MonAn").child(pushkeyMAHienTai).updateChildren(maMapValues);
//        Toast.makeText(getActivity(), "Đã cập nhật thông tin món ăn " + updateMA.getMaMonAn(), Toast.LENGTH_LONG).show();
    }

    private void getAllNhomHang(){
        myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tenNhomHangArrayList.clear();
                nhomHangArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    tenNhomHangArrayList.add(child.child("tenNhomHang").getValue(String.class));
                    NhomHang nh = child.getValue(NhomHang.class);
                    nh.setPushKey(child.getKey());
                    nhomHangArrayList.add(nh);
                }
                tenNhomHangArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Lỗi. Không thể lấy dữ liệu nhóm hàng: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertErrorDataInput() {
        edtTenMA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regexTenMA = "^[\\p{L}\\s]+$";
                if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                    edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                }
            }
        });

        edtGia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regexGia = "^\\d$";
                if (!Pattern.matches(regexGia, edtGia.getText())) {
                    edtGia.setError("Vui lòng nhập đúng định dạng số");
                }
            }
        });
    }

    private void setFocusEditText(boolean b) {
        edtMaMA.setFocusable(b);
        edtMaMA.setFocusableInTouchMode(b);
        edtTenMA.setFocusable(b);
        edtTenMA.setFocusableInTouchMode(b);
        edtGia.setFocusable(b);
        edtGia.setFocusableInTouchMode(b);

        edtMaMA.setEnabled(b);
        snLoai.setEnabled(b);
        snMaNhomHang.setEnabled(b);
        ckTrangThai.setEnabled(b);
        btnSua.setEnabled(b);
        btnLuu.setEnabled(b);
    }

    private void clearAlertErrorDataInput() {
        edtMaMA.setError(null);
        edtTenMA.setError(null);
        edtGia.setError(null);
    }

    private void clearDataInputMA() {
        edtMaMA.setText("");
        edtTenMA.setText("");
        edtGia.setText("");
        snLoai.setSelection(0);
        snMaNhomHang.setSelection(0);
    }
}