package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienDanhSachFragment extends Fragment {

    private List<NhanVien> nhanVienArrayList = null;
    private List<NhanVien> nhanVienOrginArrayList = null;
    private QuanLyNhanVienDanhSachAdapter quanLyNhanVienDanhSachAdapter = null;

    private ListView lvDSNV;
    private Button btnThemMoiNV;
    private EditText edtSearch;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public QuanLyNhanVienDanhSachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_quanly_qlnv, container, false);

        lvDSNV  = (ListView) view.findViewById(R.id.lv_nhanvien_quanly_qlnv);
        nhanVienArrayList = new ArrayList<NhanVien>();
        nhanVienOrginArrayList = new ArrayList<NhanVien>();
        quanLyNhanVienDanhSachAdapter = new QuanLyNhanVienDanhSachAdapter(getActivity(), R.layout.item_list_danhsach_fm_quanly_qlnv, nhanVienArrayList);
        lvDSNV.setAdapter(quanLyNhanVienDanhSachAdapter);

        edtSearch = (EditText) view.findViewById(R.id.edi_search_quanly_qlnv);

        getAllNV();

        lvDSNV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                NhanVien nhanVien = nhanVienArrayList.get(position);
                Bundle bundle = new Bundle();
                QuanLyNhanVienChiTietFragment quanLyNhanVienChiTietFragment = new QuanLyNhanVienChiTietFragment();

                bundle.putSerializable("nv", nhanVien);
                quanLyNhanVienChiTietFragment.setArguments(bundle);
                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlnv, quanLyNhanVienChiTietFragment);
                ft_receive.commit();
            }
        });

        btnThemMoiNV = (Button) view.findViewById(R.id.btn_themmoi_quanly_qlnv);

        btnThemMoiNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                QuanLyNhanVienChiTietFragment quanLyNhanVienChiTietFragment = new QuanLyNhanVienChiTietFragment();
                Bundle bundle = new Bundle();

                bundle.putString("btnThemNV", "btnThemNV");
                quanLyNhanVienChiTietFragment.setArguments(bundle);

                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlnv, quanLyNhanVienChiTietFragment);
                ft_receive.commit();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!edtSearch.getText().toString().equals("")) {
                    for (int j = 0; j < nhanVienArrayList.size() - 1; j++) {
                        if (!nhanVienArrayList.get(j).getTenNhanVien().contains(charSequence) && !nhanVienArrayList.get(j).getSoDienThoai().contains(charSequence)) {
                            nhanVienArrayList.remove(nhanVienArrayList.get(j));
                        }
                    }
                    quanLyNhanVienDanhSachAdapter.notifyDataSetChanged();
                } else if (edtSearch.getText().toString().equals("")){
                    nhanVienArrayList.addAll(nhanVienOrginArrayList);
                }
                quanLyNhanVienDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void getAllNV(){
        myRef.child("NhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nhanVienArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    NhanVien nv = child.getValue(NhanVien.class);
                    nv.setPushkeyNV(child.getKey());
                    nhanVienArrayList.add(nv);
                    nhanVienOrginArrayList.add(nv);
                }
                quanLyNhanVienDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}