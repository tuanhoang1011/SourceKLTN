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

public class BepTaiBanDangChoAdapter extends ArrayAdapter<ChiTietHoaDon> {

    private Activity activity = null;
    private List<ChiTietHoaDon> myArray = null;
    private int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public BepTaiBanDangChoAdapter(Activity activity, int resource, @NonNull List<ChiTietHoaDon> objects) {
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
            final TextView tvIdHDItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_idhd_item_taiban_dangcho);
            tvIdHDItemTaibanDangCho.setText(cthd.getMaHoaDon().toString());
            final TextView tvTenMonAnItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_tenmonan_item_taiban_dangcho);
            tvTenMonAnItemTaibanDangCho.setText(cthd.getTenMonAn().toString());
            final TextView tvSoLuongItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_soluong_item_taiban_dangcho);
            tvSoLuongItemTaibanDangCho.setText(cthd.getSoLuong() + "");
            final TextView tvGhiChuItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_ghichu_item_taiban_dangcho);
            tvGhiChuItemTaibanDangCho.setText(cthd.getGhiChu());
            final ImageButton ibtnCheBienItemTaibanDangCho = (ImageButton) convertView.findViewById(R.id.ibtn_chebien_item_taiban_dangcho);
            final ImageButton ibtnTamNgungPhucVuItemTaibanDangCho = (ImageButton)convertView.findViewById(R.id.ibtn_tamngungphucvu_item_taiban_dangcho);

            ibtnCheBienItemTaibanDangCho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child("HoaDon").child(cthd.getPushkeyHD()).child("chiTietHoaDon").child(cthd.getPushKeyCTHD())
                            .child("trangThai").setValue("Đang chế biến");

                }
            });
            ibtnTamNgungPhucVuItemTaibanDangCho.setOnClickListener(new View.OnClickListener() {
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
