package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.fragment.EventPageFragment;
import cuexpo.cuexpo2017.fragment.HomeFragment;
import cuexpo.cuexpo2017.fragment.MapFragment;
import cuexpo.cuexpo2017.fragment.ProfileFragment;
import cuexpo.cuexpo2017.utility.IGoToMapable;
import cuexpo.cuexpo2017.utility.NormalPinMapEntity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, IGoToMapable {

    public View rootView;
    private SharedPreferences sharedPref;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = this.findViewById(android.R.id.content);
        initTab();
    }

    private void initTab() {
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
        final Context viewPagerContext = viewPagerTab.getContext();
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon, container, false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_home));
                        break;
                    case 1:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_map));
                        break;
                    case 2:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_event));
                        break;
                    case 3:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_profile));
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
                .add("4", ProfileFragment.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        Log.d("backPressed", "back stack = " + getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
            if (sharedPref.getString("id", "").equals("")) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("ขออภัย");
                alert.setMessage("ฟังก์ชันแก้ไขข้อมูลเเปิดให้เฉพาะ Facebook User เท่านั้น!");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert2 = alert.create();
                alert2.show();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void goToMap(int facultyId) {


    }

    @Override
    public void goToMap(NormalPinMapEntity entity) {
        viewPager.setCurrentItem(1); // Go to map
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        mapFragment.goToMap(entity);
    }
}
