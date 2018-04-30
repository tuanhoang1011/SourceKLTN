package development.mobile.quanlygoimon.code.bep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocChiTietFragment extends Fragment {

    private TextView tvMaDatTruoc, tvThoiGianNhanBan, tvSoBan, tvSoNguoi;
    ListView lvCTPDT;
    DatTruocDanhSachChiTietApdater apdater;

    private PhieuDatTruoc pdt = new PhieuDatTruoc();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bep_chitiet_dattruoc, container, false);

        tvMaDatTruoc = (TextView) view.findViewById(R.id.tv_madattruoc_item_danhsach_chitiet);
        tvThoiGianNhanBan = (TextView) view.findViewById(R.id.tv_thoigiannhanban_item_danhsach_chitiet);
        tvSoBan = (TextView) view.findViewById(R.id.tv_soban_item_danhsach_chitiet);
        tvSoNguoi = (TextView) view.findViewById(R.id.tv_songuoi_item_danhsach_chitiet);
        lvCTPDT = (ListView) view.findViewById(R.id.lv_ctpdt_dattruoc_chitiet);

        Bundle bundle = getArguments();
        if (bundle != null) {
            pdt = (PhieuDatTruoc) bundle.getSerializable("pdt");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try {
                tvThoiGianNhanBan.setText("Thời gian nhận bàn: " + new SimpleDateFormat("HH:mm").format(formatter.parse(pdt.getThoiGianNhanBan())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvMaDatTruoc.setText("Mã đặt trước: " + pdt.getMaDatTruoc().toString());
            tvSoBan.setText("Số bàn: " + pdt.getSoLuongBan());
            tvSoNguoi.setText("Số người: " + pdt.getSoLuongKhach());

            apdater = new DatTruocDanhSachChiTietApdater(getActivity(), R.layout.item_list_fm_dattruoc_danhsach_chitiet, pdt.getChiTietPhieuDatTruocArrayList());
            lvCTPDT.setAdapter(apdater);
        }

        return view;
    }
}

