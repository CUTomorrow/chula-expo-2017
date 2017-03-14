package cuexpo.cuexpo2017.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.FacilityDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;

public class EventPageFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivToolbarQR;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                getActivity().runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        initTab();
                        initInstances(savedInstanceState);
                    }
                }));
                return null;
            }
        }.execute();

        return rootView;
    }

    private void initInstances(Bundle savedInstanceState) {
        toolbar = (Toolbar)rootView.findViewById(R.id.event_page_toolbar);
        toolbar.setTitle("");
        ivToolbarQR = (ImageView) rootView.findViewById(R.id.event_Page_toolbar_qr);
        ivToolbarQR.setOnClickListener(this);
    }

    private void initTab() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                this.getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("city", CityListFragment.class)
                .add("faculty", FacultyListFragment.class)
                .add("interest", InterestListFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        if (v == ivToolbarQR) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new QRFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
