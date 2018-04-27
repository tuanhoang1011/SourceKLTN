package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocChiTietFragment extends Fragment{

    private PhieuDatTruoc pdt = new PhieuDatTruoc();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_chitiet_dattruoc, container, false);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String link = bundle.getString("madt");
//            Toast.makeText(getActivity(), link, Toast.LENGTH_SHORT).show();
//        }
//        if (savedInstanceState != null) {
//            Serializable phieuDatTruoc = savedInstanceState.getSerializable("madt");
//            Toast.makeText(getActivity(), pdt.toString(), Toast.LENGTH_SHORT).show();
//        }
        Toast.makeText(getActivity(), pdt.toString(), Toast.LENGTH_SHORT).show();
        return view;
    }

    public void getPhieuDatTruocFromDatTruocDSFrag(PhieuDatTruoc phieuDatTruoc) {
        pdt = phieuDatTruoc;
    }
}
