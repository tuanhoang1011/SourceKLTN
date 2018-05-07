package development.mobile.quanlygoimon.code.phache;

import android.support.design.widget.TabLayout;
import android.support.test.filters.SmallTest;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import development.mobile.quanlygoimon.code.R;

public class PhaCheActivityTest extends ActivityInstrumentationTestCase2<PhaCheActivity> {

    public PhaCheActivityTest() {
        super(PhaCheActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_phaCheAct);
        assertNotNull(toolbar);
    }

    @SmallTest
    public void testTextView() {
        TextView titleToolbarTxtView, dangXuatTxtView;

        titleToolbarTxtView = (TextView) getActivity().findViewById(R.id.toolbarTitle_phaCheAct);
        assertNotNull(titleToolbarTxtView);
        dangXuatTxtView = (TextView) getActivity().findViewById(R.id.dangXuatTxtView_phaCheAct);
        assertNotNull(dangXuatTxtView);
    }

    @SmallTest
    public void testViewPager() {
        ViewPager pagerBep;
        pagerBep = (ViewPager) getActivity().findViewById(R.id.view_pager_phaChe);
        assertNotNull(pagerBep);
    }

    @SmallTest
    public void testTabLayout() {
        TabLayout tabLayoutBep = (TabLayout) getActivity().findViewById(R.id.tab_layout_phaChe);
        assertNotNull(tabLayoutBep);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}