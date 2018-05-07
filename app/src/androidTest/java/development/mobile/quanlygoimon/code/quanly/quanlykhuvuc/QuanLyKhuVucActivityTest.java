package development.mobile.quanlygoimon.code.quanly.quanlykhuvuc;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.FrameLayout;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class QuanLyKhuVucActivityTest extends ActivityInstrumentationTestCase2<QuanLyKhuVucActivity> {

    public QuanLyKhuVucActivityTest() {
        super(QuanLyKhuVucActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testTextView() {
        TextView tvDanhSach, tvChiTiet;

        tvDanhSach = (TextView) getActivity().findViewById(R.id.tv_field_danhsach_quanly_qlkv);
        assertNotNull(tvDanhSach);
        tvChiTiet = (TextView) getActivity().findViewById(R.id.tv_field_chitiet_quanly_qlkv);
        assertNotNull(tvChiTiet);
    }

    @SmallTest
    public void testFragment() {
        FrameLayout fl_chitiet = (FrameLayout) getActivity().findViewById(R.id.frame_layout_holder_chitiet_quanly_qlkv);
        assertNotNull(fl_chitiet);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}