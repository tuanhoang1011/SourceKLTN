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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;
import development.mobile.quanlygoimon.code.entity.MonAn;

public class TaiBanDangChoFragment extends Fragment{

    private TextView tvIdHD, tvTenMonAn, tvSoLuong;
    private ImageButton btnCheBien, btnTamNgungPhucVu;
    private ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList = null;
    private BepTaiBanDangChoAdapter bepTaiBanDangChoAdapter = null;
    ListView lvCTHD;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public TaiBanDangChoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_taiban_dangcho, container, false);

        tvIdHD = (TextView) view.findViewById(R.id.tv_idhd_item_taiban_dangcho);
        tvTenMonAn = (TextView) view.findViewById(R.id.tv_tenmonan_item_taiban_dangcho);
        tvSoLuong = (TextView) view.findViewById(R.id.tv_soluong_item_taiban_dangcho);
        btnCheBien = (ImageButton) view.findViewById(R.id.ibtn_chebien_item_taiban_dangcho);
        btnTamNgungPhucVu = (ImageButton) view.findViewById(R.id.ibtn_tamngungphucvu_item_taiban_dangcho);
        lvCTHD  = (ListView) view.findViewById(R.id.lv_cthd_taiban_dangcho);

        lvCTHD.setAdapter(bepTaiBanDangChoAdapter);
        chiTietHoaDonArrayList = new ArrayList<ChiTietHoaDon>();
        getAllCTHD();
        bepTaiBanDangChoAdapter = new BepTaiBanDangChoAdapter(getActivity(), R.layout.item_list_fm_bep_taiban_dangcho, chiTietHoaDonArrayList);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getAllCTHD(){
        myRef.child("HoaDon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chiTietHoaDonArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("====HoaDon", child.toString());
                    for(DataSnapshot childOfChild : child.child("chiTietHoaDon").getChildren()){
                        Log.d("=====chiTietHoaDon", "onDataChange: " + child.toString());
                        ChiTietHoaDon cthd = childOfChild.getValue(ChiTietHoaDon.class);
                        cthd.setMaHoaDon(child.child("maHoaDon").getValue().toString());
                        chiTietHoaDonArrayList.add(cthd);
                    }
                }
                bepTaiBanDangChoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}