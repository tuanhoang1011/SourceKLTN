package development.mobile.quanlygoimon.code.goimon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class DSMonAnFragment extends Fragment {
    private List<MonAn> monAnLst_DSMonAnFrag;
    private GridViewMonAnAdapter adapter_DSMonAnFrag = null;
    private GridView monAnGridView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private SendMonAn send;

    public DSMonAnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsmonan, container, false);

        send = (SendMonAn) getActivity();
        monAnGridView = (GridView) view.findViewById(R.id.monAnGridView);
        monAnLst_DSMonAnFrag = new ArrayList<MonAn>();
        adapter_DSMonAnFrag = new GridViewMonAnAdapter(getActivity(), R.layout.item_monan, monAnLst_DSMonAnFrag);
        monAnGridView.setAdapter(adapter_DSMonAnFrag);
        getAllMonAn("Món ăn");

        monAnGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(monAnLst_DSMonAnFrag.get(position));

                send.sendMonAn(monAnLst_DSMonAnFrag.get(position));
            }
        });

        return view;
    }

    private void getAllMonAn(String tenNhomHang){
        myRef.child("NhomHang").orderByChild("tenNhomHang").equalTo(tenNhomHang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                monAnLst_DSMonAnFrag.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for(DataSnapshot childOfChild : child.child("danhSachMonAn").getChildren()){
                        MonAn monAn = childOfChild.getValue(MonAn.class);
                        monAnLst_DSMonAnFrag.add(monAn);
                    }
                }
                adapter_DSMonAnFrag.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTenNhomHangFromDSNhomHangFrag(String tenNhomHang){
        getAllMonAn(tenNhomHang);
    }

    interface SendMonAn{
        void sendMonAn(MonAn monAn);
    }
}
