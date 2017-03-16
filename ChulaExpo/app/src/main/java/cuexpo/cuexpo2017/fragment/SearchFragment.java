package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.SearchListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.view.EventListItem;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private SearchListAdapter searchListAdapter;
    private List<EventListItem> eventList = new ArrayList<>();
    private List<ActivityItemResultDao> dao = new ArrayList<>();
    private EditText query;
    private TextView loadingNearby;
    private TextView loadingSearch;
    private String id;
    private boolean isSearching = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        query = ((EditText) rootView.findViewById(R.id.search));
        rootView.findViewById(R.id.back).setOnClickListener(backOCL);
        rootView.findViewById(R.id.search).setOnKeyListener(searchOEAL);
        loadingNearby = (TextView) rootView.findViewById(R.id.nearby_loading);
        loadingSearch = (TextView) rootView.findViewById(R.id.search_loading);

        searchListAdapter = new SearchListAdapter(getContext(), eventList, false, getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(searchListAdapter);
        recyclerView.setAdapter(scaleAdapter);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FacebookInfo", Context.MODE_PRIVATE);
        id = sharedPref.getString("id", "");
        Log.e("Search Fragment", "ID : " + id);
        initEventList();
        return rootView;
    }

    private OnClickListener backOCL = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

    TextView.OnKeyListener searchOEAL = new TextView.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                loadingSearch.setVisibility(View.VISIBLE);
                search();
                return true;
            }
            return false;

        }
    };

    private void search() {
        eventList.clear();
        loadingSearch.setText("Loading Data...");
        searchListAdapter.notifyDataSetChanged();

        String s = query.getText().toString();
        if (!isSearching) {
            isSearching = true;
            searchListAdapter.setSearching(true);
        }
        Call<ActivityItemCollectionDao> callSearchActivities;
        Log.e("Search Fragment", "String : " + s);
        callSearchActivities =
                HttpManager.getInstance().
                        getService().searchActivities(s);
        callSearchActivities.enqueue(callbackSearch);
    }

    Callback<ActivityItemCollectionDao> callbackSearch = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                dao = response.body().getResults();
                if(dao.size()>0) {
                    loadingSearch.setVisibility(View.GONE);
                    setEventList();
                } else{
                    loadingSearch.setText("ไม่พบข้อมูลกิจกรรม");
                }
                Toast.makeText(Contextor.getInstance().getContext(), "Result Found "
                        + dao.size() + ((dao.size() > 1)?" Entries":" Entry"), Toast.LENGTH_SHORT).show();
                Log.e("Search Fragment", "Search Finish with Size : " + dao.size());
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "Cannot Search. Please try again.", Toast.LENGTH_SHORT).show();
                Log.e("Search Fragment", "Search Fail " + response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Log.e("Search Fragment", "Search Fail " + t.toString());
            Toast.makeText(Contextor.getInstance().getContext(), "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
        }
    };

    private void initEventList() {
        eventList.clear();

        double lat = 13.74010;
        double lng = 100.53045;

        try {
            lat = MainApplication.getCurrentLocation().getLatitude();
            lng = MainApplication.getCurrentLocation().getLongitude();
        } catch (NullPointerException e) {
            Log.e("searchWhereAmI", e.toString());
        }

        Call<ActivityItemCollectionDao> callNearbyActivities =
                HttpManager.getInstance().getService().loadNearbyActivities(lat, lng);
        callNearbyActivities.enqueue(callbackNearbyActivities);
    }

    Callback<ActivityItemCollectionDao> callbackNearbyActivities = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                dao = response.body().getResults();
                loadingNearby.setVisibility(View.GONE);
                setEventList();
                Log.e("Search Fragment", "Nearby Finish with Size : " + response.body().getResults().size());
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "Cannot Get Nearby Activities. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("Search Fragment", "Nearby Fail " + response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Log.e("Search Fragment", "Nearby Fail " + t.toString());
            Toast.makeText(Contextor.getInstance().getContext(), "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
        }
    };

    private void setEventList() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
        for (int i = 0; i < dao.size(); i++) {
            eventList.add(new EventListItem(
                    dao.get(i).getId(),
                    dao.get(i).getName().getTh(),
                    DateUtil.getDateThai(dao.get(i).getStart()) + " \u2022 "
                            + dao.get(i).getStart().substring(11, 16)
                            + "-" + dao.get(i).getEnd().substring(11, 16),
                    sharedPref.getString(dao.get(i).getZone(), ""),
                    dao.get(i).getThumbnail()));
        }
        searchListAdapter.notifyDataSetChanged();
    }
}