package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cuexpo.cuexpo2017.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StageFragment extends Fragment implements View.OnClickListener {

    private FragmentPagerItemAdapter pagerItemAdapter;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;
    private TextView tvStageName;
    private ImageView ivBack;
    private ImageView ivSearch;
    private int stageNo;
    private String stageId;


    public StageFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StageFragment newInstance(int stageNo, String stageId) {
        StageFragment fragment = new StageFragment();
        Bundle args = new Bundle();
        args.putInt("stageNo", stageNo);
        args.putString("stageId", stageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        stageNo = getArguments().getInt("stageNo");
        stageId = getArguments().getString("stageId");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stage, container, false);
        initInstances(rootView, savedInstanceState);
        SharedPreferences sharePref = getActivity().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
        if (sharePref.getString(stageId, "").equals("SALA ST"))
            tvStageName.setText("ศาลาพระเกี้ยว");
        else
            tvStageName.setText(sharePref.getString(stageId, ""));
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        tvStageName = (TextView) rootView.findViewById(R.id.stage_toolbar_title);
        ivBack = (ImageView) rootView.findViewById(R.id.stage_back);
        ivSearch = (ImageView) rootView.findViewById(R.id.stage_toolbar_search);
        viewPager = (ViewPager) rootView.findViewById(R.id.stage_pager);
        viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.stage_pager_tab);

        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        Bundle date15 = new Bundle();
        date15.putInt("day", 15);
        date15.putString("stageId", stageId);
        Bundle date16 = new Bundle();
        date16.putInt("day", 16);
        date16.putString("stageId", stageId);
        Bundle date17 = new Bundle();
        date17.putInt("day", 17);
        date17.putString("stageId", stageId);
        Bundle date18 = new Bundle();
        date18.putInt("day", 18);
        date18.putString("stageId", stageId);
        Bundle date19 = new Bundle();
        date19.putInt("day", 19);
        date19.putString("stageId", stageId);

        pagerItemAdapter = new FragmentPagerItemAdapter(
                this.getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("15\nMAR", StageDetailFragment.class, date15)
                .add("16\nMAR", StageDetailFragment.class, date16)
                .add("17\nMAR", StageDetailFragment.class, date17)
                .add("18\nMAR", StageDetailFragment.class, date18)
                .add("19\nMAR", StageDetailFragment.class, date19)
                .create());

        viewPager.setAdapter(pagerItemAdapter);
        viewPager.setOffscreenPageLimit(4);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        int day, month, year;
        Date current = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        Log.e("STAGE",day + " " + month + " " + year);
        if(year==2017){
            if(month==2){
                switch(day) {
                    case 15:
                        viewPager.setCurrentItem(0);
                        break;
                    case 16:
                        viewPager.setCurrentItem(1);
                        break;
                    case 17:
                        viewPager.setCurrentItem(2);
                        break;
                    case 18:
                        viewPager.setCurrentItem(3);
                        break;
                    case 19:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        break;
                }
            }
        }

        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            getActivity().finish();
        } else if (v == ivSearch) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.stage_overlay, new SearchFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}