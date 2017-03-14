package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.FavouriteListAdapter;
import cuexpo.cuexpo2017.adapter.ReservedListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.datatype.MutableInteger;
import cuexpo.cuexpo2017.manager.ActivityListManager;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.view.ExpandableHeightListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FavouriteFragment extends Fragment implements View.OnClickListener {

    private ImageView back;
    private ExpandableHeightListView upComingEventListView;
    private ExpandableHeightListView previousEventListView;
    private ActivityListManager photoListManager;
    private FavouriteListAdapter upComingAdapter;
    private FavouriteListAdapter previousAdapter;
    private MutableInteger lastPositionInteger;
    private ActivityItemCollectionDao dao2 = new ActivityItemCollectionDao();
    private ActivityItemCollectionDao dao3 = new ActivityItemCollectionDao();
    private int callBackSize;
    private int callBackCount = 0;

    private SharedPreferences sharedPref;

    public FavouriteFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        photoListManager = new ActivityListManager();
        lastPositionInteger = new MutableInteger(-1);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        upComingEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.favourite_content_container);
        previousEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.favourite_content_container2);
        back = (ImageView) rootView.findViewById(R.id.favourite_back);

        upComingAdapter = new FavouriteListAdapter();
        previousAdapter = new FavouriteListAdapter();

        upComingEventListView.setAdapter(upComingAdapter);
        upComingEventListView.setExpanded(true);
        upComingEventListView.setFocusable(false);
        upComingEventListView.setOnItemClickListener(lvEventItemClickListener);

        previousEventListView.setAdapter(previousAdapter);
        previousEventListView.setExpanded(true);
        previousEventListView.setFocusable(false);
        previousEventListView.setOnItemClickListener(lvEventItemClickListener2);

        upComingEventListView.setEnabled(false);
        previousEventListView.setEnabled(false);

        back.setOnClickListener(this);

        dao2.setResults(new ArrayList<ActivityItemResultDao>());
        dao3.setResults(new ArrayList<ActivityItemResultDao>());

        sharedPref = getContext().getSharedPreferences("favouriteActivity", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPref.getAll();

        if (allEntries.size() == 0) {
            upComingAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
            previousAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
            upComingAdapter.notifyDataSetChanged();
            previousAdapter.notifyDataSetChanged();
        } else {
            upComingAdapter.setHolder("Loading Data..");
            previousAdapter.setHolder("Loading Data..");
            callBackSize = allEntries.size();
            Log.e("Favourite Fragment", "CallBackSize = " + callBackSize);
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.e("Favourite List", entry.getKey());
                Call<ActivityItemDao> callActivity =
                        HttpManager.getInstance().getService().
                                loadActivityItem(entry.getKey());
                callActivity.enqueue(callbackActivity);
            }
        }
    }

    Callback<ActivityItemDao> callbackActivity = new Callback<ActivityItemDao>() {
        @Override
        public void onResponse(Call<ActivityItemDao> call, Response<ActivityItemDao> response) {
            if (response.isSuccessful()) {
                ActivityItemDao dao = response.body();
                callBackCount++;
                Log.e("Favourite Fragment", "CallBackCount = " + callBackCount);
                if (dao.getSuccess()) {
                    if (getCurrentTime().before(setCompareDate(dao.getResults().getStart()))) {
                        Log.e("Favourite Fragment", "Upcoming " + getCurrentTime().toString()
                                + " " + setCompareDate(dao.getResults().getStart()));
                        upComingAdapter.setIsZero(false);
                        upComingEventListView.setEnabled(true);
                        dao2.setSuccess(true);
                        dao2.addResults(dao.getResults());
                        upComingAdapter.setActivityDao(dao2);
                        upComingAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Favourite Fragment", "Previous " + getCurrentTime().toString()
                                + " " + setCompareDate(dao.getResults().getEnd()));
                        previousAdapter.setIsZero(false);
                        previousEventListView.setEnabled(true);
                        dao3.setSuccess(true);
                        dao3.addResults(dao.getResults());
                        previousAdapter.setActivityDao(dao3);
                        previousAdapter.notifyDataSetChanged();
                    }
                }
                if (callBackCount == callBackSize) {
                    callBackCount = 0;
                    if (upComingAdapter.getIsZero()) {
                        upComingAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
                        upComingAdapter.notifyDataSetChanged();
                    }
                    if (previousAdapter.getIsZero()) {
                        previousAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
                        previousAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                try {
                    Log.e("Favourite Fragment", "Call Activity Not Success " + response.errorBody().string());
                    callBackCount++;
                    if (upComingAdapter.getIsZero()) {
                        upComingAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
                        upComingAdapter.notifyDataSetChanged();
                    }
                    if (previousAdapter.getIsZero()) {
                        previousAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
                        previousAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ActivityItemDao> call, Throwable t) {
            Log.e("Favourite Fragment", "Call Activity Fail");
        }
    };

    private Date getCurrentTime() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return new Date();
    }

    private Date setCompareDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()); // Quoted "Z" to indicate UTC, no timezone offset
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    AdapterView.OnItemClickListener lvEventItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!upComingAdapter.getIsZero()) {
                upComingEventListView.setEnabled(true);
                String activityId = upComingAdapter.getItem(position).getId();
                SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
                activitySharedPref.edit().putString("EventID", activityId).apply();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.favourite_acitivity_content_container, new EventDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else upComingEventListView.setEnabled(false);
        }
    };

    AdapterView.OnItemClickListener lvEventItemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!previousAdapter.getIsZero()) {
                previousEventListView.setEnabled(true);
                String activityId = previousAdapter.getItem(position).getId();
                SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
                activitySharedPref.edit().putString("EventID", activityId).apply();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.favourite_acitivity_content_container, new EventDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else previousEventListView.setEnabled(false);
        }
    };

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
        if (v == back) {
            getActivity().finish();
        }
    }

}
