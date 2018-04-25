package development.mobile.quanlygoimon.code.bep;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;

public class BepTaiBanDangChoAdapter extends ArrayAdapter<ChiTietHoaDon> {

    Activity activity = null;
    ArrayList<ChiTietHoaDon> myArray = null;
    int layoutId;

    public BepTaiBanDangChoAdapter(Activity activity, int resource, @NonNull ArrayList<ChiTietHoaDon> objects) {
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
            ChiTietHoaDon cthd = myArray.get(position);
            final TextView tvIdHD = (TextView)convertView.findViewById(R.id.tv_idhd_item_taiban_dangcho);
            tvIdHD.setText(cthd.getMaHoaDon().toString());
            final TextView tvTenMonAn = (TextView)convertView.findViewById(R.id.tv_tenmonan_item_taiban_dangcho);
            tvTenMonAn.setText(cthd.getTenMonAn().toString());
            final TextView tvSoLuong = (TextView)convertView.findViewById(R.id.tv_soluong_item_taiban_dangcho);
            tvTenMonAn.setText(cthd.getSoLuong());
        }
        return convertView;
    }
}
