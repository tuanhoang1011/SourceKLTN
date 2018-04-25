package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;

public class TaiBanDangChoFragment extends Fragment implements View.OnClickListener{

    private TextView tvIdHD, tvTenMonAn, tvSoLuong;
    private Button btnCheBien, btnTamNgungPhucVu;
    private ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList = null;
    private BepTaiBanDangChoAdapter bepTaiBanDangChoAdapter = null;
    ListView lvCTHD;

    public TaiBanDangChoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_taiban_dangcho, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvIdHD = (TextView) view.findViewById(R.id.tv_idhd_item_taiban_dangcho);
        tvTenMonAn = (TextView) view.findViewById(R.id.tv_tenmonan_item_taiban_dangcho);
        tvSoLuong = (TextView) view.findViewById(R.id.tv_soluong_item_taiban_dangcho);
        btnCheBien = (Button) view.findViewById(R.id.btn_chebien_item_taiban_dangcho);
        btnTamNgungPhucVu = (Button) view.findViewById(R.id.btn_tamngungphucvu_item_taiban_dangcho);
        lvCTHD  = (ListView) view.findViewById(R.id.lv_cthd_taiban_dangcho);

        chiTietHoaDonArrayList = new ArrayList<ChiTietHoaDon>();
        bepTaiBanDangChoAdapter = new BepTaiBanDangChoAdapter(getActivity(), R.layout.item_list_fm_bep_taiban_dangcho, chiTietHoaDonArrayList);

        btnCheBien.setOnClickListener(this);
        btnTamNgungPhucVu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_chebien_item_taiban_dangcho) {
//            View v = lvCTHD.getSelectedView();
//            chiTietHoaDonArrayList.remove();
//            bepTaiBanDangChoAdapter.notifyDataSetChanged();
        } else if (id == R.id.btn_tamngungphucvu_item_taiban_dangcho) {

        }
    }
}