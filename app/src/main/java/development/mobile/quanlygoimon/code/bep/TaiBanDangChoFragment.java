package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.mobile.quanlygoimon.code.R;

public class TaiBanDangChoFragment extends Fragment {

    public TaiBanDangChoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bep_taiban_dangcho, container, false);
    }
}