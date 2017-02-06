package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.ActivityListAdapter;
import cuexpo.chulaexpo.adapter.HighlightListAdapter;
import cuexpo.chulaexpo.adapter.HomeStageListAdapter;
import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.manager.PhotoListManager;
import cuexpo.chulaexpo.view.ExpandableHeightListView;
import me.relex.circleindicator.CircleIndicator;

public class EventPageFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView ivToolbarQR;
    private View rootView;


    public EventPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
        initTab();
        initInstances(savedInstanceState);
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
                .add("city", MoreFragment.class)
                .add("faculty", MoreFragment.class)
                .add("interest", MoreFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        if (v == ivToolbarQR) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.event_page_containerQR, new QRFragment().newInstance(), "QRFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
