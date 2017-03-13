package cuexpo.cuexpo2017.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapterNew;
import cuexpo.cuexpo2017.adapter.TagPageAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagPageFragment extends Fragment {

    private ListView list;
    private TagPageAdapter adapter;
    private List<ActivityItemResultDao> eventList;

    public TagPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tag_page, container, false);
        initInstances(rootView, savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("TagDetail", Context.MODE_PRIVATE);
        String tag = sharedPreferences.getString("tagEnName","");

        Call<ActivityItemCollectionDao> call = HttpManager.getInstance().getService().loadActivityByTags(tag);
        call.enqueue(callbackActivity);

        return rootView;
    }

    Callback<ActivityItemCollectionDao> callbackActivity = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                eventList = response.body().getResults();
                Log.e("Tag Fragment","Successful with Size of " + eventList.size());
                adapter.setEvent(eventList);
                adapter.notifyDataSetChanged();
            } else {
                try {
                    Log.e("Tag Fragment", response.errorBody().string());
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onFailure(Call<ActivityItemCollectionDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private AdapterView.OnItemClickListener itemOCL = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position >= 3) {
                String activityId = eventList.get(position-3).getId();
                SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
                activitySharedPref.edit().putString("EventID", activityId).apply();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, new EventDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    };


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        list = (ListView) rootView.findViewById(R.id.list_view);
        adapter = new TagPageAdapter(getContext());
        list.setAdapter(adapter);
        list.setOnItemClickListener(itemOCL);

    }

}
