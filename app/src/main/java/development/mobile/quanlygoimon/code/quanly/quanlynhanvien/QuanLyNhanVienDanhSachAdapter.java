package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

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
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienDanhSachAdapter extends ArrayAdapter<NhanVien> {

    private Activity activity = null;
    private List<NhanVien> myArray = null;
    private int layoutId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public QuanLyNhanVienDanhSachAdapter(Activity activity, int resource, @NonNull List<NhanVien> objects) {
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
            final NhanVien nv = myArray.get(position);
            final TextView tvMaNV = (TextView) convertView.findViewById(R.id.tv_manv_item_quanly_qlnv);
            tvMaNV.setText(nv.getMaNhanVien() + "");
            final TextView tvTenNV = (TextView) convertView.findViewById(R.id.tv_tennv_item_quanly_qlnv);
            tvTenNV.setText(nv.getTenNhanVien().toString());
            final TextView tvChucVu = (TextView) convertView.findViewById(R.id.tv_chucvu_item_quanly_qlnv);

            switch (nv.getChucVu().toString()) {
                case "PhucVu":
                    tvChucVu.setText("Phục vụ");
                    break;
                case "Bep":
                    tvChucVu.setText("Bếp");
                    break;
                case "PhaChe":
                    tvChucVu.setText("Pha chế");
                    break;
                case "ThuNgan":
                    tvChucVu.setText("Thu ngân");
                    break;
                case "QuanLy":
                    tvChucVu.setText("Quản lý");
                    break;
            }

            final ImageView imgIconIsLogin = (ImageView) convertView.findViewById(R.id.img_iconisdangnhap_item_quanly_qlnv);
            if (nv.isDangNhap()) {
                imgIconIsLogin.setImageResource(android.R.drawable.presence_online);
            }
            final ImageButton ibtnXoa = (ImageButton) convertView.findViewById(R.id.ibtn_xoanv_item_quanly_qlnv);

            ibtnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Bạn có muốn xóa nhân viên " + nv.getMaNhanVien() + "?");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (nv.isDangNhap()) {
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("Thông báo");
                                alertDialog.setMessage("Nhân viên đang online. Không thể xóa");
                                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                myRef.child("NhanVien").child(nv.getPushkeyNV()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(getContext(), "Đã xóa nhân viên " + nv.getMaNhanVien(), Toast.LENGTH_LONG).show();
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