package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.HighlightPhotoListAdapter;
import cuexpo.chulaexpo.manager.HttpManager;
import cuexpo.chulaexpo.manager.PhotoListManager;
import dao.PhotoItemCollectionDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ListView listView;
    HighlightPhotoListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initInstances(rootView);
        return rootView;
    }


    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        toolbar = (Toolbar)rootView.findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        listView = (ListView)rootView.findViewById(R.id.lvHighlight);
        listAdapter = new HighlightPhotoListAdapter();
        listView.setAdapter(listAdapter);

        /* Fetch Data From Server
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                if(response.isSuccessful()){
                    PhotoItemCollectionDao dao = response.body();
                    listAdapter.setDao(dao);
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
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

}
