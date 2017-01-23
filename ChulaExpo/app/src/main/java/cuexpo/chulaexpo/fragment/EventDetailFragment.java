package cuexpo.chulaexpo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.EventDetailListAdapter;


public class EventDetailFragment extends Fragment {

    private static final int MAX_ROWS = 100;
    View rootView;
    private ListView listView;
    private View eventImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        eventImageView = rootView.findViewById(R.id.event_image);
        headerView = (FrameLayout) rootView.findViewById(R.id.header);

        View listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        listView.addHeaderView(listHeader);
        listView.setOnScrollListener(onScrollListener);

        EventDetailListAdapter adapter = new EventDetailListAdapter(getActivity(), 0);
        listView.setAdapter(adapter);

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

}
