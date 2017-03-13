package cuexpo.cuexpo2017.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapterNew;
import cuexpo.cuexpo2017.adapter.TagPageAdapter;
import cuexpo.cuexpo2017.adapter.ZoneDetailListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagPageFragment extends Fragment {

    private ListView list;
    private TagPageAdapter adapter;
    private List<ActivityItemResultDao> activities;
    private ImageView backButton;
    protected int limit;

    public TagPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tag_page, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        list = (ListView) rootView.findViewById(R.id.list_view);
        adapter = new TagPageAdapter(getContext());
        list.setAdapter(adapter);
        list.setOnItemClickListener(itemOCL);

        backButton = (ImageView) rootView.findViewById(R.id.toolbar_back);
        backButton.setOnClickListener(backButtonOnclickListener);

//        ViewTreeObserver vto = list.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(onGlobalLayoutListener);

        limit = 10;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("TagDetail", Context.MODE_PRIVATE);
        String tagId = sharedPreferences.getString("titleENG", "");

        JSONObject range = new JSONObject();
        try {
            String startString = "2017-03-" + 15 + "T00:00:00.000Z";
            String endString = "2017-03-" + 20 + "T23:59:00.000Z";
            range.put("gte", startString);
            range.put("lte", endString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<ActivityItemCollectionDao> eventCall = HttpManager.getInstance().getService().loadActivityByTag(tagId, range.toString(), "start", limit);
        eventCall.enqueue(callbackEvent);
    }


    Callback<ActivityItemCollectionDao> callbackEvent = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                activities = response.body().getResults();
                adapter.setEvent(activities);
                adapter.notifyDataSetChanged();
            } else {
                try {
                    Log.e("fetch error", response.errorBody().string());
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

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

//            SharedPreferences sharedPreferences = getContext().getSharedPreferences("TagDetail", Context.MODE_PRIVATE);
//            String tagId = sharedPreferences.getString("titleENG", "");
//
//            JSONObject range = new JSONObject();
//            try {
//                String startString = "2017-03-" + 15 + "T00:00:00.000Z";
//                String endString = "2017-03-" + 20 + "T23:59:00.000Z";
//                range.put("gte", startString);
//                range.put("lte", endString);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Call<ActivityItemCollectionDao> eventCall = HttpManager.getInstance().getService().loadActivityByTag(tagId, range.toString(), "start", limit);
//            eventCall.enqueue(callbackEvent);

            list.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    };

    private AdapterView.OnItemClickListener itemOCL = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position >= 3) {
                String activityId = activities.get(position-3).getId();
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


    private View.OnClickListener backButtonOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

}
