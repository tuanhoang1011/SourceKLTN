package development.mobile.quanlygoimon.code.quanly.quanlynhomhang;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhomHang;

public class QuanLyNhomHangDanhSachAdapter extends ArrayAdapter<NhomHang> {

    private Activity activity = null;
    private List<NhomHang> myArray = null;
    private int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public QuanLyNhomHangDanhSachAdapter(Activity activity, int resource, @NonNull List<NhomHang> objects) {
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
            final NhomHang nh = myArray.get(position);
            final TextView tvMaKV = (TextView) convertView.findViewById(R.id.tv_manh_item_quanly_qlnh);
            tvMaKV.setText(nh.getMaNhomHang() + "");
            final TextView tvTenKV = (TextView) convertView.findViewById(R.id.tv_tennh_item_quanly_qlnh);
            tvTenKV.setText(nh.getTenNhomHang().toString());

            final ImageButton ibtnXoa = (ImageButton) convertView.findViewById(R.id.ibtn_xoanh_item_quanly_qlnh);

            ibtnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Bạn có muốn xóa nhóm hàng " + nh.getMaNhomHang() + "?");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child("NhomHang").child(nh.getPushKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    boolean check = true;

                                    for (DataSnapshot child : dataSnapshot.child("danhSachMonAn").getChildren()) {
                                        if (child.child("trangThai").getValue(Boolean.class) == true) {
                                            check = false;
                                            break;
                                        }
                                    }

                                    if (check) {
                                        myRef.child("NhomHang").child(nh.getPushKey()).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                Toast.makeText(getContext(), "Đã xóa nhóm hàng " + nh.getMaNhomHang(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                        alertDialog.setTitle("Thông báo");
                                        alertDialog.setMessage("Nhóm hàng có món ăn đang sửa dụng. Không thể xóa");
                                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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
