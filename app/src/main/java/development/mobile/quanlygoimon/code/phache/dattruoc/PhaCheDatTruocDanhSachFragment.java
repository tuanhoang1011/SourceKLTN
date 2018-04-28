package development.mobile.quanlygoimon.code.phache.dattruoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietPhieuDatTruoc;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class PhaCheDatTruocDanhSachFragment extends Fragment{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private List<PhieuDatTruoc> phieuDatTruocArrayList = null;
    private ArrayAdapter<PhieuDatTruoc> phieuDatTruocArrayAdapter = null;
    private ListView lvPhieuDatTruoc;

    public PhaCheDatTruocDanhSachFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phache_danhsach_dattruoc, container, false);

        phieuDatTruocArrayList = new ArrayList<PhieuDatTruoc>();
        phieuDatTruocArrayAdapter = new PhaCheDatTruocDanhSachAdapter(getActivity(), R.layout.item_list_phache_dattruoc, phieuDatTruocArrayList);
        lvPhieuDatTruoc  = (ListView) view.findViewById(R.id.lv_ctdattruoc_phache_dattruoc);
        lvPhieuDatTruoc.setAdapter(phieuDatTruocArrayAdapter);
        getAllPhieuDatTruoc();

        lvPhieuDatTruoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                PhieuDatTruoc phieuDatTruoc = phieuDatTruocArrayList.get(position);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft_rep = fm.beginTransaction();
                Bundle bundle = new Bundle();
                PhaCheDatTruocChiTietFragment phaCheDatTruocChiTietFragment = new PhaCheDatTruocChiTietFragment();

                ft_rep.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                bundle.putSerializable("pdt", phieuDatTruoc);

                phaCheDatTruocChiTietFragment.setArguments(bundle);
                ft_rep.replace(R.id.frame_layout_holder_phache, phaCheDatTruocChiTietFragment);
                ft_rep.addToBackStack(null);
                ft_rep.commit();
            }
        });

        return view;
    }

    private void getAllPhieuDatTruoc(){
        myRef.child("PhieuDatTruoc").orderByChild("thoiGianNhanBan").startAt(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))
                .endAt(new SimpleDateFormat("dd/MM/yyyy 23:59").format(new Date())).addValueEventListener(new ValueEventListener() {

            PhieuDatTruoc tmp = new PhieuDatTruoc();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phieuDatTruocArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ArrayList<ChiTietPhieuDatTruoc> lst = new ArrayList<ChiTietPhieuDatTruoc>();
                    for(DataSnapshot childOfChild : child.child("chiTietPhieuDatTruoc").getChildren()){
                        if (childOfChild.child("loai").getValue(String.class).equals("Pha chế")) {
                            PhieuDatTruoc phieuDatTruoc = child.getValue(PhieuDatTruoc.class);
                            ChiTietPhieuDatTruoc chiTietPhieuDatTruoc = childOfChild.getValue(ChiTietPhieuDatTruoc.class);
                            lst.add(chiTietPhieuDatTruoc);
                            phieuDatTruoc.setChiTietPhieuDatTruocArrayList(lst);
                            if (!phieuDatTruocArrayList.contains(phieuDatTruoc)) {
                                phieuDatTruocArrayList.add(phieuDatTruoc);
                            }
                        }
                    }
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
