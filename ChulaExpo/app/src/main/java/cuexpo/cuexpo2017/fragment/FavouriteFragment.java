package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

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
        previousEventListView.setOnItemClickListener(lvEventItemClickListener);

        back.setOnClickListener(this);

        dao2.setResults(new ArrayList<ActivityItemResultDao>());
        dao3.setResults(new ArrayList<ActivityItemResultDao>());

        sharedPref = getContext().getSharedPreferences("favouriteActivity", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        Map<String, ?> allEntries = sharedPref.getAll();

        if(allEntries.size()==0){
            upComingAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
            previousAdapter.setHolder("ไม่มี Event ที่กำลังจะเกิดขึ้น");
        } else {
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
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
                System.out.println(dao.getResults().getId());
                upComingAdapter.setIsZero(false);
                /*for (int i = 0; i < reserveUpcomingDao.getResults().size(); i++) {
                    if (dao.getResults().getId().equals(reserveUpcomingDao.getResults().get(i).getActivityId())) {
                        dao.getResults().setStart(reserveUpcomingDao.getResults().get(i).getStart());
                        dao.getResults().setEnd(reserveUpcomingDao.getResults().get(i).getEnd());
                        break;
                    }
                }*/
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

    public JSONObject getCurrentTime(String operator) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String currentTime = df.format(new Date());
        System.out.println("NOW " + currentTime);
        JSONObject range = new JSONObject();
        try {
            range.put(operator, currentTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return range;
    }

    AdapterView.OnItemClickListener lvEventItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String activityId = upComingAdapter.getItem(position).getId();
            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("EventID", activityId).apply();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.favourite_acitivity_content_container, new EventDetailFragment());
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
