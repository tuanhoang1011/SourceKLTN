package development.mobile.quanlygoimon.code.bep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import development.mobile.quanlygoimon.code.phucvuchonban.BanDangPhucVuFragment;
import development.mobile.quanlygoimon.code.phucvuchonban.BanTrongFragment;

public class PagerAdapterBep extends FragmentPagerAdapter {
    public PagerAdapterBep(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;

        switch (position) {
            case 0:
                frag = new BanTrongFragment();
                break;
            case 1:
                frag = new BanDangPhucVuFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position) {
            case 0:
                title = "Bàn trống";
                break;
            case 1:
                title = "Bàn đang phục vụ";
                break;
        }

        return title;
    }
}