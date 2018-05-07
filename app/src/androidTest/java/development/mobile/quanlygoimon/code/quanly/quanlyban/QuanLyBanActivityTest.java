package development.mobile.quanlygoimon.code.quanly.quanlyban;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.junit.Test;

import development.mobile.quanlygoimon.code.R;

public class QuanLyBanActivityTest extends ActivityInstrumentationTestCase2<QuanLyBanActivity> {

    public QuanLyBanActivityTest() {
        super(QuanLyBanActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testButton() {
        Button btnThem = (Button) getActivity().findViewById(R.id.btn_themban_quanly_qlban);
        assertNotNull(btnThem);
    }

    @SmallTest
    public void testSpinner() {
        Spinner snKhuVuc = (Spinner) getActivity().findViewById(R.id.sn_khuvuc_quanly_qlban);
        assertNotNull(snKhuVuc);
    }

    @SmallTest
    public void testTextView() {
        TextView tvChiTiet, tvMa, tvTrangThai, tvBtnXoa;

        tvChiTiet = (TextView) getActivity().findViewById(R.id.tv_field_chitiet_quanly_qlban);
        assertNotNull(tvChiTiet);
        tvMa = (TextView) getActivity().findViewById(R.id.tv_field_maban_item_quanly_qlban);
        assertNotNull(tvMa);
        tvTrangThai = (TextView) getActivity().findViewById(R.id.tv_field_trangthai_item_quanly_qlban);
        assertNotNull(tvTrangThai);
        tvBtnXoa = (TextView) getActivity().findViewById(R.id.field_ibtn_xoaban_item_quanly_qlban);
        assertNotNull(tvBtnXoa);
    }

    @SmallTest
    public void testListView() {
        ListView lvDanhSach = (ListView) getActivity().findViewById(R.id.lv_dsban_quanly_qlban);
        assertNotNull(lvDanhSach);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void getAllBanTheoKhuVuc() {
    }

    @Test
    public void onActivityResult() {
    }

    @Test
    public void onBackPressed() {
    }
}