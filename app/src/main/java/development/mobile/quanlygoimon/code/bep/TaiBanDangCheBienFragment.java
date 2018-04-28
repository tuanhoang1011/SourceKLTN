package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TaiBanDangCheBienFragment extends Fragment {

    private List<ChiTietHoaDon> chiTietHoaDonArrayList = null;
    private BepTaiBanDangCheBienAdapter bepTaiBanDangCheBienAdapter = null;
    private ListView lvCTHD;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public TaiBanDangCheBienFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_taiban_dangchebien, container, false);
        lvCTHD  = (ListView) view.findViewById(R.id.lv_cthd_taiban_dangchebien);
        chiTietHoaDonArrayList = new ArrayList<ChiTietHoaDon>();
        bepTaiBanDangCheBienAdapter = new BepTaiBanDangCheBienAdapter(getActivity(), R.layout.item_list_fm_bep_taiban_dangchebien, chiTietHoaDonArrayList);
        lvCTHD.setAdapter(bepTaiBanDangCheBienAdapter);
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
                                && childOfChild.child("trangThai").getValue(String.class).equals("Đang chế biến")) {
                            ChiTietHoaDon cthd = childOfChild.getValue(ChiTietHoaDon.class);
                            cthd.setMaHoaDon(child.child("maHoaDon").getValue(String.class));
                            cthd.setPushkeyHD(child.getKey());
                            cthd.setPushKeyCTHD(childOfChild.getKey());
                            chiTietHoaDonArrayList.add(cthd);
                        }
                    }
                }
                bepTaiBanDangCheBienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}