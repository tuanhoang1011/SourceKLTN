package development.mobile.quanlygoimon.code.quanly.quanlynhanvien;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.NhanVien;

public class QuanLyNhanVienChiTietFragment extends Fragment {

    private ImageButton btnCheBien, btnTamNgungPhucVu;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    NhanVien nv = new NhanVien();

    public QuanLyNhanVienChiTietFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_quanly_qlnv, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            nv = (NhanVien) bundle.getSerializable("nv");
            Toast.makeText(getActivity(), "ChiTiet++" +  nv.toString(), Toast.LENGTH_LONG).show();

            Log.d("NhanVien", "NhanVien: " + nv.toString());
        }


        return view;
    }

    private void createNhanVien(){

    }

    private void editNhanVien(){

    }
}