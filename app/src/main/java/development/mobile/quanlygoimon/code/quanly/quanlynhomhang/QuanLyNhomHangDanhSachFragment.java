package development.mobile.quanlygoimon.code.quanly.quanlynhomhang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import development.mobile.quanlygoimon.code.entity.NhomHang;

public class QuanLyNhomHangDanhSachFragment extends Fragment {

    private List<NhomHang> nhomHangArrayList = null;
    private QuanLyNhomHangDanhSachAdapter quanLyNhomHangDanhSachAdapter = null;

    private ListView lvDSNH;
    private Button btnThemMoiNH;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ValueEventListener listener;

    public QuanLyNhomHangDanhSachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_quanly_qlnh, container, false);

        lvDSNH  = (ListView) view.findViewById(R.id.lv_nhomhang_quanly_qlnh);
        nhomHangArrayList = new ArrayList<NhomHang>();
        quanLyNhomHangDanhSachAdapter = new QuanLyNhomHangDanhSachAdapter(getActivity(), R.layout.item_list_danhsach_fm_quanly_qlnh, nhomHangArrayList);
        lvDSNH.setAdapter(quanLyNhomHangDanhSachAdapter);

        getAllNH();

        lvDSNH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                NhomHang nhomHang = nhomHangArrayList.get(position);
                Bundle bundle = new Bundle();
                QuanLyNhomHangChiTietFragment quanLyNhomHangChiTietFragment = new QuanLyNhomHangChiTietFragment();

                bundle.putSerializable("nh", nhomHang);
                quanLyNhomHangChiTietFragment.setArguments(bundle);
                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlnh, quanLyNhomHangChiTietFragment);
                ft_receive.commit();
            }
        });

        btnThemMoiNH = (Button) view.findViewById(R.id.btn_themnhomhang_quanly_qlnh);

        btnThemMoiNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                QuanLyNhomHangChiTietFragment quanLyNhomHangChiTietFragment = new QuanLyNhomHangChiTietFragment();
                Bundle bundle = new Bundle();

                bundle.putString("btnThemNH", "btnThemNH");
                quanLyNhomHangChiTietFragment.setArguments(bundle);

                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlnh, quanLyNhomHangChiTietFragment);
                ft_receive.commit();
            }
        });

        return view;
    }

    private void getAllNH(){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nhomHangArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    NhomHang nh = child.getValue(NhomHang.class);
                    nh.setPushKey(child.getKey());
                    nhomHangArrayList.add(nh);
                }
                quanLyNhomHangDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        };
        myRef.child("NhomHang").addValueEventListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            myRef.child("NhomHang").removeEventListener(listener);
        }
    }
}
