package cuexpo.chulaexpo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import cuexpo.chulaexpo.adapter.SearchListAdapter;
import cuexpo.chulaexpo.fragment.EventDetailFragment;
import cuexpo.chulaexpo.view.EventListItem;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


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
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.setAdapter(searchListAdapter);
        setEventList();
        return rootView;
    }

    private void setEventList() {
        eventList.clear();
        for(int i=0; i<10; i++){
            String []tags = {"ENG"};
            eventList.add(new EventListItem(1, "The Accountant", "18 มีนาคม 13.00-14.00", tags));
        }
        searchListAdapter.notifyDataSetChanged();
    }

}
