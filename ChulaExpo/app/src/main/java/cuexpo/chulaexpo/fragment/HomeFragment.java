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
import android.widget.ListView;
import android.widget.TextView;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.ActivityListAdapter;
import cuexpo.chulaexpo.adapter.HighlightListAdapter;
import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.manager.PhotoListManager;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ListView listView;
    ActivityListAdapter listAdapter;
    HighlightListAdapter highlightListAdapter;
    PhotoListManager photoListManager;
    MutableInteger lastPositionInteger;
    ViewPager vpHighlight;
    TextView tvHighlightLabel,tvHighlightTime;

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
        Log.e("HOMEFRAG", "IMdestroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        initInstances(rootView,savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        //can save state
        photoListManager = new PhotoListManager();
        lastPositionInteger = new MutableInteger(-1);
    }


    private void initInstances(View rootView,Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //Cannot save state
        toolbar = (Toolbar)rootView.findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        vpHighlight = (ViewPager)rootView.findViewById(R.id.vpHighlight);
        tvHighlightLabel = (TextView)rootView.findViewById(R.id.tvHighlightLabel);
        tvHighlightTime = (TextView)rootView.findViewById(R.id.tvHighlightTime);
        highlightListAdapter = new HighlightListAdapter();
        vpHighlight.setAdapter(highlightListAdapter);

        listView = (ListView)rootView.findViewById(R.id.lvActivity);
        listAdapter = new ActivityListAdapter(lastPositionInteger);
        listAdapter.setDao(photoListManager.getDao());
        listView.setAdapter(listAdapter);


        /* Fetch Data From Server
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                if(response.isSuccessful()){
                    PhotoItemCollectionDao cuexpo.chulaexpo.dao = response.body();
                    listAdapter.setDao(cuexpo.chulaexpo.dao);
                     listAdapter.notifyDataSetChanged();
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
