package development.mobile.quanlygoimon.code.bep;

import android.support.design.widget.TabLayout;
import android.support.test.filters.SmallTest;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class BepActivityTest extends ActivityInstrumentationTestCase2<BepActivity> {

    public BepActivityTest() {
        super(BepActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_BepAct);
        assertNotNull(toolbar);
    }

    @SmallTest
    public void testTextView() {
        TextView titleToolbarTxtView, dangXuatTxtView;

        titleToolbarTxtView = (TextView) getActivity().findViewById(R.id.toolbarTitle_bepAct);
        assertNotNull(titleToolbarTxtView);
        dangXuatTxtView = (TextView) getActivity().findViewById(R.id.dangXuatTxtView_bepAct);
        assertNotNull(dangXuatTxtView);
    }

    @SmallTest
    public void testViewPager() {
        ViewPager pagerBep;
        pagerBep = (ViewPager) getActivity().findViewById(R.id.view_pager_bep);
        assertNotNull(pagerBep);
    }

    @SmallTest
    public void testTabLayout() {
        TabLayout tabLayoutBep = (TabLayout) getActivity().findViewById(R.id.tab_layout_bep);
        assertNotNull(tabLayoutBep);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}