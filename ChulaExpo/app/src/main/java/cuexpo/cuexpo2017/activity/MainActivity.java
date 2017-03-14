package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.dao.User;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.fragment.EventPageFragment;
import cuexpo.cuexpo2017.fragment.HomeFragment;
import cuexpo.cuexpo2017.fragment.MapFragment;
import cuexpo.cuexpo2017.fragment.ProfileFragment;
import cuexpo.cuexpo2017.fragment.SearchFragment;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.IGoToMapable;
import cuexpo.cuexpo2017.utility.NormalPinMapEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, IGoToMapable {

    public View rootView;
    private SharedPreferences sharedPref;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    private MainActivity thisAcitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        thisAcitivity = this;

        setContentView(R.layout.activity_main);
        rootView = thisAcitivity.findViewById(android.R.id.content);

        sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
        if (!sharedPref.getString("apiToken", "").equals(""))
            HttpManager.getInstance().setAPIKey(sharedPref.getString("apiToken", ""));

        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                initTab();
                return null;
            }
        }.execute();
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
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_home));
                        break;
                    case 4:
                        icon.setImageDrawable(ContextCompat.getDrawable(viewPagerContext, R.drawable.selector_tab_profile));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        });


        runOnUiThread(new Thread(new Runnable() {
            public void run() {

                FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(thisAcitivity)
                        .add("1", HomeFragment.class)
                        .add("2", MapFragment.class)
                        .add("3", EventPageFragment.class)
                        .add("4", SearchFragment.class)
                        .add("5", ProfileFragment.class)
                        .create());

                viewPager = (ViewPager) findViewById(R.id.pager);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(thisAcitivity);
                viewPager.setOffscreenPageLimit(4);
                viewPagerTab.setViewPager(viewPager);

                getTabInfo();
            }
        }));
    }

    private void getTabInfo() {

        if (!sharedPref.getString("apiToken", "").equals("")) {
            Call<UserDao> callUser = HttpManager.getInstance().getService().loadUserInfo();
            callUser.enqueue(new Callback<UserDao>() {
                @Override
                public void onResponse(Call<UserDao> call, Response<UserDao> response) {
                    Log.e("UserInfo", "Load UserInfo Success");
                    if (response.isSuccessful()) {
                        UserDao dao = response.body();
                        if (dao.getSuccess()) {
                            Log.e("UserInfo", "Success=true");
                            //keep token
                            SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("uid", dao.getResults().getId());
                            editor.putString("type", dao.getResults().getType());
                            editor.putString("profile", dao.getResults().getProfile());
                            editor.putString("gender", dao.getResults().getGender());
                            editor.putString("name", dao.getResults().getName());
                            editor.putString("emails", dao.getResults().getEmail());
                            editor.putString("tags", TextUtils.join(",", dao.getResults().getTags()));
                            Log.e("UserInfo", TextUtils.join(",", dao.getResults().getTags()));
                            try {
                                editor.putInt("age", dao.getResults().getAge());
                            } catch (Exception e) {
                                editor.putInt("age", 0);
                            }
                            if (dao.getResults().getType().equals("Academic")) {
                                if (dao.getResults().getAcademic() != null) {
                                    editor.putString("academicSchool", dao.getResults().getAcademic().getAcademicSchool());
                                    editor.putString("academicYear", dao.getResults().getAcademic().getAcademicYear());
                                    editor.putString("academicLevel", dao.getResults().getAcademic().getAcademicLevel());
                                }
                            } else if (dao.getResults().getType().equals("Worker")) {
                                editor.putString("workerJob", dao.getResults().getWorker().getJob());
                            }
                            editor.apply();
                        } else {

                        }
                    } else {
                        Log.e("UserInfo", "Load UserInfo Incomplete");
                    }
                }

                @Override
                public void onFailure(Call<UserDao> call, Throwable t) {
                    Log.e("UserInfo", "Load UserInfo Failure " + t.toString());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("backPressed", "back stack = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void goToMap(int facultyId) {
        viewPager.setCurrentItem(1); // Go to map
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        mapFragment.goToMap(facultyId);
    }

    @Override
    public void goToMap(NormalPinMapEntity entity) {
        viewPager.setCurrentItem(1); // Go to map
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        mapFragment.goToMap(entity);
    }
}
