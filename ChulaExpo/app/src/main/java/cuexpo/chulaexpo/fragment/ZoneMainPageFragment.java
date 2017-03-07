package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.EventDetailListAdapter;
import cuexpo.chulaexpo.adapter.ZoneDetailListAdapter;
import cuexpo.chulaexpo.dao.ActivityItemDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.dao.ZoneDao;
import cuexpo.chulaexpo.dao.ZoneResult;
import cuexpo.chulaexpo.manager.HttpManager;
import cuexpo.chulaexpo.utility.DateUtil;
import cuexpo.chulaexpo.utility.Resource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZoneMainPageFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private ListView relatedListView;
    private FrameLayout relatedHeader;
    private ImageView zoneImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;
    private View listHeader;
    private TextView title;
    private Fragment fragment;
    private ZoneResult dao;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_zone_main_page, container, false);
        fragment = this;
        listView = (ListView) rootView.findViewById(R.id.list_view);

        zoneImageView = (ImageView) rootView.findViewById(R.id.zone_image);
        headerView = (FrameLayout) rootView.findViewById(R.id.header);
        title = (TextView) rootView.findViewById(R.id.title);

        relatedListView = (ListView) rootView.findViewById(R.id.related_list_view);
        relatedHeader = (FrameLayout) rootView.findViewById(R.id.related_header);

        ImageView closeButton = (ImageView) rootView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeButtonOnClickListener);
        listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Zone", Context.MODE_PRIVATE);
        String id = activitySharedPref.getString("ZoneID", "");

        String zone = activitySharedPref.getString("Zone", "");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
        String zoneShortName = sharedPref.getString(zone, "");
        TextView zoneTag = (TextView) rootView.findViewById(R.id.zone_tag);
        zoneTag.setText(zoneShortName);
        zoneTag.setBackgroundResource(Resource.getColor(zoneShortName));
        for(int i=0;i<lightZone.length-1;i++){
            if(zoneShortName.equals(lightZone[i])) {
                zoneTag.setTextColor(Color.BLACK);
                break;
            }
        }

        Call<ZoneResult> call = HttpManager.getInstance().getService().loadZoneById("5899a98a5eeecd3698f6cfc6");
        call.enqueue(callbackActivity);

//        Call<RoundDao> roundCall = HttpManager.getInstance().getService().loadRoundsById(id);
//        call.enqueue(callbackActivity);

        return rootView;
    }



    Callback<ZoneResult> callbackActivity = new Callback<ZoneResult>() {
        @Override
        public void onResponse(Call<ZoneResult> call, Response<ZoneResult> response) {
            if (response.isSuccessful()) {
                dao = response.body();
//                Glide.with(fragment)
//                        .load("http://staff.chulaexpo.com"+dao.getBanner())
//                        .placeholder(R.color.blackOverlay)
//                        .centerCrop()
//                        .into(zoneImageView);
//                Log.d("zone", ""+dao.getName());
//                title.setText(dao.getName().getTh());
                zoneImageView.setImageResource(R.drawable.faculty_1);
                title.setText("Faculty of engineering");
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
        public void onFailure(Call<ZoneResult> call, Throwable t) {
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

            // TODO: reset adapter parameter ;
//            ZoneDetailListAdapter adapter = new ZoneDetailListAdapter(getActivity(), dao.getId(),
//                    dao.getType(),
//                    dao.getWebsite(),
//                    dao.getDescription().getTh(),
//                    dao.getLocation().getLatitude(),
//                    dao.getLocation().getLongitude()
//            );
            ZoneDetailListAdapter adapter = new ZoneDetailListAdapter(getActivity(), dao.getId(),
                    dao.getType(),
                    dao.getWebsite(),
                    "this is คณะวิศวะ" + "\n" + "fjkewofwoe" + "\n" + "fjkewofwoe" + "\n" + "fjkewofwoe" +"\n" + "fjkewofwoe",
                    0.0,
                    0.0
            );
            listView.setAdapter(adapter);

            headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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
