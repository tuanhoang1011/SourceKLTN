package development.mobile.quanlygoimon.code.quanly.quanlymonan;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;
import development.mobile.quanlygoimon.code.entity.NhomHang;
import development.mobile.quanlygoimon.code.quanly.quanlymonan.utility.FilePaths;

public class QuanLyMonAnChiTietFragment extends Fragment implements ChangePhotoDialog.OnPhotoReceivedListener{

    private Button btnLuu, btnSua;
    private EditText edtMaMA, edtTenMA, edtGia;
    private Spinner snLoai, snMaNhomHang;
    private CheckBox ckTrangThai;
    private ImageView imgMonAn;
    private TextView tvTitle;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private String pushkeyMAHienTai = "";
    private MonAn ma = new MonAn();
    private ArrayList<String> loaiLst;
    private ArrayList<String> tenNhomHangArrayList;
    private ArrayList<NhomHang> nhomHangArrayList;
    private ArrayAdapter<String> tenNhomHangArrayAdapter = null;
    private String pushkeynhomhang;

    //var
    private boolean mStoragePermissions;
    private Uri mSelectedImageUri;
    private Bitmap mSelectedImageBitmap;
    private byte[] mBytes;
    private double progress;
    private ProgressBar mProgressBar;



    private static final int REQUEST_CODE = 1234;
    private static final double MB_THRESHHOLD = 5.0;
    private static final double MB = 1000000.0;

