package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.StageActivity;
import cuexpo.cuexpo2017.adapter.ActivityListAdapter;
import cuexpo.cuexpo2017.adapter.HighlightListAdapter;
import cuexpo.cuexpo2017.adapter.HomeStageListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ZoneDao;
import cuexpo.cuexpo2017.dao.ZoneResult;
import cuexpo.cuexpo2017.datatype.MutableInteger;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.manager.ActivityListManager;
import cuexpo.cuexpo2017.view.ExpandableHeightListView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Toolbar toolbar;
    ExpandableHeightListView lvActivity, lvStage;
    ActivityListAdapter activityListAdapter;
    HighlightListAdapter highlightListAdapter;
    HomeStageListAdapter homeStageListAdapter;
    ActivityListManager photoListManager;
    MutableInteger lastPositionInteger;
    ViewPager vpHighlight;
    TextView tvHighlightLabel;
    CircleIndicator indicatorHighlight;
    ImageView ivToolbarQR, ivVPHighlight;
    View rootView;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Vector<String> stageObjId;
    boolean firstStage;
    String apiToken;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize Fragment level's variables
        init(savedInstanceState);


        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);  // restore Instance State
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        //can save state
        photoListManager = new ActivityListManager();
        lastPositionInteger = new MutableInteger(-1);
        stageObjId = new Vector<String>();
    }


    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //Cannot save state
        toolbar = (Toolbar) rootView.findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        ivToolbarQR = (ImageView) rootView.findViewById(R.id.home_toolbar_qr);
        ivToolbarQR.setOnClickListener(this);
        ivVPHighlight = (ImageView) rootView.findViewById(R.id.ivHighlight);
        //ivVPHighlight.setOnClickListener(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        vpHighlight = (ViewPager) rootView.findViewById(R.id.vpHighlight);
        indicatorHighlight = (CircleIndicator) rootView.findViewById(R.id.indicatorHighlight);
        tvHighlightLabel = (TextView) rootView.findViewById(R.id.tvHighlightLabel);
        highlightListAdapter = new HighlightListAdapter(this);
        vpHighlight.setAdapter(highlightListAdapter);
        vpHighlight.setFocusable(false);
        indicatorHighlight.setViewPager(vpHighlight);
        highlightListAdapter.registerDataSetObserver(indicatorHighlight.getDataSetObserver());

        lvStage = (ExpandableHeightListView) rootView.findViewById(R.id.lvStage);
        lvStage.setExpanded(true);
        lvStage.setFocusable(false);
        homeStageListAdapter = new HomeStageListAdapter();
        lvStage.setAdapter(homeStageListAdapter);
        lvStage.setOnItemClickListener(lvStageItemClickListener);

        lvActivity = (ExpandableHeightListView) rootView.findViewById(R.id.lvActivity);
        activityListAdapter = new ActivityListAdapter(lastPositionInteger);
        activityListAdapter.setDao(photoListManager.getDao());
        lvActivity.setAdapter(activityListAdapter);
        lvActivity.setExpanded(true);
        lvActivity.setFocusable(false);
        lvActivity.setOnItemClickListener(lvEventItemClickListener);

