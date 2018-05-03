package development.mobile.quanlygoimon.code.quanly.quanlymonan;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.entity.NhomHang;

public class QuanLyMonAnDanhSachFragment extends Fragment {

    private List<MonAn> monAnArrayList = null;
    private List<MonAn> monAnOrginArrayList = null;
    private QuanLyMonAnDanhSachAdapter quanLyMonAnDanhSachAdapter = null;

    private ListView lvDSMA;
    private Button btnThemMoiMA;
    private EditText edtSearch;
    private Spinner snNhomHang;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ValueEventListener listener;
    private ArrayList<String> tenNhomHangArrayList;
    private ArrayList<NhomHang> nhomHangArrayList;
    private ArrayAdapter<String> tenNhomHangArrayAdapter = null;

    public QuanLyMonAnDanhSachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_quanly_qlma, container, false);

        snNhomHang = (Spinner) view.findViewById(R.id.sn_nhomhang_quanly_qlma);

        lvDSMA  = (ListView) view.findViewById(R.id.lv_monan_quanly_qlma);
        monAnArrayList = new ArrayList<MonAn>();
        monAnOrginArrayList = new ArrayList<MonAn>();
        quanLyMonAnDanhSachAdapter = new QuanLyMonAnDanhSachAdapter(getActivity(), R.layout.item_list_danhsach_fm_quanly_qlma, monAnArrayList);
        lvDSMA.setAdapter(quanLyMonAnDanhSachAdapter);

        tenNhomHangArrayList = new ArrayList<>();
        nhomHangArrayList = new ArrayList<>();
        tenNhomHangArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_dropdown_spinner_khuvuc_thungan, tenNhomHangArrayList);
        tenNhomHangArrayAdapter.setDropDownViewResource(R.layout.item_spinner_khuvuc_thungan);
        snNhomHang.setAdapter(tenNhomHangArrayAdapter);

        edtSearch = (EditText) view.findViewById(R.id.edi_searchmonan_quanly_qlma);

        getAllNhomHang();

        lvDSMA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                MonAn monAn = monAnArrayList.get(position);
                Bundle bundle = new Bundle();
                QuanLyMonAnChiTietFragment quanLyMonAnChiTietFragment = new QuanLyMonAnChiTietFragment();

                bundle.putSerializable("ma", monAn);

                quanLyMonAnChiTietFragment.setArguments(bundle);
                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlma, quanLyMonAnChiTietFragment);
                ft_receive.commit();
            }
        });

        btnThemMoiMA = (Button) view.findViewById(R.id.btn_themmonan_quanly_qlma);

        btnThemMoiMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                QuanLyMonAnChiTietFragment quanLyMonAnChiTietFragment = new QuanLyMonAnChiTietFragment();
                Bundle bundle = new Bundle();

                bundle.putString("btnThemMA", "btnThemMA");
                bundle.putString("pushkeynhomhang", nhomHangArrayList.get(snNhomHang.getSelectedItemPosition()).getPushKey());
                quanLyMonAnChiTietFragment.setArguments(bundle);

                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlma, quanLyMonAnChiTietFragment);
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
                    for (int j = 0; j < monAnArrayList.size() - 1; j++) {
                        if (!monAnArrayList.get(j).getTenMonAn().contains(charSequence) || !monAnArrayList.get(j).getLoai().contains(charSequence)) {
                            monAnArrayList.remove(monAnArrayList.get(j));
                        }
                    }
                    quanLyMonAnDanhSachAdapter.notifyDataSetChanged();
                } else if (edtSearch.getText().toString().equals("")){
                   getAllMonAnTheoNhomHang(snNhomHang.getSelectedItemPosition());
                }
                quanLyMonAnDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        snNhomHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                getAllMonAnTheoNhomHang(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
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

    public void getAllMonAnTheoNhomHang(final int position){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                monAnArrayList.clear();
                for (DataSnapshot child : dataSnapshot.child("danhSachMonAn").getChildren()) {
                    MonAn ma = child.getValue(MonAn.class);
                    ma.setPushKey(child.getKey());
                    ma.setPushKeyNhomHang(nhomHangArrayList.get(position).getPushKey());
                    monAnArrayList.add(ma);
                }
                quanLyMonAnDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        };
        myRef.child("NhomHang").child(nhomHangArrayList.get(position).getPushKey()).addValueEventListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            myRef.child("NhomHang").child(nhomHangArrayList.get(snNhomHang.getSelectedItemPosition()).getPushKey()).removeEventListener(listener);
        }
    }
}
