package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.ZoneDetailListAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.dao.ZoneItemDao;
import cuexpo.cuexpo2017.dao.ZoneResult;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.Resource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZoneMainPageFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private ImageView zoneImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;
    private View listHeader;
    private TextView title;
    private Fragment fragment;
    private ZoneResult dao;
    private String zoneId;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    private ZoneDetailListAdapter adapter;
    private int facultyId = 1;
    List<ActivityItemResultDao> activities = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_zone_main_page, container, false);
        fragment = this;
        listView = (ListView) rootView.findViewById(R.id.list_view);

        zoneImageView = (ImageView) rootView.findViewById(R.id.zone_image);
        headerView = (FrameLayout) rootView.findViewById(R.id.header);
        title = (TextView) rootView.findViewById(R.id.title);

        ImageView closeButton = (ImageView) rootView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeButtonOnClickListener);
        listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        SharedPreferences zoneNameSharedPref = getActivity().getSharedPreferences("Zone", Context.MODE_PRIVATE);
        String zoneName = zoneNameSharedPref.getString("ZoneName", "");

        SharedPreferences reverseZoneKeySharedPref = getActivity().getSharedPreferences("ReverseZoneKey", Context.MODE_PRIVATE);
        zoneId = reverseZoneKeySharedPref.getString(zoneName, "");

        Call<ZoneItemDao> call = HttpManager.getInstance().getService().loadZoneById(zoneId);
        call.enqueue(callbackActivity);

        return rootView;
    }

    Callback<ActivityItemCollectionDao> callbackEvent = new Callback<ActivityItemCollectionDao>() {
        @Override
        public void onResponse(Call<ActivityItemCollectionDao> call, Response<ActivityItemCollectionDao> response) {
            if (response.isSuccessful()) {
                activities = response.body().getResults();
                adapter.setEventList(activities);
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

    Callback<ZoneItemDao> callbackActivity = new Callback<ZoneItemDao>() {
        @Override
        public void onResponse(Call<ZoneItemDao> call, Response<ZoneItemDao> response) {
            if (response.isSuccessful()) {
                dao = response.body().getResults();
                Glide.with(fragment)
                        .load("https://api.chulaexpo.com"+dao.getBanner())
                        .placeholder(R.drawable.banner)
                        .centerCrop()
                        .into(zoneImageView);
                title.setText(dao.getName().getTh());

                TextView zoneTag = (TextView) rootView.findViewById(R.id.zone_tag);
                String zoneShortName = dao.getShortName().getEn();
                zoneTag.setText(zoneShortName);
                zoneTag.setBackgroundResource(Resource.getColor(zoneShortName));
                for(int i=0;i<lightZone.length-1;i++){
                    if(zoneShortName.equals(lightZone[i])) {
                        zoneTag.setTextColor(Color.BLACK);
                        break;
                    }
                }

                ViewTreeObserver vto = headerView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(onGlobalLayoutListener);
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
        public void onFailure(Call<ZoneItemDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (listView.getFirstVisiblePosition() == 0) {
                View firstChild = listView.getChildAt(0);
                int topY = 0;
                if (firstChild != null) {
                    topY = firstChild.getTop();
                }

                int imageTopY = stickyViewSpacer.getTop();
                Log.d("scroll", "im top = " + imageTopY + " : top y = " + topY);
                headerView.setY(Math.max(0, imageTopY + topY));
                zoneImageView.setY(topY * 0.5f);
            }
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            LinearLayout.LayoutParams stickyViewSpacerLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    headerView.getHeight() - dpToPx(7));
            stickyViewSpacer.setLayoutParams(stickyViewSpacerLayoutParams);

            listView.addHeaderView(listHeader);
            listView.setOnScrollListener(onScrollListener);
            listView.setOnItemClickListener(itemOCL);

            SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Zone", Context.MODE_PRIVATE);
            facultyId = activitySharedPref.getInt("FacultyId", 1);
            adapter = new ZoneDetailListAdapter(
                    fragment,
                    getActivity(),
                    facultyId,
                    dao.getId(),
                    dao.getType(),
                    dao.getWebsite(),
                    dao.getDescription().getTh(),
                    dao.getLocation().getLatitude(),
                    dao.getLocation().getLongitude()
            );
            listView.setAdapter(adapter);

            JSONObject range = new JSONObject();
            try {
                String startString = "2017-03-" + 15 + "T00:00:00.000Z";
                String endString = "2017-03-" + 15 + "T23:59:00.000Z";
                range.put("gte", startString);
                range.put("lte", endString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Call<ActivityItemCollectionDao> eventCall = HttpManager.getInstance().getService().loadActivityByZone(zoneId, range.toString(), "start");
            eventCall.enqueue(callbackEvent);

            headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener closeButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

}
