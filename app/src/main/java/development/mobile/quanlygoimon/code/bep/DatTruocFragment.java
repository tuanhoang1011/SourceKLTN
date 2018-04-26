package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    List<PhieuDatTruoc> phieuDatTruocArrayList = null;
    ArrayAdapter<PhieuDatTruoc> phieuDatTruocArrayAdapter = null;
    ListView lvPhieuDatTruoc;

    public DatTruocFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_dattruoc, container, false);

        lvPhieuDatTruoc  = (ListView) view.findViewById(R.id.lv_ctdattruoc_dattruoc);

        phieuDatTruocArrayList = new ArrayList<PhieuDatTruoc>();
        phieuDatTruocArrayAdapter = new DatTruocAdapter(getActivity(), R.layout.item_list_bep_dattruoc, phieuDatTruocArrayList);
        lvPhieuDatTruoc.setAdapter(phieuDatTruocArrayAdapter);
        getAllPhieuDatTruoc();

        return view;
    }

    private void getAllPhieuDatTruoc(){
        myRef.child("PhieuDatTruoc").addValueEventListener(new ValueEventListener() {
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
