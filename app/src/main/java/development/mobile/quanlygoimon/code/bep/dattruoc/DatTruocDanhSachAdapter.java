package development.mobile.quanlygoimon.code.bep.dattruoc;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

public class DatTruocDanhSachAdapter extends ArrayAdapter<PhieuDatTruoc> {
    private Activity activity = null;
    private List<PhieuDatTruoc> myArray = null;
    private int layoutId;

    public DatTruocDanhSachAdapter(Activity activity, int resource, @NonNull List<PhieuDatTruoc> objects) {
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
            final PhieuDatTruoc phieuDatTruoc = myArray.get(position);
            final TextView tvMaDatTruoc= (TextView) convertView.findViewById(R.id.tv_madatruoc_item_dattruoc);
            tvMaDatTruoc.setText(phieuDatTruoc.getMaDatTruoc().toString());
            final TextView tvThoiGianNhanBan= (TextView) convertView.findViewById(R.id.tv_thoigiannhanban_item_dattruoc);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                tvThoiGianNhanBan.setText(new SimpleDateFormat("HH:mm").format(formatter.parse(phieuDatTruoc.getThoiGianNhanBan())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final TextView tvSoBan= (TextView) convertView.findViewById(R.id.tv_soban_item_dattruoc);
            tvSoBan.setText(phieuDatTruoc.getSoLuongBan() + "");
            final TextView tvSoNguoi = (TextView) convertView.findViewById(R.id.tv_songuoi_item_dattruoc);
            tvSoNguoi.setText(phieuDatTruoc.getSoLuongKhach() + "");
        }

        return convertView;
    }
}
