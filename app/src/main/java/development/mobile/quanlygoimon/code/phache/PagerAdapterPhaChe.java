package development.mobile.quanlygoimon.code.phache;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import development.mobile.quanlygoimon.code.phache.dattruoc.PhaCheDatTruocFragment;
import development.mobile.quanlygoimon.code.phache.taiban.PhaCheTaiBanFragment;

public class PagerAdapterPhaChe extends FragmentPagerAdapter {
    public PagerAdapterPhaChe(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;

        switch (position) {
            case 0:
                frag = new PhaCheTaiBanFragment();
                break;
            case 1:
                frag = new PhaCheDatTruocFragment();
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
                title = "Tại bàn";
                break;
            case 1:
                title = "Đặt trước";
                break;
        }

        return title;
    }
}