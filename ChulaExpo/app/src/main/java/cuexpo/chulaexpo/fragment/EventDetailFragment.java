package cuexpo.chulaexpo.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.EventDetailListAdapter;


public class EventDetailFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private View eventImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;
    private View listHeader;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        fragment = this;
        listView = (ListView) rootView.findViewById(R.id.list_view);
        eventImageView = rootView.findViewById(R.id.event_image);
        headerView = (FrameLayout) rootView.findViewById(R.id.header);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        ImageView closeButton = (ImageView) rootView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeButtonOnClickListener);
        listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        Glide.with(this)
                .load("http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg")
                .placeholder(R.color.blackOverlay)
                .centerCrop()
                .into((ImageView) eventImageView);


        title.setText("การแสดงสาธิต หุ่นยนต์ดูดฝุ่น");


        ViewTreeObserver vto = headerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(onGlobalLayoutListener);

        return rootView;
    }

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

            EventDetailListAdapter adapter = new EventDetailListAdapter(getActivity(), 0);
            listView.setAdapter(adapter);

            headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    };

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener closeButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };
}
