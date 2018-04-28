package development.mobile.quanlygoimon.code.phache.dattruoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.mobile.quanlygoimon.code.R;

public class PhaCheDatTruocFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phache_dattruoc, container, false);
        FragmentManager fm = getFragmentManager();

        FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.frame_layout_holder_phache, new PhaCheDatTruocDanhSachFragment());
        ft_add.commit();

        return view;
    }
}