    public QuanLyMonAnChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlma, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tv_title_chitiet_quanly_qlma);
        imgMonAn = (ImageView) view.findViewById(R.id.img_anhmonan_chitiet_quanly_qlma);
        edtMaMA = (EditText) view.findViewById(R.id.edt_mama_chitiet_quanly_qlma);
        edtTenMA = (EditText) view.findViewById(R.id.edt_tenma_chitiet_quanly_qlma);
        snLoai = (Spinner) view.findViewById(R.id.sn_loai_chitiet_quanly_qlma);
        edtGia = (EditText) view.findViewById(R.id.edt_gia_chitiet_quanly_qlma);
        ckTrangThai = (CheckBox) view.findViewById(R.id.ck_trangthai_chitiet_quanly_qlma);
        snMaNhomHang = (Spinner) view.findViewById(R.id.spn_manhomhang_chitiet_quanly_qlma);
        btnLuu = (Button) view.findViewById(R.id.btn_luu_chitiet_quanly_qlma);
        btnSua = (Button) view.findViewById(R.id.btn_sua_chitiet_quanly_qlma);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        loaiLst = new ArrayList<>();

        loaiLst.add("Bếp");
        loaiLst.add("Pha chế");
        loaiLst.add("Ăn liền");

        ArrayAdapter<String> loaiAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item_spinner_quanly, loaiLst);
        loaiAdapter.setDropDownViewResource(R.layout.item_spinner_quanly);
        snLoai.setAdapter(loaiAdapter);

        nhomHangArrayList = new ArrayList<NhomHang>();
        tenNhomHangArrayList = new ArrayList<String>();
        tenNhomHangArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item_spinner_quanly, tenNhomHangArrayList);
        tenNhomHangArrayAdapter.setDropDownViewResource(R.layout.item_spinner_quanly);
        snMaNhomHang.setAdapter(tenNhomHangArrayAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("ma") != null) {
                ma = (MonAn) bundle.getSerializable("ma");
            }
            else if (bundle.getString("btnThemMA") != null) {
                pushkeynhomhang = bundle.getString("pushkeynhomhang");
            }
        }

        getAllNhomHang();

        edtMaMA.setEnabled(false);
        setFocusEditText(false);
        alertErrorDataInput();

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSua.getText().toString().equals("Sửa")) {
                    setFocusEditText(true);
                    edtMaMA.setEnabled(false);
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Thêm")) {
                    setFocusEditText(true);
                    edtMaMA.setEnabled(false);
                    clearDataInputMA();
                    btnSua.setText("Hủy");
                } else if (btnSua.getText().toString().equals("Hủy")) {
                    if (tvTitle.getText().equals("Chi tiết món ăn")) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        loadMA(ma);
                    } else if (tvTitle.getText().equals("Thêm mới món ăn")) {
                        setFocusEditText(false);
                        btnSua.setText("Thêm");
                        btnSua.setEnabled(true);
                        clearDataInputMA();
                    }
                    clearAlertErrorDataInput();
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitle.getText().equals("Chi tiết món ăn")) {
                    boolean check = true;

                    String regexTenMA = "^[\\p{L}\\s]+$";
                    if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                        edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                        check = false;
                    }

                    String regexGia = "^\\d$";
                    if (!Pattern.matches(regexGia, edtGia.getText())) {
                        edtGia.setError("Vui lòng nhập đúng định dạng số");
                        check = false;
                    }

                    if (check) {
                        setFocusEditText(false);
                        btnSua.setEnabled(true);
                        btnSua.setText("Sửa");
                        MonAn updateMA = new MonAn(ma.getMaMonAn(), edtTenMA.getText().toString(), snLoai.getSelectedItem().toString(),
                                Double.parseDouble(edtGia.getText().toString()), ckTrangThai.isChecked(),
                                nhomHangArrayList.get(snMaNhomHang.getSelectedItemPosition()).getMaNhomHang(), "");
                        suaThongTinMonAn(updateMA);
                        loadMA(updateMA);
                    }
                } else if (tvTitle.getText().equals("Thêm mới món ăn")) {
                    myRef.child("NhomHang").child(pushkeynhomhang).child("danhSachMonAn").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean check = true;

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                if (child.child("tenMonAn").getValue(String.class).equalsIgnoreCase( edtTenMA.getText().toString())) {
                                    edtTenMA.setError("Tên món ăn trùng. Vui lòng nhập lại");
                                    check = false;
                                    break;
                                }
                            }

                            String regexTenMA = "^[\\p{L}\\s]+$";
                            if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                                edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                                check = false;
                            }

                            String regexGia = "^\\d+$";
                            if (!Pattern.matches(regexGia, edtGia.getText())) {
                                edtGia.setError("Vui lòng nhập đúng định dạng số");
                                check = false;
                            }

                            if (check) {
                                setFocusEditText(false);
                                btnSua.setEnabled(true);
                                btnSua.setText("Thêm");
                                MonAn newMA = new MonAn(edtTenMA.getText().toString(), snLoai.getSelectedItem().toString(),
                                        Double.parseDouble(edtGia.getText().toString()), ckTrangThai.isChecked(),
                                        nhomHangArrayList.get(snMaNhomHang.getSelectedItemPosition()).getMaNhomHang(), "");

                                themMonAn(newMA);
                                clearDataInputMA();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        imgMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStoragePermissions){
                    ChangePhotoDialog dialog = new ChangePhotoDialog();
                    dialog.show(getFragmentManager(), "Thay đổi ảnh món ăn");
                }else{
                    verifyStoragePermissions();
                }
            }
        });

        return view;
    }

    private void loadMA(MonAn ma) {
        edtMaMA.setText(ma.getMaMonAn() + "");
        edtTenMA.setText(ma.getTenMonAn().toString());

        for (int i = 0; i < loaiLst.size() - 1; i++) {
            if (ma.getLoai().equals(loaiLst.get(i))) {
                snLoai.setSelection(i);
                break;
            }
        }

        edtGia.setText(ma.getGia() + "");

        if (ma.isTrangThai())
            ckTrangThai.setChecked(true);
        else
            ckTrangThai.setChecked(false);

        pushkeyMAHienTai = ma.getPushKey();

        for (int i = 0; i < tenNhomHangArrayList.size(); i++) {
            if (nhomHangArrayList.get(i).getMaNhomHang() == ma.getMaNhomHang()) {
                snMaNhomHang.setSelection(i);
                break;
            }
        }
    }

    private void themMonAn(final MonAn newMA){
        myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int maxMaMA = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot childOfChild : child.child("danhSachMonAn").getChildren()) {
                        int id = childOfChild.child("maMonAn").getValue(Integer.class);
                        if (id > maxMaMA) {
                            maxMaMA = id;
                        }
                    }
                }

                newMA.setMaMonAn(maxMaMA + 1);

                myRef.child("NhomHang").child(pushkeynhomhang).child("danhSachMonAn").push().setValue(newMA).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(mSelectedImageUri != null){
                            uploadNewPhoto(mSelectedImageUri);
                        }else if(mSelectedImageBitmap  != null){
                            uploadNewPhoto(mSelectedImageBitmap);
                        }

                        Toast.makeText(getActivity(), "Đã thêm món ăn " + newMA.getTenMonAn(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void suaThongTinMonAn(MonAn updateMA){
//        updateMA.setMaMonAn(ma.getMaMonAn());
//        Map<String, Object> maMapValues = updateMA.toMap();
//
//        myRef.child("MonAn").child(pushkeyMAHienTai).updateChildren(maMapValues);
//        Toast.makeText(getActivity(), "Đã cập nhật thông tin món ăn " + updateMA.getMaMonAn(), Toast.LENGTH_LONG).show();
    }

    private void getAllNhomHang(){
        myRef.child("NhomHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tenNhomHangArrayList.clear();
                nhomHangArrayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    tenNhomHangArrayList.add(child.child("tenNhomHang").getValue(String.class));
                    NhomHang nh = child.getValue(NhomHang.class);
                    nh.setPushKey(child.getKey());
                    nhomHangArrayList.add(nh);
                }
                Bundle bundle = getArguments();
                if (bundle != null) {
                    if (bundle.getSerializable("ma") != null) {
                        ma = (MonAn) bundle.getSerializable("ma");
                        loadMA(ma);
                        btnSua.setEnabled(true);
                        tvTitle.setText("Chi tiết món ăn");
                    }
                    else if (bundle.getString("btnThemMA") != null) {
                        tvTitle.setText("Thêm mới món ăn");
                        setFocusEditText(true);
                        edtMaMA.setEnabled(false);
                        btnSua.setText("Hủy");
                    }
                }
                tenNhomHangArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Lỗi. Không thể lấy dữ liệu nhóm hàng: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertErrorDataInput() {
        edtTenMA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regexTenMA = "^[\\p{L}\\s]+$";
                if (!Pattern.matches(regexTenMA, edtTenMA.getText())) {
                    edtTenMA.setError("Vui lòng nhập tên món ăn là chữ và không chứa ký tự đặc biệt từ 1 ký tự trở lên");
                }
            }
        });

        edtGia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String regexGia = "^\\d+$";
                if (!Pattern.matches(regexGia, edtGia.getText())) {
                    edtGia.setError("Vui lòng nhập đúng định dạng số");
                }
            }
        });
    }

    private void setFocusEditText(boolean b) {
        edtMaMA.setFocusable(b);
        edtMaMA.setFocusableInTouchMode(b);
        edtTenMA.setFocusable(b);
        edtTenMA.setFocusableInTouchMode(b);
        edtGia.setFocusable(b);
        edtGia.setFocusableInTouchMode(b);

        edtMaMA.setEnabled(b);
        snLoai.setEnabled(b);
        snMaNhomHang.setEnabled(b);
        ckTrangThai.setEnabled(b);
        btnSua.setEnabled(b);
        btnLuu.setEnabled(b);
    }

    private void clearAlertErrorDataInput() {
        edtMaMA.setError(null);
        edtTenMA.setError(null);
        edtGia.setError(null);
    }

    private void clearDataInputMA() {
        edtMaMA.setText("");
        edtTenMA.setText("");
        edtGia.setText("");
        snLoai.setSelection(0);
        snMaNhomHang.setSelection(0);
    }

    public void verifyStoragePermissions(){
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(getContext(),
                permissions[0] ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),
                permissions[1] ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),
                permissions[2] ) == PackageManager.PERMISSION_GRANTED) {
            mStoragePermissions = true;
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    permissions,
                    REQUEST_CODE
            );
        }
    }

    @Override
    public void getImagePath(Uri imagePath) {
        if( !imagePath.toString().equals("")){
            mSelectedImageBitmap = null;
            mSelectedImageUri = imagePath;
            Log.d("", "getImagePath: got the image uri: " + mSelectedImageUri);
            ImageLoader.getInstance().displayImage(imagePath.toString(), imgMonAn);
        }

    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        if(bitmap != null){
            mSelectedImageUri = null;
            mSelectedImageBitmap = bitmap;
            Log.d("", "getImageBitmap: got the image bitmap: " + mSelectedImageBitmap);
            imgMonAn.setImageBitmap(bitmap);
        }
    }

    public void uploadNewPhoto(Uri imageUri){
        Log.d("", "uploadNewPhoto: uploading new profile photo to firebase storage.");

        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imageUri);
    }

    public void uploadNewPhoto(Bitmap imageBitmap){
        Log.d("", "uploadNewPhoto: uploading new profile photo to firebase storage.");

        BackgroundImageResize resize = new BackgroundImageResize(imageBitmap);
        Uri uri = null;
        resize.execute(uri);
    }

    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {

        Bitmap mBitmap;
        public BackgroundImageResize(Bitmap bm) {
            if(bm != null){
                mBitmap = bm;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
            Toast.makeText(getContext(), "compressing image", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected byte[] doInBackground(Uri... params ) {
            Log.d("", "doInBackground: started.");

            if(mBitmap == null){

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), params[0]);
                    Log.d("", "doInBackground: bitmap size: megabytes: " + mBitmap.getByteCount()/MB + " MB");
                } catch (IOException e) {
                    Log.e("", "doInBackground: IOException: ", e.getCause());
                }
            }

            byte[] bytes = null;
            for (int i = 1; i < 11; i++){
                if(i == 10){
                    Toast.makeText(getActivity(), "That image is too large.", Toast.LENGTH_SHORT).show();
                    break;
                }
                bytes = getBytesFromBitmap(mBitmap,100/i);
                Log.d("", "doInBackground: megabytes: (" + (11-i) + "0%) "  + bytes.length/MB + " MB");
                if(bytes.length/MB  < MB_THRESHHOLD){
                    return bytes;
                }
            }
            return bytes;
        }


        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            hideDialog();
            mBytes = bytes;
            executeUploadTask();
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideDialog() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void executeUploadTask(){
        showDialog();
        FilePaths filePaths = new FilePaths();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + ma.getPushKey()
                        + "/anhmonan");

        if(mBytes.length/MB < MB_THRESHHOLD) {

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .setContentLanguage("en")
//                    .setCustomMetadata("Mitch's special meta data", "JK nothing special here")
                    .setCustomMetadata("location", "VietNam")
                    .build();
            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(mBytes, metadata);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseURL = taskSnapshot.getDownloadUrl();
                    Toast.makeText(getActivity(), "Cập nhật ảnh món ăn thành công", Toast.LENGTH_SHORT).show();
                    Log.d("", "onSuccess: firebase download url : " + firebaseURL.toString());
                    myRef.child("NhomHang").child(ma.getPushKeyNhomHang()).child("danhSachMonAn")
                            .child(ma.getPushKey())
                            .child("urlAnh")
                            .setValue(firebaseURL.toString());

                    hideDialog();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getActivity(), "Cập nhật ảnh món ăn không thành công", Toast.LENGTH_SHORT).show();

                    hideDialog();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if(currentProgress > (progress + 15)){
                        progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d("", "onProgress: Upload is " + progress + "% done");
                        Toast.makeText(getActivity(), progress + "%", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            ;
        }else{
            Toast.makeText(getContext(), "Image is too Large", Toast.LENGTH_SHORT).show();
        }
    }
}