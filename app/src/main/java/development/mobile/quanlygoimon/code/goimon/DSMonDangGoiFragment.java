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
    private List<MonAn> monAnLst_DSMonDGFrag;
    private List<MonAn> monAnLst_DSMonDGFraga = new ArrayList<>();
    private ListViewMonDangGoiAdapter adapter_DSMonDGFrag = null;
    private ListView monDGLsrView_monDGFrag;

    public DSMonDangGoiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsmondanggoi, container, false);

        monDGLsrView_monDGFrag = (ListView) view.findViewById(R.id.monDGLsrView_monDGFrag);
        monAnLst_DSMonDGFrag = new ArrayList<>();
        adapter_DSMonDGFrag = new ListViewMonDangGoiAdapter(getActivity(), R.layout.item_mondanggoi, monAnLst_DSMonDGFrag);
        monDGLsrView_monDGFrag.setAdapter(adapter_DSMonDGFrag);

        return view;
    }

    public void getMonAnFromDSMonAnFrag(MonAn monAn) {
        if (monAnLst_DSMonDGFrag.contains(monAn)) {
            int index = monAnLst_DSMonDGFrag.indexOf(monAn);
            System.out.println(monAnLst_DSMonDGFrag);
            System.out.println(monAnLst_DSMonDGFraga);
            MonAn monAn1 = monAnLst_DSMonDGFrag.get(index);
            monAn1.setSoLuong(monAn1.getSoLuong() + 1);
            monAn1.setGia(monAn1.getGia() + monAn.getGia());
        } else {
            monAn.setSoLuong(1);
            monAnLst_DSMonDGFrag.add(monAn);
        }
        adapter_DSMonDGFrag.notifyDataSetChanged();
    }
}
