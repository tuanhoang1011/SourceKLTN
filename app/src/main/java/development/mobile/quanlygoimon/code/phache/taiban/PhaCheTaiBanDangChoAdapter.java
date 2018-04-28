package development.mobile.quanlygoimon.code.phache.taiban;

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

public class PhaCheTaiBanDangChoAdapter extends ArrayAdapter<ChiTietHoaDon> {

    private Activity activity = null;
    private List<ChiTietHoaDon> myArray = null;
    private int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public PhaCheTaiBanDangChoAdapter(Activity activity, int resource, @NonNull List<ChiTietHoaDon> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.myArray = objects;
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if (myArray.size() > 0 && position >= 0) {
            final ChiTietHoaDon cthd = myArray.get(position);
            final TextView tvIdHD = (TextView)convertView.findViewById(R.id.tv_idhd_item_phache_taiban_dangcho);
            tvIdHD.setText(cthd.getMaHoaDon().toString());
            final TextView tvTenMonAn = (TextView)convertView.findViewById(R.id.tv_tenmonan_item_phache_taiban_dangcho);
            tvTenMonAn.setText(cthd.getTenMonAn().toString());
            final TextView tvSoLuong = (TextView)convertView.findViewById(R.id.tv_soluong_item_phache_taiban_dangcho);
            tvSoLuong.setText(cthd.getSoLuong() + "");
            final TextView tvGhiChu = (TextView)convertView.findViewById(R.id.tv_ghichu_item_phache_taiban_dangcho);
            tvGhiChu.setText(cthd.getGhiChu());
            final ImageButton ibtnCheBien = (ImageButton) convertView.findViewById(R.id.ibtn_chebien_item_phache_taiban_dangcho);
            final ImageButton ibtnTamNgungPhucVu = (ImageButton)convertView.findViewById(R.id.ibtn_tamngungphucvu_item_phache_taiban_dangcho);

            ibtnCheBien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child("HoaDon").child(cthd.getPushkeyHD()).child("chiTietHoaDon").child(cthd.getPushKeyCTHD())
                            .child("trangThai").setValue("Đang chế biến");

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