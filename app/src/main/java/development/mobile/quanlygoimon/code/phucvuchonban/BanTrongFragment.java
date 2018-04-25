package development.mobile.quanlygoimon.code.phucvuchonban;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.Ban;
import development.mobile.quanlygoimon.code.goimon.GoiMonActivity;


public class BanTrongFragment extends Fragment {
    private List<Ban> banLst = null;
    private GridViewBanAdapter gvBanAdapter = null;
    private GridView banGrid;
    private Button goiMonBtn;
    private Spinner khuVucSpinner;
    private ArrayList<String> khuVucLst = null;
    private ArrayAdapter<String> adapterSpinner = null;
    private ArrayList<String> banSelected = new ArrayList<String>();
    private ArrayList<String> pushKeyBanSelected = new ArrayList<String>();
    private HashMap<Integer, List<Integer>> temptSelectedMap = new HashMap<>();
    private int tempPosition = 0;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public BanTrongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bantrong, container, false);

        goiMonBtn = (Button) view.findViewById(R.id.goiMonBtn);
        banGrid = (GridView) view.findViewById(R.id.banGrid);
        khuVucSpinner = (Spinner) view.findViewById(R.id.khuVucSpinner);

        banLst = new ArrayList<Ban>();
        gvBanAdapter = new GridViewBanAdapter(getActivity(), R.layout.item_ban, banLst);
        banGrid.setAdapter(gvBanAdapter);

        khuVucLst = new ArrayList<String>();
        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, khuVucLst);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        khuVucSpinner.setAdapter(adapterSpinner);

        getAllKhuVuc();

        khuVucSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Integer> temptBanSelectedLst = gvBanAdapter.selectedPositions;
                temptSelectedMap.put(tempPosition, temptBanSelectedLst);
                tempPosition = position;
                banLst.clear();
                if (temptSelectedMap.get(position) != null) {
                    gvBanAdapter.selectedPositions = temptSelectedMap.get(position);
                } else {
                    gvBanAdapter.selectedPositions = new ArrayList<Integer>();
                }
                getListBan(khuVucLst.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        banGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (banLst.get(position).getTrangThai().equals("Trống")) {
                    int selectedIndex = gvBanAdapter.selectedPositions.indexOf(position);
                    if (selectedIndex > -1) {
                        gvBanAdapter.selectedPositions.remove(selectedIndex);
                        Ban ban = banLst.get(position);
                        banSelected.remove(ban.getMaBan());
                        pushKeyBanSelected.remove(ban.getPushKey());
                    } else {
                        gvBanAdapter.selectedPositions.add(position);
                        Ban ban = banLst.get(position);
                        banSelected.add(ban.getMaBan());
                        pushKeyBanSelected.add(ban.getPushKey());
                    }
                    if (banSelected.size() > 0) {
                        goiMonBtn.setEnabled(true);
                    } else {
                        goiMonBtn.setEnabled(false);
                    }
                    gvBanAdapter.notifyDataSetChanged();
                }
            }
        });

        goiMonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoiMonActivity.class);
                intent.putExtra("DSBan", banSelected);
                intent.putExtra("DSPushKeyBan", pushKeyBanSelected);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getAllKhuVuc() {
        myRef.child("KhuVuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String tenKhuVuc = "";
                    tenKhuVuc = child.child("tenKhuVuc").getValue(String.class);
                    khuVucLst.add(tenKhuVuc);
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListBan(String tenKhuVuc) {
        myRef.child("KhuVuc").orderByChild("tenKhuVuc").equalTo(tenKhuVuc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot childOfChild : child.child("danhSachBan").getChildren()) {
                        Ban ban = new Ban();
                        ban.setMaBan(childOfChild.child("maBan").getValue(String.class));
                        ban.setTrangThai(childOfChild.child("trangThai").getValue(String.class));
                        ban.setPushKey(childOfChild.getKey());
                        banLst.add(ban);
                    }
                }
                gvBanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
