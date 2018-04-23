package development.mobile.quanlygoimon.code.goimon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class DSNhomHangFragment extends Fragment {
    private List<String> nhomHangLst;
    private ListViewNhomHangAdapter adapter = null;
    private ListView nhomHangLstView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private SendTenNhomHang send;

    public DSNhomHangFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsnhomhang, container, false);

        send = (SendTenNhomHang) getActivity();
        nhomHangLstView = (ListView) view.findViewById(R.id.nhomHangLstView);
        nhomHangLst = new ArrayList<String>();
        adapter = new ListViewNhomHangAdapter(getActivity(), R.layout.item_nhomhang, nhomHangLst);
        nhomHangLstView.setAdapter(adapter);
        getAllNhomHang();

        nhomHangLstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.positionSelected != position){
                    send.senTenNhomHang(nhomHangLst.get(position));
                    adapter.positionSelected = position;
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    private void getAllNhomHang(){
        myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String tenNhomHang = "";
                    tenNhomHang = child.child("tenNhomHang").getValue(String.class);
                    nhomHangLst.add(tenNhomHang);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface SendTenNhomHang {
        void senTenNhomHang(String tenNhomHang);
    }
}
