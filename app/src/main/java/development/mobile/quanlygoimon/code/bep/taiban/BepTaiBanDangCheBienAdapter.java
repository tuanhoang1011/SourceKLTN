package development.mobile.quanlygoimon.code.bep.taiban;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;

public class BepTaiBanDangCheBienAdapter extends ArrayAdapter<ChiTietHoaDon> {

    Activity activity = null;
    List<ChiTietHoaDon> myArray = null;
    int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public BepTaiBanDangCheBienAdapter(Activity activity, int resource, @NonNull List<ChiTietHoaDon> objects) {
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
            final ChiTietHoaDon cthd = myArray.get(position);
            final TextView tvIdHDItemTaibanDangCheBien = (TextView) convertView.findViewById(R.id.tv_idhd_item_taiban_dangchebien);
            tvIdHDItemTaibanDangCheBien.setText(cthd.getMaHoaDon().toString());
            final TextView tvTenMonAnItemTaibanDangCheBien = (TextView) convertView.findViewById(R.id.tv_tenmonan_item_taiban_dangchebien);
            tvTenMonAnItemTaibanDangCheBien.setText(cthd.getTenMonAn().toString());
            final TextView tvSoLuongItemTaibanDangCheBien = (TextView) convertView.findViewById(R.id.tv_soluong_item_taiban_dangchebien);
            tvSoLuongItemTaibanDangCheBien.setText(cthd.getSoLuong() + "");
            final TextView tvGhiChuItemTaibanDangCheBien = (TextView) convertView.findViewById(R.id.tv_ghichu_item_taiban_dangchebien);
            tvGhiChuItemTaibanDangCheBien.setText(cthd.getGhiChu());
            final ImageButton ibtnHoanThanhMonAn = (ImageButton) convertView.findViewById(R.id.ibtn_hoanthanhmonan_item_taiban_dangchebien);
            final ImageButton ibtnTamNgungPhucVu = (ImageButton) convertView.findViewById(R.id.ibtn_tamngungphucvu_item_taiban_dangchebien);

            ibtnHoanThanhMonAn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child("HoaDon").child(cthd.getPushkeyHD()).child("chiTietHoaDon").child(cthd.getPushKeyCTHD())
                            .child("trangThai").setValue("Chế biến xong");
                }
            });

            ibtnTamNgungPhucVu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child("HoaDon").child(cthd.getPushkeyHD()).child("chiTietHoaDon").child(cthd.getPushKeyCTHD())
                            .child("trangThai").setValue("Tạm ngưng");
                }
            });
        }

        return convertView;
    }
}