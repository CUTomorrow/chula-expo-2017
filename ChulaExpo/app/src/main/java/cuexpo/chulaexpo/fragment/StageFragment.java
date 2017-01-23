package cuexpo.chulaexpo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.StageListAdapter;
import cuexpo.chulaexpo.view.DateSelector;
import cuexpo.chulaexpo.view.StageInsideListItem;
import cuexpo.chulaexpo.view.StageListItem;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StageFragment extends Fragment implements View.OnClickListener {

    List<StageListItem> listDataHeader;
    HashMap<StageListItem, StageInsideListItem> listDataChild;
    ExpandableListView expandableListView;
    StageListAdapter listAdapter;
    DateSelector day15;
    DateSelector day16;
    DateSelector day17;
    DateSelector day18;
    DateSelector day19;

    int previousGroup = -1;

    public StageFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StageFragment newInstance() {
        StageFragment fragment = new StageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stage, container, false);
        initInstances(rootView, savedInstanceState);

        StageListItem item = new StageListItem(getContext());
        StageInsideListItem item2 = new StageInsideListItem(getContext());

        day15.setDate("15");
        day16.setDate("16");
        day17.setDate("17");
        day18.setDate("18");
        day19.setDate("19");

        day15.setToggle(1);
        day16.setToggle(0);
        day17.setToggle(0);
        day18.setToggle(0);
        day19.setToggle(0);

        day15.setOnClickListener(this);
        day16.setOnClickListener(this);
        day17.setOnClickListener(this);
        day18.setOnClickListener(this);
        day19.setOnClickListener(this);

        item.setTime("08.00","08.05");
        item.setName("Opening");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.05","08.10");
        item.setName("Chulalongkorn Talk");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.10","08.15");
        item.setName("Robotic Vaccum Cleaner");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.15","08.20");
        item.setName("In The Time Of Modern : Overture");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.20","08.30");
        item.setName("In The Time Of Modern : Epilouge");
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.30","09.30");
        item.setName("Final Fantasy : Game Conceptual Design");
        listDataHeader.add(item);

        listDataChild.put(listDataHeader.get(0), item2);
        listDataChild.put(listDataHeader.get(1), item2);
        listDataChild.put(listDataHeader.get(2), item2);
        listDataChild.put(listDataHeader.get(3), item2);
        listDataChild.put(listDataHeader.get(4), item2);
        listDataChild.put(listDataHeader.get(5), item2);


        listAdapter = new StageListAdapter(listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);

        // Listview Group click listener
        /*expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });*/

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
        day15 = (DateSelector) rootView.findViewById(R.id.stage_date_selector);
        day16 = (DateSelector) rootView.findViewById(R.id.stage_date_selector2);
        day17 = (DateSelector) rootView.findViewById(R.id.stage_date_selector3);
        day18 = (DateSelector) rootView.findViewById(R.id.stage_date_selector4);
        day19 = (DateSelector) rootView.findViewById(R.id.stage_date_selector5);
    }

    @Override
    public void onClick(View v) {
        if (v == day15) {
            day15.setToggle(1);
            day16.setToggle(0);
            day17.setToggle(0);
            day18.setToggle(0);
            day19.setToggle(0);
        } else if (v == day16) {
            day15.setToggle(0);
            day16.setToggle(1);
            day17.setToggle(0);
            day18.setToggle(0);
            day19.setToggle(0);
        } else if (v == day17) {
            day15.setToggle(0);
            day16.setToggle(0);
            day17.setToggle(1);
            day18.setToggle(0);
            day19.setToggle(0);
        } else if (v == day18) {
            day15.setToggle(0);
            day16.setToggle(0);
            day17.setToggle(0);
            day18.setToggle(1);
            day19.setToggle(0);
        } else if (v == day19) {
            day15.setToggle(0);
            day16.setToggle(0);
            day17.setToggle(0);
            day18.setToggle(0);
            day19.setToggle(1);
        } else {

        }
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
