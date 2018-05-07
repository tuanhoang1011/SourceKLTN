package development.mobile.quanlygoimon.code.quanly;

import android.support.test.filters.SmallTest;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class QuanLyActivityTest extends ActivityInstrumentationTestCase2<QuanLyActivity> {

    public QuanLyActivityTest() {
        super(QuanLyActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_quanLyAct);
        assertNotNull(toolbar);
    }

    @SmallTest
    public void testTextView() {
        TextView titleToolbarTxtView, dangXuatTxtView;

        titleToolbarTxtView = (TextView) getActivity().findViewById(R.id.toolbarTitle_quanLyAct);
        assertNotNull(titleToolbarTxtView);
        dangXuatTxtView = (TextView) getActivity().findViewById(R.id.dangXuatTxtView_quanLyAct);
        assertNotNull(dangXuatTxtView);
    }

    @SmallTest
    public void testViewPager() {
        GridView grdViewQuanLy;
        grdViewQuanLy = (GridView) getActivity().findViewById(R.id.grdView_quanly);
        assertNotNull(grdViewQuanLy);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}