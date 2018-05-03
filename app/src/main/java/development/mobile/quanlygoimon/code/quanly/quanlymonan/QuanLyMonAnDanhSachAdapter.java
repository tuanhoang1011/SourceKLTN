package development.mobile.quanlygoimon.code.quanly.quanlymonan;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;

public class QuanLyMonAnDanhSachAdapter extends ArrayAdapter<MonAn> {

    private Activity activity = null;
    private List<MonAn> myArray = null;
    private int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public QuanLyMonAnDanhSachAdapter(Activity activity, int resource, @NonNull List<MonAn> objects) {
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
            final MonAn ma = myArray.get(position);

            final ImageView imgMonAn = (ImageView) convertView.findViewById(R.id.img_anhmonan_item_quanly_qlma);
            imgMonAn.setImageResource(R.drawable.icon_nhomhang);
            final TextView tvMaMA = (TextView) convertView.findViewById(R.id.tv_mama_item_quanly_qlma);
            tvMaMA.setText("Mã: " + ma.getMaMonAn());
            final TextView tvTenMA = (TextView) convertView.findViewById(R.id.tv_tenma_item_quanly_qlma);
            tvTenMA.setText("Tên: "+ ma.getTenMonAn().toString());
            final TextView tvLoai = (TextView) convertView.findViewById(R.id.tv_loai_item_quanly_qlma);
            tvLoai.setText("Loại: " + ma.getLoai().toString());
            final TextView tvGia = (TextView) convertView.findViewById(R.id.tv_gia_item_quanly_qlma);
            tvGia.setText("Giá: " + ma.getGia() + "");

            final ImageButton ibtnXoa = (ImageButton) convertView.findViewById(R.id.ibtn_xoama_item_quanly_qlma);

            ibtnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Bạn có muốn xóa món ăn " + ma.getMaMonAn() + "?");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ma.isTrangThai()) {
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("Thông báo");
                                alertDialog.setMessage("Món ăn có trạng thái Có phục vụ. Không thể xóa");
                                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                myRef.child("NhomHang").child(ma.getPushKeyNhomHang()).child("danhSachMonAn").child(ma.getPushKey()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(getContext(), "Đã xóa món ăn " + ma.getTenMonAn(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            ibtnXoa.setFocusable(false);
            ibtnXoa.setFocusableInTouchMode(false);
        }

        return convertView;
    }
}