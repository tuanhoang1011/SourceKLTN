package development.mobile.quanlygoimon.code.bep.taiban;

import android.test.AndroidTestCase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.lang.reflect.Method;

import development.mobile.quanlygoimon.code.bep.dattruoc.DatTruocDanhSachFragment;

public class TaiBanDangChoFragmentTest extends AndroidTestCase{



    @Test
    public void testConnectFirebase() throws Throwable{
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Method method = DatTruocDanhSachFragment.class.getDeclaredMethod("getAllPhieuDatTruoc");
        method.setAccessible(true);
//        return method.invoke(targetObject, argObjects);

//        Method method = targetClass.getDeclaredMethod(methodName, argClasses);
//        method.setAccessible(true);
//        return method.invoke(targetObject, argObjects);

//        Field field = targetClass.getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(object, value);

//        assertNotNull();
    }
}