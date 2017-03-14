package cuexpo.cuexpo2017.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.SearchListAdapter;
import cuexpo.cuexpo2017.view.EventListItem;
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
        ((EditText) rootView.findViewById(R.id.search)).addTextChangedListener(searchWatcher);
        searchListAdapter = new SearchListAdapter(eventList, false, getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(searchListAdapter);
//        scaleAdapter.setDuration(3000);
        recyclerView.setAdapter(scaleAdapter);
        initEventList();
        return rootView;
    }

    private TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO call search result api here
            Log.d("afterTextChanged", s.toString());
        }
    };

    private void initEventList() {
        eventList.clear();
        for(int i=0; i<20; i++){
            String tags = "ENG";
            eventList.add(new EventListItem(1, "The Accountant", "18 มีนาคม 13.00-14.00", tags));
        }

        searchListAdapter.notifyDataSetChanged();
        // TODO remove all and call nearby event here
    }

    // TODO call this after call api
    private void setEventList() {
        eventList.clear();
        searchListAdapter.notifyDataSetChanged();
    }

}
