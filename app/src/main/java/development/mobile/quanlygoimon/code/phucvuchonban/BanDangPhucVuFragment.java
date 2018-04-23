package development.mobile.quanlygoimon.code.phucvuchonban;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.mobile.quanlygoimon.code.R;

public class BanDangPhucVuFragment extends Fragment {

    public BanDangPhucVuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bandangphucvu, container, false);
    }
}
