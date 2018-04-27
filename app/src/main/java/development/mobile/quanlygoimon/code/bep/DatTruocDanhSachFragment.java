package development.mobile.quanlygoimon.code.bep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocDanhSachFragment extends Fragment{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private List<PhieuDatTruoc> phieuDatTruocArrayList = null;
    private ArrayAdapter<PhieuDatTruoc> phieuDatTruocArrayAdapter = null;
    private ListView lvPhieuDatTruoc;

    public DatTruocDanhSachFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_danhsach_dattruoc, container, false);

        lvPhieuDatTruoc  = (ListView) view.findViewById(R.id.lv_ctdattruoc_dattruoc);

        phieuDatTruocArrayList = new ArrayList<PhieuDatTruoc>();
        phieuDatTruocArrayAdapter = new DatTruocDanhSachAdapter(getActivity(), R.layout.item_list_bep_dattruoc, phieuDatTruocArrayList);
        lvPhieuDatTruoc.setAdapter(phieuDatTruocArrayAdapter);
        getAllPhieuDatTruoc();

        lvPhieuDatTruoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                PhieuDatTruoc phieuDatTruoc = phieuDatTruocArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("madt", phieuDatTruoc.getMaDatTruoc());
                bundle.putString("thoigiannhan", phieuDatTruoc.getThoiGianNhanBan());
                bundle.putString("soban", phieuDatTruoc.getSoLuongBan() + "");
                bundle.putString("songuoi", phieuDatTruoc.getSoLuongKhach() + "");

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_rep = fm.beginTransaction();
                DatTruocChiTietFragment datTruocChiTietFragment = new DatTruocChiTietFragment();
                datTruocChiTietFragment.setArguments(bundle);
                ft_rep.replace(R.id.frame_layout_holder, datTruocChiTietFragment);
                ft_rep.commit();
            }
        });

        return view;
    }

    private void getAllPhieuDatTruoc(){
        myRef.child("PhieuDatTruoc").orderByChild("thoiGianNhanBan").startAt(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).endAt(new SimpleDateFormat("dd/MM/yyyy 23:59").format(new Date())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phieuDatTruocArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PhieuDatTruoc phieuDatTruoc = child.getValue(PhieuDatTruoc.class);
                    phieuDatTruocArrayList.add(phieuDatTruoc);
                    Log.d("\n\n", "PhieuDatTruoc: " + phieuDatTruoc.toString());
                }
                phieuDatTruocArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
