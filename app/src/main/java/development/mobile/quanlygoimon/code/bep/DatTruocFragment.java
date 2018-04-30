package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import development.mobile.quanlygoimon.code.R;

public class DatTruocFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_dattruoc, container, false);
        FragmentManager fm = getFragmentManager();

        android.support.v4.app.FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.frame_layout_holder, new DatTruocDanhSachFragment());
        ft_add.commit();

        return view;
    }
}
