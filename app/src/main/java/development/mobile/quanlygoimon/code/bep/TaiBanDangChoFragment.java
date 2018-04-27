package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.goimon.ListViewNhomHangAdapter;

public class TaiBanDangChoFragment extends Fragment{

    private TextView tvIdHD, tvTenMonAn, tvSoLuong;
    private ImageButton btnCheBien, btnTamNgungPhucVu;
    private List<ChiTietHoaDon> chiTietHoaDonArrayList = null;
    private BepTaiBanDangChoAdapter bepTaiBanDangChoAdapter = null;
    private ListView lvCTHD;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public TaiBanDangChoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_taiban_dangcho, container, false);
        lvCTHD  = (ListView) view.findViewById(R.id.lv_cthd_taiban_dangcho);
        chiTietHoaDonArrayList = new ArrayList<ChiTietHoaDon>();
        bepTaiBanDangChoAdapter = new BepTaiBanDangChoAdapter(getActivity(), R.layout.item_list_fm_bep_taiban_dangcho, chiTietHoaDonArrayList);
        lvCTHD.setAdapter(bepTaiBanDangChoAdapter);
        getAllHD();

        return view;
    }

    private void getAllHD(){
        myRef.child("HoaDon").orderByChild("daThanhToan").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chiTietHoaDonArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for(DataSnapshot childOfChild : child.child("chiTietHoaDon").getChildren()){
                        if (childOfChild.child("loai").getValue(String.class).equals("Bếp")
                                && childOfChild.child("trangThai").getValue(String.class).equals("Đang chờ")) {
                            ChiTietHoaDon cthd = childOfChild.getValue(ChiTietHoaDon.class);
                            cthd.setMaHoaDon(child.child("maHoaDon").getValue(String.class));
                            chiTietHoaDonArrayList.add(cthd);
                        }
                    }
                }
                bepTaiBanDangChoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
//        myRef.child("HoaDon").orderByChild("daThanhToan").equalTo(false).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for(DataSnapshot child : dataSnapshot.child("chiTietHoaDon").getChildren()){
//                    if (child.child("trangThai").getValue(String.class).equals("Đang chờ") &&
//                            child.child("loai").getValue(String.class).equals("Bếp")) {
//                        ChiTietHoaDon cthd = child.getValue(ChiTietHoaDon.class);
//                        cthd.setMaHoaDon(dataSnapshot.child("maHoaDon").getValue(String.class));
//                        cthd.setPushkeyHD(dataSnapshot.getKey());
//                        cthd.setPushKeyCTHD(child.getKey());
//                        chiTietHoaDonArrayList.add(cthd);
//                    }
//                }
//                bepTaiBanDangChoAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                for(DataSnapshot child : dataSnapshot.child("chiTietHoaDon").getChildren()){
//                    if (child.child("trangThai").getValue(String.class).equals("Đang chờ") &&
//                            child.child("loai").getValue(String.class).equals("Bếp")) {
//                        String key= child.getKey();
//                        ChiTietHoaDon cthd = child.getValue(ChiTietHoaDon.class);
//                        cthd.setMaHoaDon(dataSnapshot.child("maHoaDon").getValue(String.class));
//                        chiTietHoaDonArrayList.set(chiTietHoaDonArrayList.indexOf(key), cthd);
//                    }
//                }
//                bepTaiBanDangChoAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}