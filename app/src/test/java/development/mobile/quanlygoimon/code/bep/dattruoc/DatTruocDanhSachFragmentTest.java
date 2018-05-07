package development.mobile.quanlygoimon.code.bep.dattruoc;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.entity.PhieuDatTruoc;

@RunWith(AndroidJUnit4.class)
public class DatTruocDanhSachFragmentTest {
//    private FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private DatabaseReference myRef = database.getReference();
//    private List<PhieuDatTruoc> phieuDatTruocArrayList = new ArrayList<>();
//    private ValueEventListener listener;

//    @Rule public final Instrumentation.ActivityResult<> bep = new Instrumentation.ActivityResult<>(BepActivity.class);

    DatTruocDanhSachFragment datTruocDanhSachFragment;


    @Test
    public void getAllPhieuDatTruoc() {

//        FirebaseApp.initializeApp(DatTruocFragment.class.getClass());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        List<PhieuDatTruoc> phieuDatTruocArrayList = new ArrayList<>();
        ValueEventListener listener;

//        assertNotNull(datTruocDanhSachFragment.getAllPhieuDatTruoc());

    }
}