package development.mobile.quanlygoimon.code.phucvuchonban;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import development.mobile.quanlygoimon.code.R;

public class PhucVuActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter adapter;
    private TabLayout tabLayout;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phucvu);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        FragmentManager manager = getSupportFragmentManager();
        adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
