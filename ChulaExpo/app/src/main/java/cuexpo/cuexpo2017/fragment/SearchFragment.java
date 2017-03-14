package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.SearchListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.dao.ActivitySearchItemCollectionDao;
import cuexpo.cuexpo2017.dao.DeleteResultDao;
import cuexpo.cuexpo2017.dao.NearbyDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.manager.HttpManagerSpecial;
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
    private ImageView search;
    private EditText query;
    private String id;
    private boolean isSearching = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        query = ((EditText) rootView.findViewById(R.id.search));
        search = (ImageView) rootView.findViewById(R.id.search_icon_button);
        search.setOnClickListener(searchClickListener);
        rootView.findViewById(R.id.back).setOnClickListener(backOCL);

        searchListAdapter = new SearchListAdapter(eventList, false, getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(searchListAdapter);
        recyclerView.setAdapter(scaleAdapter);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FacebookInfo", Context.MODE_PRIVATE);
        id = sharedPref.getString("id", "");
        HttpManagerSpecial.getInstance().setAPIKey(sharedPref.getString("apiToken", ""));
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

    private View.OnClickListener searchClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = query.getText().toString();
            if (!isSearching) {
                isSearching = true;
                searchListAdapter.setSearching(true);
            }
            Call<ActivitySearchItemCollectionDao> callSearchActivities;
            Log.e("Search Fragment", "String : " + s);
            /*if (checkMap) {
                callSearchActivities = HttpManagerSpecial.getInstance().
                        getService().searchActivities(id, lat, lng, 300, s);
            } else {*/
            callSearchActivities =
                    HttpManagerSpecial.getInstance().
                            getService().searchActivities(s);
            //}
            callSearchActivities.enqueue(callbackSearch);
        }
    };

    Callback<ActivitySearchItemCollectionDao> callbackSearch = new Callback<ActivitySearchItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivitySearchItemCollectionDao> call, Response<ActivitySearchItemCollectionDao> response) {
            if (response.isSuccessful()) {
                dao = response.body().getActivities();
                Log.e("Search Fragment", "Search Finish " + response.body().getStatus().getStatus());
                Log.e("Search Fragment", "Search Finish " + response.body().getActivities().size());
                setEventList();
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "Cannot Search. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("Search Fragment", "Search Fail " + response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<ActivitySearchItemCollectionDao> call, Throwable t) {
            Log.e("Search Fragment", "Search Fail " + t.toString());
            Toast.makeText(Contextor.getInstance().getContext(), "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
        }
    };

    private void initEventList() {
        eventList.clear();
        Call<NearbyDao> callNearbyActivities =
                HttpManagerSpecial.getInstance().getService().loadNearbyActivities(13.73826, 100.53272);
        callNearbyActivities.enqueue(callbackNearbyActivities);
    }

   Callback<NearbyDao> callbackNearbyActivities = new Callback<NearbyDao>() {
        @Override
        public void onResponse(Call<NearbyDao> call, Response<NearbyDao> response) {
            if (response.isSuccessful()) {
                Log.e("Search Fragment", "LAT " + response.body().getLat());
                Log.e("Search Fragment", "LNG " + response.body().getLng());
                searchListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "Cannot Get Nearby Activities. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("Search Fragment", "Nearby Fail " + response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<NearbyDao> call, Throwable t) {
            Log.e("Search Fragment", "Nearby Fail " + t.toString());
            Toast.makeText(Contextor.getInstance().getContext(), "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
        }
    };

    private void setEventList() {
        eventList.clear();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
        searchListAdapter.notifyDataSetChanged();
        for (int i = 0; i < dao.size(); i++) {
            eventList.add(new EventListItem(
                    dao.get(i).getId(),
                    dao.get(i).getName().getTh(),
                    DateUtil.getDateThai(dao.get(i).getStart()) + " \u2022 "
                            + dao.get(i).getStart().substring(11, 16)
                            + "-" + dao.get(i).getEnd().substring(11, 16),
                    sharedPref.getString(dao.get(i).getZone(), "")));
        }
        searchListAdapter.notifyDataSetChanged();
    }

}
