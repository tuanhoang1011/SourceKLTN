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
    private ListViewMonDangGoiAdapter adapter_DSMonDGFrag = null;
    private ListView monDGLsrView_monDGFrag;

    public DSMonDangGoiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsmondanggoi, container, false);

        monDGLsrView_monDGFrag = (ListView) view.findViewById(R.id.monDGLsrView_monDGFrag);
        monAnLst_DSMonDGFrag = new ArrayList<MonAn>();
        adapter_DSMonDGFrag = new ListViewMonDangGoiAdapter(getActivity(), R.layout.item_mondanggoi, monAnLst_DSMonDGFrag);
        monDGLsrView_monDGFrag.setAdapter(adapter_DSMonDGFrag);

        monDGLsrView_monDGFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                View view1 = monDGLsrView_monDGFrag.getChildAt(position);
                TextView tempGhiChu = (TextView) view1.findViewById(R.id.ghiChuTxtView_item);
                TextView tempTenMon = (TextView) view1.findViewById(R.id.tenMonTxtView_item);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_changeinfomonan);

                final TextView ghiChuTitle_dialogChange = (TextView) dialog.findViewById(R.id.ghiChuTitle_dialogChange);
                final EditText ghiChuEditTxt_dialogChange = (EditText) dialog.findViewById(R.id.ghiChuEditTxt_dialogChange);
                Button huyBtn_dialogChange = (Button) dialog.findViewById(R.id.huyBtn_dialogChange);
                Button okBtn_dialogChange = (Button) dialog.findViewById(R.id.okBtn_dialogChange);

                ghiChuTitle_dialogChange.setText("Ghi chú " + tempTenMon.getText());
                ghiChuEditTxt_dialogChange.setText(tempGhiChu.getText());
                ghiChuEditTxt_dialogChange.requestFocus();

                huyBtn_dialogChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                okBtn_dialogChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monAnLst_DSMonDGFrag.get(position).setGhiChu(ghiChuEditTxt_dialogChange.getText().toString());
                        adapter_DSMonDGFrag.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(900, 450);
            }
        });

        return view;
    }

    public void getMonAnFromDSMonAnFrag(MonAn monAn) {
        if (monAnLst_DSMonDGFrag.contains(monAn)) {
            int index = monAnLst_DSMonDGFrag.indexOf(monAn);
            MonAn monAn1 = monAnLst_DSMonDGFrag.get(index);
            monAn1.setSoLuong(monAn1.getSoLuong() + 1);
            System.out.println(monAn1.getGia() + "---" +  monAn.getGia());
            monAn1.setGia(monAn1.getGia() + 70000);
        } else {
            monAn.setSoLuong(1);
            monAnLst_DSMonDGFrag.add(monAn);
        }
        adapter_DSMonDGFrag.notifyDataSetChanged();
    }
}
