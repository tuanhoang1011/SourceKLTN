package development.mobile.quanlygoimon.code.bep;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_dattruoc, container, false);
        FragmentManager fm = getFragmentManager();

        android.support.v4.app.FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.frame_layout_holder, new DatTruocDanhSachFragment());
        ft_add.commit();


////      replace
//        FragmentTransaction ft_rep = fm.beginTransaction();
//        ft_rep.replace(R.id.your_placehodler, new YourFragment());
//        ft_rep.commit();
//
////      remove
//        Fragment fragment = fm.findFragmentById(R.id.your_placehodler);
//        FragmentTransaction ft_remo = fm.beginTransaction();
//        ft_remo.remove(fragment);
//        ft_remo.commit();

        return view;
    }
}
