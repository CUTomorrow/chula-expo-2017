package cuexpo.chulaexpo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.ActivityListAdapter;
import cuexpo.chulaexpo.adapter.HighlightListAdapter;
import cuexpo.chulaexpo.adapter.StageListAdapter;
import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.manager.PhotoListManager;
import cuexpo.chulaexpo.view.HeaderView;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ListView lvActivity, lvStage;
    ActivityListAdapter activityListAdapter;
    HighlightListAdapter highlightListAdapter;
    StageListAdapter stageListAdapter;
    PhotoListManager photoListManager;
    MutableInteger lastPositionInteger;
    ViewPager vpHighlight;
    TextView tvHighlightLabel,tvHighlightTime;
    View activityHeaderView, stageHeaderView, highlightView, stageView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Initialize Fragment level's variables
        init(savedInstanceState);


        if(savedInstanceState != null){
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
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        activityHeaderView =  new HeaderView(getContext(),"ACTIVITY");
        stageHeaderView = new HeaderView(getContext(),"ON STAGE");
        highlightView =  inflater.inflate(R.layout.viewpager_highlight, container, false);
        stageView = inflater.inflate(R.layout.listview_stage,container,false);
        initInstances(rootView,savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        //can save state
        photoListManager = new PhotoListManager();
        lastPositionInteger = new MutableInteger(-1);
    }


    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //Cannot save state
        toolbar = (Toolbar)rootView.findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

//        layoutActivityHeader = (RelativeLayout) rootView.findViewById(R.id.layoutActivityHeader);

        vpHighlight = (ViewPager)highlightView.findViewById(R.id.vpHighlight);
        tvHighlightLabel = (TextView)highlightView.findViewById(R.id.tvHighlightLabel);
        tvHighlightTime = (TextView)highlightView.findViewById(R.id.tvHighlightTime);
        highlightListAdapter = new HighlightListAdapter();
        vpHighlight.setAdapter(highlightListAdapter);

        lvActivity = (ListView)rootView.findViewById(R.id.lvActivity);
        activityListAdapter = new ActivityListAdapter(lastPositionInteger);
        activityListAdapter.setDao(photoListManager.getDao());
        lvActivity.setAdapter(activityListAdapter);

        lvStage = (ListView) stageView.findViewById(R.id.lvStage);
        stageListAdapter = new StageListAdapter();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lvStage.getLayoutParams();
        lp.height = stageListAdapter.getCount()*75*2 + lvStage.getDividerHeight()*(stageListAdapter.getCount()-1);
        lvStage.setLayoutParams(lp);
        lvStage.setAdapter(stageListAdapter);

        lvActivity.addHeaderView(highlightView);
        lvActivity.addHeaderView(stageHeaderView);
        lvActivity.addHeaderView(stageView);
        lvActivity.addHeaderView(activityHeaderView);


//        activityListAdapter.notifyDataSetChanged();
        //lvActivity.addHeaderView(vpHighlight);


        /* Fetch Data From Server
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                if(response.isSuccessful()){
                    PhotoItemCollectionDao cuexpo.chulaexpo.dao = response.body();
                    activityListAdapter.setDao(cuexpo.chulaexpo.dao);
                     activityListAdapter.notifyDataSetChanged();
                } else {
                    //response but not success
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
                //Cannot connect to server
                try {
                    Toast.makeText(Contextor.getInstance().getContext(),
                            t.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        */
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
        outState.putBundle("photoListManager",photoListManager.onSavedInstanceState());
        outState.putBundle("lastPositionInteger",lastPositionInteger.onSavedInstanceState());
    }

    private  void onRestoreInstanceState(Bundle savedInstanceState){
        //Restore instance state here
        photoListManager.onRestoreInstanceState(savedInstanceState.getBundle("photoListManager"));
        lastPositionInteger.onRestoreInstanceState(savedInstanceState.getBundle("lastPositionInteger"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