//        activityListAdapter.notifyDataSetChanged();
        //lvActivity.addHeaderView(vpHighlight);


        //Fetch Data From Server
        sharedPref = getActivity().getSharedPreferences("FacebookInfo", Context.MODE_PRIVATE);
        apiToken = sharedPref.getString("apiToken", "");
        Call<ActivityItemCollectionDao> call;
        if(!apiToken.equals("")){
            call = HttpManager.getInstance().getService()
                    .loadRecommendedActivityList(apiToken);
        } else {
            call = HttpManager.getInstance().getService()
                .loadActivityList("name,thumbnail,start,end,zone", 20, "start");
        }
        call.enqueue(callbackActivity);
        Call<ZoneDao> callZone = HttpManager.getInstance().getService().loadZoneList("name_EN");
        callZone.enqueue(callbackZone);
        Call<ActivityItemCollectionDao> callHighlight = HttpManager.getInstance().getService().loadHighlightActivity(true,
                "banner,name,shortDescription", getCurrentTime("gte"), 6);
        callHighlight.enqueue(callbackHighlight);
    }

    /**************
     * Callback API
     *************/
    Callback<ActivityItemCollectionDao> callbackActivity = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                ActivityItemCollectionDao dao = response.body();
                activityListAdapter.setDao(dao);
                activityListAdapter.notifyDataSetChanged();
                //Toast.makeText(Contextor.getInstance().getContext(), dao.getResults().get(0).getName().getEn(), Toast.LENGTH_SHORT).show();
            } else {
                //Handle
                Log.e("HomeActivity", "Load Activities Not Success");
                activityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Log.e("HomeActivity", "Load Activities Fail " + t.toString());
            activityListAdapter.notifyDataSetChanged();
        }
    };

    Callback<ZoneDao> callbackZone = new Callback<ZoneDao>() {
        @Override
        public void onResponse(Call<ZoneDao> call, Response<ZoneDao> response) {
            if (response.isSuccessful()) {
                ZoneDao dao = response.body();
                //get SharedPref
                sharedPref = getActivity().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                SharedPreferences reverseZoneKeySharedPref = getActivity().getSharedPreferences("ReverseZoneKey", Context.MODE_PRIVATE);
                SharedPreferences.Editor reverseZoneKeyEditor = reverseZoneKeySharedPref.edit();
                for (int i = 0; i < dao.getResults().size(); i++) {
                    ZoneResult zone = dao.getResults().get(i);
                    editor.putString(zone.getId(), zone.getShortName().getEn());
                    reverseZoneKeyEditor.putString(zone.getShortName().getEn(), zone.getId());
                    if (zone.getType().equals("Stage")) {
                        Log.d("StageHome", zone.getId());
                        stageObjId.add(zone.getId());
                        editor.putString(zone.getId(), zone.getShortName().getTh());
                    }
                }
                Log.d("StageHome", "stage size = " + stageObjId.size());
                editor.commit();
                reverseZoneKeyEditor.commit();
                for(int k=0; k<stageObjId.size(); k++){
                    if(k==0) firstStage = true;
                    Call<ActivityItemCollectionDao> callActivityOfStage = HttpManager.getInstance().getService()
                            .loadIncomingActivityOnStage(stageObjId.get(k), "name,start,end,zone", getCurrentTime("gte"), "start", 1);
                    callActivityOfStage.enqueue(callbackStageObjId);
                }
            } else {
                //Handle

                try {
                    Log.e("StageHome", response.errorBody().string());
                    //Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ZoneDao> call, Throwable t) {
            Log.e("HomeZone", t.toString());
            //Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ActivityItemCollectionDao> callbackStageObjId = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                ActivityItemCollectionDao dao = response.body();
                if (firstStage) {
                    homeStageListAdapter.setDao(dao);
                    firstStage = false;
                } else homeStageListAdapter.addDao(dao.getResults().get(0));
                Log.d("StageHome", dao + "");
                homeStageListAdapter.notifyDataSetChanged();
            } else {
                //Handle
                Log.e("StageHome", "Not Success");
            }
        }

        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Log.e("StageHome", "HomeStage Failure");
        }
    };

    Callback<ActivityItemCollectionDao> callbackHighlight = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                ActivityItemCollectionDao dao = response.body();
                highlightListAdapter.setDao(dao);
                highlightListAdapter.notifyDataSetChanged();
                getView().findViewById(R.id.layoutStageHeader).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.home_loading_txt).setVisibility(View.GONE);
            } else {
                try {
                    Log.e("HomeHighlight", "Load Highlight Not Success\n" + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Log.e("HomeHighlight", t.toString());
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
        outState.putBundle("photoListManager", photoListManager.onSavedInstanceState());
        outState.putBundle("lastPositionInteger", lastPositionInteger.onSavedInstanceState());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        //Restore instance state here
        photoListManager.onRestoreInstanceState(savedInstanceState.getBundle("photoListManager"));
        lastPositionInteger.onRestoreInstanceState(savedInstanceState.getBundle("lastPositionInteger"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**************
     * Listener
     *************/
    AdapterView.OnItemClickListener lvStageItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), StageActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putInt("stageNo", position + 1);
            mBundle.putString("stageId", homeStageListAdapter.getDao().getResults().get(position).getZone());
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    };

    AdapterView.OnItemClickListener lvEventItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String activityId = activityListAdapter.getItem(position).getId();
            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("EventID", activityId).apply();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    @Override
    public void onClick(View v) {
        if(v == ivToolbarQR){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new QRFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        /*
        else if(v == ivVPHighlight){
            String highlightActivityId = highlightListAdapter.getItemPosition(vpHighlight.getCurrentItem())+"" ;
            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("HighlightEventID", activityId).apply();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        */

    }

    public JSONObject getCurrentTime(String operator) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String currentTime = df.format(new Date());
        Log.d("HomeTime", "CurrentTime = " + currentTime);
        JSONObject range = new JSONObject();
            try {
            range.put(operator, currentTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return range;
    }
}


