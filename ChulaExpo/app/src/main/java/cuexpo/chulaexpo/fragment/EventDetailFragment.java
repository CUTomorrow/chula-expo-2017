package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.EventDetailListAdapter;
import cuexpo.chulaexpo.dao.ActivityItemDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.manager.HttpManager;
import cuexpo.chulaexpo.utility.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventDetailFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private View eventImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;
    private View listHeader;
    private TextView title;
    private Fragment fragment;
    private ActivityItemResultDao dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        fragment = this;
        listView = (ListView) rootView.findViewById(R.id.list_view);

        eventImageView = rootView.findViewById(R.id.event_image);
        headerView = (FrameLayout) rootView.findViewById(R.id.header);
        title = (TextView) rootView.findViewById(R.id.title);

        ImageView closeButton = (ImageView) rootView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeButtonOnClickListener);
        listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
        String id = activitySharedPref.getString("EventID", "");
        Call<ActivityItemDao> call = HttpManager.getInstance().getService().loadActivityItem(id);
        call.enqueue(callbackActivity);

        return rootView;
    }

    Callback<ActivityItemDao> callbackActivity = new Callback<ActivityItemDao>() {
        @Override
        public void onResponse(Call<ActivityItemDao> call, Response<ActivityItemDao> response) {
            if (response.isSuccessful()) {
                dao = response.body().getResults();
                Glide.with(fragment)
                        .load("http://staff.chulaexpo.com"+dao.getBanner())
                        .placeholder(R.color.blackOverlay)
                        .centerCrop()
                        .into((ImageView) eventImageView);
                title.setText(dao.getName().getTh());
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
        public void onFailure(Call<ActivityItemDao> call, Throwable t) {
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
                eventImageView.setY(topY * 0.5f);
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

            String strDate = dao.getStart();
            String endDaye = dao.getEnd();
            EventDetailListAdapter adapter = new EventDetailListAdapter(getActivity(), 0,
                    dao.getLocation().getPlace(),
                    dao.getContact(),
                    DateUtil.getDateRangeThai(strDate, endDaye) + " \u2022 " + strDate.substring(11,16) + "-" + endDaye.substring(11,16),
                    dao.getDescription().getTh(),
                    dao.getLocation().getLatitude(),
                    dao.getLocation().getLongitude(),
                    dao.getPictures()
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
