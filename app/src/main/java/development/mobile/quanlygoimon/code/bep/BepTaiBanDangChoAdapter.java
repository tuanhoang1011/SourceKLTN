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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChiTietHoaDon;

public class BepTaiBanDangChoAdapter extends ArrayAdapter<ChiTietHoaDon> {

    Activity activity = null;
    ArrayList<ChiTietHoaDon> myArray = null;
    int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

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
            final ChiTietHoaDon cthd = myArray.get(position);
            final TextView tvIdHDItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_idhd_item_taiban_dangcho);
            tvIdHDItemTaibanDangCho.setText(cthd.getThoiGianGoi().toString());
            final TextView tvTenMonAnItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_tenmonan_item_taiban_dangcho);
            tvTenMonAnItemTaibanDangCho.setText(cthd.getTenMonAn().toString());
            final TextView tvSoLuongItemTaibanDangCho = (TextView)convertView.findViewById(R.id.tv_soluong_item_taiban_dangcho);
            tvSoLuongItemTaibanDangCho.setText(cthd.getSoLuong());
            final ImageButton ibtnCheBienItemTaibanDangCho = (ImageButton) convertView.findViewById(R.id.ibtn_chebien_item_taiban_dangcho);
            final ImageButton ibtnTamNgungPhucVuItemTaibanDangCho = (ImageButton)convertView.findViewById(R.id.ibtn_tamngungphucvu_item_taiban_dangcho);

            ibtnCheBienItemTaibanDangCho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cthd.setTrangThai("");
                }
            });
        }
        return convertView;
    }
}
