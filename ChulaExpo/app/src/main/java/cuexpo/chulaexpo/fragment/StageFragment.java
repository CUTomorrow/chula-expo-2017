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
import cuexpo.chulaexpo.view.StageInsideListItem;
import cuexpo.chulaexpo.view.StageListItem;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StageFragment extends Fragment {

    List<StageListItem> listDataHeader;
    HashMap<StageListItem, StageInsideListItem> listDataChild;
    ExpandableListView expandableListView;
    StageListAdapter listAdapter;
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

        item.setTime("08.00");
        item.setStatus(1);
        item.setName("Opening");
        item.setLineMode(1);
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.20");
        item.setStatus(1);
        item.setName("Chulalongkorn Talk");
        item.setLineMode(3);
        listDataHeader.add(item);

        item = new StageListItem(getContext());
        item.setTime("08.30");
        item.setStatus(1);
        item.setName("Robotic Vaccum Cleaner");
        item.setLineMode(6);
        listDataHeader.add(item);

        listDataChild.put(listDataHeader.get(0),item2);
        listDataChild.put(listDataHeader.get(1),item2);
        listDataChild.put(listDataHeader.get(2),item2);

        listAdapter = new StageListAdapter(listDataHeader,listDataChild);

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
