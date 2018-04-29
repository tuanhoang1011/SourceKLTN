package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienChiTietFragment extends Fragment {

    private Button btnLuu, btnSua;
    private EditText edtMaNV, edtTenNV, edtNgaySinh, edtDiaChi, edtSDT;
    private Spinner spnChucVu;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    NhanVien nv = new NhanVien();

    public QuanLyNhanVienChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlnv, container, false);

        edtMaNV = (EditText) view.findViewById(R.id.edt_manv_chitiet_quanly_qlnv);
        edtTenNV = (EditText) view.findViewById(R.id.edt_tennv_chitiet_quanly_qlnv);
        edtNgaySinh = (EditText) view.findViewById(R.id.edt_ngaysinh_chitiet_quanly_qlnv);
        edtDiaChi = (EditText) view.findViewById(R.id.edt_diachi_chitiet_quanly_qlnv);
        edtSDT = (EditText) view.findViewById(R.id.edt_sdt_chitiet_quanly_qlnv);
        spnChucVu = (Spinner) view.findViewById(R.id.spn_chucvu_chitiet_quanly_qlnv);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlnv);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlnv);

        List<String> chucVuLst= new ArrayList<>();
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            nv = (NhanVien) bundle.getSerializable("nv");

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

            btnSua.setEnabled(true);
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFocusEditText(true);
                btnSua.setEnabled(false);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFocusEditText(false);
                btnSua.setEnabled(true);
                saveThongTinNhanVien();
            }
        });

        return view;
    }

    private void saveThongTinNhanVien(){
        NhanVien updateNV = new NhanVien(nv.getMaNhanVien(), edtTenNV.getText().toString(),
                edtNgaySinh.getText().toString(), edtDiaChi.getText().toString(),
                edtSDT.getText().toString(), spnChucVu.getSelectedItem().toString(), nv.isDangNhap());

        Map<String, Object> nvMapValues = updateNV.toMap();
        myRef.child("NhanVien").child(nv.getPushkeyNV()).updateChildren(nvMapValues);

        Toast.makeText(getActivity(), "Đã cập nhật thông tin nhân viên " + nv.getMaNhanVien(), Toast.LENGTH_SHORT).show();
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
}