package development.mobile.quanlygoimon.code.quanly.quanlykhuvuc;

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
import development.mobile.quanlygoimon.code.entity.KhuVuc;

public class QuanLyKhuVucDanhSachFragment extends Fragment {

    private List<KhuVuc> khuVucArrayList = null;
    private QuanLyKhuVucDanhSachAdapter quanLyKhuVucDanhSachAdapter = null;

    private ListView lvDSKV;
    private Button btnThemMoiKV;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ValueEventListener listener;

    public QuanLyKhuVucDanhSachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_quanly_qlkv, container, false);

        lvDSKV  = (ListView) view.findViewById(R.id.lv_khuvuc_quanly_qlkv);
        khuVucArrayList = new ArrayList<KhuVuc>();
        quanLyKhuVucDanhSachAdapter = new QuanLyKhuVucDanhSachAdapter(getActivity(), R.layout.item_list_danhsach_fm_quanly_qlkv, khuVucArrayList);
        lvDSKV.setAdapter(quanLyKhuVucDanhSachAdapter);

        getAllNV();

        lvDSKV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                KhuVuc khuVuc = khuVucArrayList.get(position);
                Bundle bundle = new Bundle();
                QuanLyKhuVucChiTietFragment quanLyKhuVucChiTietFragment = new QuanLyKhuVucChiTietFragment();

                bundle.putSerializable("kv", khuVuc);
                quanLyKhuVucChiTietFragment.setArguments(bundle);
                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlkv, quanLyKhuVucChiTietFragment);
                ft_receive.commit();
            }
        });

        btnThemMoiKV = (Button) view.findViewById(R.id.btn_themmoi_quanly_qlkv);

        btnThemMoiKV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_receive = fm.beginTransaction();
                QuanLyKhuVucChiTietFragment quanLyKhuVucChiTietFragment = new QuanLyKhuVucChiTietFragment();
                Bundle bundle = new Bundle();

                bundle.putString("btnThemKV", "btnThemKV");
                quanLyKhuVucChiTietFragment.setArguments(bundle);

                ft_receive.replace(R.id.frame_layout_holder_chitiet_quanly_qlkv, quanLyKhuVucChiTietFragment);
                ft_receive.commit();
            }
        });

        return view;
    }

    private void getAllNV(){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                khuVucArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    KhuVuc kv = child.getValue(KhuVuc.class);
                    kv.setPushkeyKV(child.getKey());
                    khuVucArrayList.add(kv);
                }
                quanLyKhuVucDanhSachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        };
        myRef.child("KhuVuc").addValueEventListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            myRef.child("KhuVuc").removeEventListener(listener);
        }
    }
}