package cuexpo.chulaexpo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import com.bumptech.glide.Glide;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.EventDetailListAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventDetailActivity extends AppCompatActivity {

    private ListView listView;
    private View eventImageView;
    private FrameLayout headerView;
    private View stickyViewSpacer;
    private View listHeader;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        activity = this;

        listView = (ListView) findViewById(R.id.list_view);
        eventImageView = findViewById(R.id.event_image);
        Glide.with(this)
                .load("http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg")
                .placeholder(R.color.blackOverlay)
                .centerCrop()
                .into((ImageView) eventImageView);
        headerView = (FrameLayout) findViewById(R.id.header);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("การแสดงสาธิต หุ่นยนต์ดูดฝุ่น");

        ImageView closeButton = (ImageView) findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeButtonOnClickListener);

        LayoutInflater inflater = getLayoutInflater();
        listHeader = inflater.inflate(R.layout.item_event_detail_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.sticky_view_placeholder);

        ViewTreeObserver vto = headerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(onGlobalLayoutListener);
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

            EventDetailListAdapter adapter = new EventDetailListAdapter(activity, 0);
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
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
