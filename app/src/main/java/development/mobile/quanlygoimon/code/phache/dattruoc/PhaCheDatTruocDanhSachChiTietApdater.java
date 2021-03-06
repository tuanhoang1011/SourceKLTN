package development.mobile.quanlygoimon.code.phache.dattruoc;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietPhieuDatTruoc;

public class PhaCheDatTruocDanhSachChiTietApdater extends ArrayAdapter<ChiTietPhieuDatTruoc> {

    private Activity activity = null;
    private List<ChiTietPhieuDatTruoc> myArray = null;
    private int layoutId;

    public PhaCheDatTruocDanhSachChiTietApdater(Activity activity, int resource, @NonNull List<ChiTietPhieuDatTruoc> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.myArray = objects;
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if (myArray.size() > 0 && position >= 0) {
            final ChiTietPhieuDatTruoc chiTietPhieuDatTruoc = myArray.get(position);
            final TextView tvTenMonAn = (TextView)convertView.findViewById(R.id.tv_tenmonan_item_phache_danhsach_chitiet);
            tvTenMonAn.setText(chiTietPhieuDatTruoc.getTenMonAn().toString());
            final TextView tvSoLuong = (TextView)convertView.findViewById(R.id.tv_soluong_item_phache_danhsach_chitiet);
            tvSoLuong.setText(chiTietPhieuDatTruoc.getSoLuong() + "");
            final TextView tvGhiChu = (TextView)convertView.findViewById(R.id.tv_ghichu_item_phache_danhsach_chitiet);
            tvGhiChu.setText(chiTietPhieuDatTruoc.getGhiChu().toString());
        }

        return convertView;
    }
}
