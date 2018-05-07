package development.mobile.quanlygoimon.code.quanly.quanlymonan;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.FrameLayout;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class QuanLyMonAnActivityTest extends ActivityInstrumentationTestCase2<QuanLyMonAnActivity> {

    public QuanLyMonAnActivityTest() {
        super(QuanLyMonAnActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testTextView() {
        TextView tvDanhSach, tvChiTiet;

        tvDanhSach = (TextView) getActivity().findViewById(R.id.tv_field_danhsach_quanly_qlma);
        assertNotNull(tvDanhSach);
        tvChiTiet = (TextView) getActivity().findViewById(R.id.tv_field_chitiet_quanly_qlma);
        assertNotNull(tvChiTiet);
    }

    @SmallTest
    public void testFragment() {
        FrameLayout fl_chitiet = (FrameLayout) getActivity().findViewById(R.id.frame_layout_holder_chitiet_quanly_qlma);
        assertNotNull(fl_chitiet);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}