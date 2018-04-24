package development.mobile.quanlygoimon.code.goimon;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;

public class DSMonDangGoiFragment extends Fragment {
    public List<MonAn> monAnLst_DSMonDGFrag;
    private ListViewMonDangGoiAdapter adapter_DSMonDGFrag = null;
    private ListView monDGLsrView_monDGFrag;

    public DSMonDangGoiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsmondanggoi, container, false);

        monDGLsrView_monDGFrag = (ListView) view.findViewById(R.id.monDGLsrView_monDGFrag);
        monAnLst_DSMonDGFrag = new ArrayList<>();
        adapter_DSMonDGFrag = new ListViewMonDangGoiAdapter((GoiMonActivity)getActivity(), R.layout.item_mondanggoi, monAnLst_DSMonDGFrag);
        monDGLsrView_monDGFrag.setAdapter(adapter_DSMonDGFrag);

        return view;
    }

    public void getMonAnFromDSMonAnFrag(MonAn monAn) {
        if (monAnLst_DSMonDGFrag.contains(monAn)) {
            int index = monAnLst_DSMonDGFrag.indexOf(monAn);
            MonAn monAn1 = monAnLst_DSMonDGFrag.get(index);
            monAn1.setSoLuong(monAn1.getSoLuong() + 1);
            monAn1.setGia(monAn1.getGia() + monAn.getGia());
        } else {
            MonAn monAn2 = new MonAn();
            monAn2.setMaMonAn(monAn.getMaMonAn());
            monAn2.setTenMonAn(monAn.getTenMonAn());
            monAn2.setGia(monAn.getGia());
            monAn2.setSoLuong(1);
            monAn2.setGhiChu("");
            monAn2.setLoai(monAn.getLoai());
            monAnLst_DSMonDGFrag.add(monAn2);
        }
        adapter_DSMonDGFrag.notifyDataSetChanged();
    }
}
