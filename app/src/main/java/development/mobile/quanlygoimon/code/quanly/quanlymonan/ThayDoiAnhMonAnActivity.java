package development.mobile.quanlygoimon.code.quanly.quanlymonan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import development.mobile.quanlygoimon.code.R;

public class ThayDoiAnhMonAnActivity extends AppCompatActivity {
    private static final String TAG = "ThayDoiAnhMonAnActivity";

    public static final int  CAMERA_REQUEST_CODE = 5467;
    public static final int PICKFILE_REQUEST_CODE = 8352;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_anh_mon_an);

        setTitle("Chọn ảnh món ăn");

        TextView selectPhoto = (TextView) findViewById(R.id.dialogChoosePhoto_ThayDoiAnhMonAn);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        TextView takePhoto = (TextView) findViewById(R.id.dialogOpenCamera_ThayDoiAnhMonAn);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: image: " + selectedImageUri);


            Intent intent = new Intent();
            intent.putExtra("uriImageSelected", selectedImageUri);

//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//
//            bundle.putString("ma", edtMaBan.getText().toString().trim());
//            intent.putExtra("data", bundle);

            setResult(PICKFILE_REQUEST_CODE, intent);

            finish();

//            mOnPhotoReceived.getImagePath(selectedImageUri);
        }

        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: done taking a photo.");

            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putByteArray("bitmapTook", byteArray);
            intent.putExtra("datacamera", bundle);
            setResult(CAMERA_REQUEST_CODE, intent);

            finish();

//            mOnPhotoReceived.getImageBitmap(bitmap);
        }
    }
}
