package cuexpo.chulaexpo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.StageListAdapter;
import cuexpo.chulaexpo.manager.StageManager;
import cuexpo.chulaexpo.view.DateSelector;
import cuexpo.chulaexpo.view.StageInsideListItem;
import cuexpo.chulaexpo.view.StageListItem;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StageFragmentDetail extends Fragment {

    ExpandableListView expandableListView;
    StageListAdapter listAdapter;
    private List<StageListItem> listDataHeader;
    private  HashMap<StageListItem, StageInsideListItem> listDataChild;

    int previousGroup = -1;
    int myInt;

    public StageFragmentDetail() {
        super();
    }

    @SuppressWarnings("unused")
    public static StageFragmentDetail newInstance() {
        StageFragmentDetail fragment = new StageFragmentDetail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        Bundle date = this.getArguments();
        if (date != null) {
            myInt = date.getInt("day", 15);
        }

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stage_detail, container, false);
        initInstances(rootView, savedInstanceState);

        StageListItem item = new StageListItem(getContext());
        StageInsideListItem item2 = new StageInsideListItem(getContext());

        item.setTime("08.00", "08.05");
        if (myInt == 15) item.setName("Opening15");
        else if (myInt == 16) item.setName("Opening16");
        else if (myInt == 17) item.setName("Opening17");
        else if (myInt == 18) item.setName("Opening18");
        else if (myInt == 19) item.setName("Opening19");

        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.05", "08.10");
        item.setName("Chulalongkorn Talk");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.10", "08.15");
        item.setName("Robotic Vaccum Cleaner");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.15", "08.20");
        item.setName("In The Time Of Modern : Overture");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.20", "08.30");
        item.setName("In The Time Of Modern : Epilouge");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.30", "09.30");
        item.setName("Final Fantasy : Game Conceptual Design");
        listDataHeader.add(item);

        listDataChild.put(listDataHeader.get(0), item2);
        item2 = new StageInsideListItem(getContext());
        item2.setDescription("a");
        listDataChild.put(listDataHeader.get(1), item2);
        item2 = new StageInsideListItem(getContext());
        item2.setDescription("b");
        listDataChild.put(listDataHeader.get(2), item2);
        item2 = new StageInsideListItem(getContext());
        item2.setDescription("c");
        listDataChild.put(listDataHeader.get(3), item2);
        item2 = new StageInsideListItem(getContext());
        item2.setDescription("d");
        listDataChild.put(listDataHeader.get(4), item2);
        item2 = new StageInsideListItem(getContext());
        item2.setDescription("e");
        listDataChild.put(listDataHeader.get(5), item2);


        listAdapter = new StageListAdapter(listDataHeader,listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);

        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Only One Item Will be Expanded
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        // Listview Group collasped listener
        /*expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });*/

        // Listview on child click listener
        /*expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });*/

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.stage_content_container);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
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

}