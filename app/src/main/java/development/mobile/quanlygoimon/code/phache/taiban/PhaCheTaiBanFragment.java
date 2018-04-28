package development.mobile.quanlygoimon.code.phache.taiban;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.mobile.quanlygoimon.code.R;

public class PhaCheTaiBanFragment extends Fragment {

    public PhaCheTaiBanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phache_taiban, container, false);

        return view;
    }
}