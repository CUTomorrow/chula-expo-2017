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
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.ActivityListAdapter;
import cuexpo.cuexpo2017.adapter.ReservedListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.dao.RoundResult;
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
public class ReservedFragment extends Fragment implements View.OnClickListener {

    private ImageView back;
    private ExpandableHeightListView upComingEventListView;
    private ExpandableHeightListView previousEventListView;
    private ActivityListManager photoListManager;
    private ReservedListAdapter upComingAdapter;
    private ReservedListAdapter previousAdapter;
    private MutableInteger lastPositionInteger;
    private ActivityItemCollectionDao dao2 = new ActivityItemCollectionDao();
    private ActivityItemCollectionDao dao3 = new ActivityItemCollectionDao();

    public ReservedFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ReservedFragment newInstance() {
        ReservedFragment fragment = new ReservedFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_reserved, container, false);
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
        upComingEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.reserved_content_container);
        previousEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.reserved_content_container2);
        back = (ImageView) rootView.findViewById(R.id.reserved_back);

        upComingAdapter = new ReservedListAdapter();
        previousAdapter = new ReservedListAdapter();

        upComingEventListView.setAdapter(upComingAdapter);
        upComingEventListView.setExpanded(true);
        upComingEventListView.setFocusable(false);
        upComingEventListView.setOnItemClickListener(lvEventItemClickListener);

        previousEventListView.setAdapter(previousAdapter);
        previousEventListView.setExpanded(true);
        previousEventListView.setFocusable(false);
        previousEventListView.setOnItemClickListener(lvEventItemClickListener2);

        back.setOnClickListener(this);

        dao2.setResults(new ArrayList<ActivityItemResultDao>());
        dao3.setResults(new ArrayList<ActivityItemResultDao>());

        Call<RoundDao> callUpcomingReservedList = HttpManager.getInstance().getService().loadReservedRounds(getCurrentTime("gte"));
        callUpcomingReservedList.enqueue(callbackReservedList);

        Call<RoundDao> callPreviousReservedList = HttpManager.getInstance().getService().loadReservedRounds(getCurrentTime("lt"));
        callPreviousReservedList.enqueue(callbackReservedList2);
    }

    public JSONObject getCurrentTime(String operator) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String currentTime = df.format(new Date());
        JSONObject range = new JSONObject();
        try {
            range.put(operator, currentTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return range;
    }

    Callback<RoundDao> callbackReservedList = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                RoundDao dao = response.body();
                upComingAdapter.setRoundDao(dao);
                upComingAdapter.notifyDataSetChanged();

                for (int i = 0; i < dao.getResults().size(); i++) {
                    System.out.println("number " + i + " " + dao.getResults().get(i).getId() + " " + dao.getResults().get(i).getActivityId());
                    Call<ActivityItemDao> callActivity =
                            HttpManager.getInstance().getService().
                                    loadActivityItem(dao.getResults().get(i).getActivityId());
                    callActivity.enqueue(callbackActivity);
                }
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
    };

    Callback<RoundDao> callbackReservedList2 = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                RoundDao dao = response.body();
                previousAdapter.setRoundDao(dao);
                previousAdapter.notifyDataSetChanged();

                for (int i = 0; i < dao.getResults().size(); i++) {
                    System.out.println("number " + i + " " + dao.getResults().get(i).getId() + " " + dao.getResults().get(i).getActivityId());
                    Call<ActivityItemDao> callActivity =
                            HttpManager.getInstance().getService().
                                    loadActivityItem(dao.getResults().get(i).getActivityId());
                    callActivity.enqueue(callbackActivity2);
                }
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
    };

    Callback<ActivityItemDao> callbackActivity = new Callback<ActivityItemDao>() {
        @Override
        public void onResponse(Call<ActivityItemDao> call, Response<ActivityItemDao> response) {
            if (response.isSuccessful()) {
                ActivityItemDao dao = response.body();
                dao2.setSuccess(true);
                dao2.addResults(dao.getResults());
                upComingAdapter.setActivityDao(dao2);
                upComingAdapter.notifyDataSetChanged();
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ActivityItemDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
    };

    Callback<ActivityItemDao> callbackActivity2 = new Callback<ActivityItemDao>() {
        @Override
        public void onResponse(Call<ActivityItemDao> call, Response<ActivityItemDao> response) {
            if (response.isSuccessful()) {
                ActivityItemDao dao = response.body();
                dao3.setSuccess(true);
                dao3.addResults(dao.getResults());
                previousAdapter.setActivityDao(dao3);
                previousAdapter.notifyDataSetChanged();
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ActivityItemDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
    };

    AdapterView.OnItemClickListener lvEventItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("CICKED LISTENER " + position);
            String activityId = upComingAdapter.getItem(position).getId();
            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("EventID", activityId).apply();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.reserved_acitivity_content_container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    AdapterView.OnItemClickListener lvEventItemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("CICKED LISTENER " + position);
            String activityId = previousAdapter.getItem(position).getId();
            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("EventID", activityId).apply();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.reserved_acitivity_content_container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
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
