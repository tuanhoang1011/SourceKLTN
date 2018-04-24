package development.mobile.quanlygoimon.code.bep;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.phucvuchonban.PagerAdapter;

public class BepActivity extends AppCompatActivity {

    private ViewPager pagerBep;
    private PagerAdapterBep adapterBep;
    private TabLayout tabLayoutBep;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bep);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        pagerBep = (ViewPager) findViewById(R.id.view_pager_bep);
        tabLayoutBep = (TabLayout) findViewById(R.id.tab_layout_bep);

        FragmentManager manager = getSupportFragmentManager();
        adapterBep = new PagerAdapterBep(manager);
        pagerBep.setAdapter(adapterBep);

        tabLayoutBep.setupWithViewPager(pagerBep);
        pagerBep.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutBep));
    }
}
