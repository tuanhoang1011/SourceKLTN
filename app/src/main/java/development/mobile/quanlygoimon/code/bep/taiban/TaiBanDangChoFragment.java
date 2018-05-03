package development.mobile.quanlygoimon.code.bep.taiban;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;

public class TaiBanDangChoFragment extends Fragment{

    private List<ChiTietHoaDon> chiTietHoaDonArrayList = null;
    private BepTaiBanDangChoAdapter bepTaiBanDangChoAdapter = null;
    private ListView lvCTHD;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private ValueEventListener listener;

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
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chiTietHoaDonArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for(DataSnapshot childOfChild : child.child("chiTietHoaDon").getChildren()){
                        if (childOfChild.child("loai").getValue(String.class).equals("Bếp")
                                && childOfChild.child("trangThai").getValue(String.class).equals("Đang chờ")) {
                            ChiTietHoaDon cthd = childOfChild.getValue(ChiTietHoaDon.class);
                            cthd.setMaHoaDon(child.child("maHoaDon").getValue(String.class));
                            cthd.setPushkeyHD(child.getKey());
                            cthd.setPushKeyCTHD(childOfChild.getKey());
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
        };
        myRef.child("HoaDon").orderByChild("daThanhToan").equalTo(false).addValueEventListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            myRef.child("HoaDon").orderByChild("daThanhToan").equalTo(false).removeEventListener(listener);
        }
    }

}