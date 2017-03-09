package cuexpo.chulaexpo2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cuexpo.chulaexpo2017.adapter.SearchListAdapter;
import cuexpo.chulaexpo2017.view.EventListItem;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class SearchFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private SearchListAdapter searchListAdapter;
    private List<EventListItem> eventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        searchListAdapter = new SearchListAdapter(eventList, false, getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(searchListAdapter);
//        scaleAdapter.setDuration(3000);
        recyclerView.setAdapter(scaleAdapter);
        setEventList();
        return rootView;
    }

    private void setEventList() {
        eventList.clear();
        for(int i=0; i<20; i++){
            String []tags = {"ENG"};
            eventList.add(new EventListItem(1, "The Accountant", "18 มีนาคม 13.00-14.00", tags));
        }
        searchListAdapter.notifyDataSetChanged();
    }

}
