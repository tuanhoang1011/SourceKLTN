package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.mobile.quanlygoimon.code.R;

public class TaiBanFragment  extends Fragment {

    public TaiBanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_taiban, container, false);

        return view;
    }
}