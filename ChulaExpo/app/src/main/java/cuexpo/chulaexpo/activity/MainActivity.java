package cuexpo.chulaexpo.activity;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.SearchFragment;
import cuexpo.chulaexpo.fragment.EventPageFragment;
import cuexpo.chulaexpo.fragment.HomeFragment;
import cuexpo.chulaexpo.fragment.MapFragment;
import cuexpo.chulaexpo.fragment.ProfileFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    public View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = this.findViewById(android.R.id.content);
        initTab();
    }

    private void initTab(){
        final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
        final Context viewPagerContext = viewPagerTab.getContext();
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon, container, false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    case 1:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    case 2:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    case 3:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    case 4:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    case 5:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_mock_tab));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        });

        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("1", HomeFragment.class)
                .add("2", MapFragment.class)
                .add("3", EventPageFragment.class)
                .add("4", SearchFragment.class)
                .add("5", ProfileFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
