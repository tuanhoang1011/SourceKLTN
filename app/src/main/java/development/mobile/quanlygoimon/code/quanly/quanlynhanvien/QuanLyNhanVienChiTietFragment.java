package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienChiTietFragment extends Fragment {

    private Button btnLuu, btnSua;
    private EditText edtMaNV, edtTenNV, edtNgaySinh, edtDiaChi, edtSDT;
    private Spinner spnChucVu;
    private TextView tvTitle;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    NhanVien nv = new NhanVien();
    List<String> chucVuLst= new ArrayList<>();

    public QuanLyNhanVienChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlnv, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tv_title_chitiet_quanly_qlnv);
        edtMaNV = (EditText) view.findViewById(R.id.edt_manv_chitiet_quanly_qlnv);
        edtTenNV = (EditText) view.findViewById(R.id.edt_tennv_chitiet_quanly_qlnv);
        edtNgaySinh = (EditText) view.findViewById(R.id.edt_ngaysinh_chitiet_quanly_qlnv);
        edtDiaChi = (EditText) view.findViewById(R.id.edt_diachi_chitiet_quanly_qlnv);
        edtSDT = (EditText) view.findViewById(R.id.edt_sdt_chitiet_quanly_qlnv);
        spnChucVu = (Spinner) view.findViewById(R.id.spn_chucvu_chitiet_quanly_qlnv);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlnv);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlnv);

        chucVuLst.add("PhucVu");
        chucVuLst.add("Bep");
        chucVuLst.add("PhaChe");
        chucVuLst.add("QuanLy");
        ArrayAdapter<String> chucVuArrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, chucVuLst);
        chucVuArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChucVu.setAdapter(chucVuArrayAdapter);

        edtMaNV.setEnabled(false);
        setFocusEditText(false);
        alertErrorDataInput();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("nv") != null) {
                nv = (NhanVien) bundle.getSerializable("nv");
                loadNV(nv);
                btnSua.setEnabled(true);
                tvTitle.setText("Chi tiết nhân viên");
            }
            else if (bundle.getString("btnThemNV") != null) {
                tvTitle.setText("Thêm mới nhân viên");
                setFocusEditText(true);
                btnSua.setText("Hủy");
            }
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSua.getText().toString().equals("Sửa")) {
                    setFocusEditText(true);
                    btnSua.setText("Hủy");
                }
                else if (btnSua.getText().toString().equals("Hủy")) {
                    if (tvTitle.getText().equals("Chi tiết nhân viên")) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        loadNV(nv);
                    } else if (tvTitle.getText().equals("Thêm mới nhân viên")) {
                        setFocusEditText(false);
                        clearDataInputNV();
                    }
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidDataInput()) {
                    setFocusEditText(false);
                    btnSua.setEnabled(true);
                    btnSua.setText("Sửa");
                    saveThongTinNhanVien();
                }
            }
        });

        return view;
    }

    private void clearDataInputNV() {
        edtMaNV.setText("");
        edtTenNV.setText("");
        edtNgaySinh.setText("");
        edtDiaChi.setText("");
        edtSDT.setText("");
        spnChucVu.setSelection(0);
    }

    private void loadNV(NhanVien nv) {
        edtMaNV.setText(nv.getMaNhanVien() + "");
        edtTenNV.setText(nv.getTenNhanVien().toString());
        edtNgaySinh.setText(nv.getNgaySinh().toString());
        edtDiaChi.setText(nv.getDiaChi().toString());
        edtSDT.setText(nv.getSoDienThoai().toString());

        for (int i = 0; i < chucVuLst.size() - 1; i++) {
            if (nv.getChucVu().equals(chucVuLst.get(i))) {
                spnChucVu.setSelection(i);
                break;
            }
        }
    }

    private void saveThongTinNhanVien(){
        NhanVien updateNV = new NhanVien(nv.getMaNhanVien(), edtTenNV.getText().toString(),
                edtNgaySinh.getText().toString(), edtDiaChi.getText().toString(),
                edtSDT.getText().toString(), spnChucVu.getSelectedItem().toString(), nv.isDangNhap());
        Map<String, Object> nvMapValues = updateNV.toMap();

        myRef.child("NhanVien").child(nv.getPushkeyNV()).updateChildren(nvMapValues);
        Toast.makeText(getActivity(), "Đã cập nhật thông tin nhân viên " + nv.getMaNhanVien(), Toast.LENGTH_LONG).show();
    }

    private void setFocusEditText(boolean b) {
        edtTenNV.setFocusable(b);
        edtTenNV.setFocusableInTouchMode(b);
        edtNgaySinh.setFocusable(b);
        edtNgaySinh.setFocusableInTouchMode(b);
        edtDiaChi.setFocusable(b);
        edtDiaChi.setFocusableInTouchMode(b);
        edtSDT.setFocusable(b);
        edtSDT.setFocusableInTouchMode(b);

        spnChucVu.setEnabled(b);
        btnSua.setEnabled(b);
        btnLuu.setEnabled(b);
    }

    private void alertErrorDataInput() {
        edtTenNV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtTenNV.getText().length() < 1)
                    edtTenNV.setError("Vui lòng nhập tên nhân từ 1 ký tự trở lên");
            }
        });
        edtNgaySinh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regex = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/(19\\d{2}|2\\d{3})$";

                if (!Pattern.matches(regex, edtNgaySinh.getText()))
                    edtNgaySinh.setError("Vui lòng nhập đúng định dạng ngày dd/mm/yyyy và năm từ 1900 đến 2999");
            }
        });

        edtDiaChi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtDiaChi.getText().length() < 1)
                    edtDiaChi.setError("Vui lòng nhập tên nhân từ 1 ký tự trở lên");
            }
        });

        edtSDT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regex = "^0\\d{9}|0\\d{10}|0\\d{11}$";
                if (!Pattern.matches(regex, edtSDT.getText()))
                    edtSDT.setError("Vui lòng nhập đúng định dạng số điện thoại");
            }
        });
    }

    private boolean isValidDataInput() {
        if (edtTenNV.getText().length() < 1) {
//            edtTenNV.setError("Vui lòng nhập tên nhân từ 1 ký tự trở lên");
            return false;
        }

        String regexNgaySinh = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/(19\\d{2}|2\\d{3})$";
        if (!Pattern.matches(regexNgaySinh, edtNgaySinh.getText())) {
//            edtNgaySinh.setError("Vui lòng nhập đúng định dạng ngày dd/mm/yyyy và năm từ 1900 đến 2999");
            return false;
        }

        if (edtDiaChi.getText().length() < 1) {
//            edtDiaChi.setError("Vui lòng nhập tên nhân từ 1 ký tự trở lên");
            return false;
        }

        String regexSDT = "^0\\d{9}|0\\d{10}|0\\d{11}$";
        if (!Pattern.matches(regexSDT, edtSDT.getText())) {
//            edtSDT.setError("Vui lòng nhập đúng định dạng số điện thoại");
            return false;
        }

        return true;
    }
}