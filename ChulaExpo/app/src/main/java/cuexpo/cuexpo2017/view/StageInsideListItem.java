package cuexpo.cuexpo2017.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StageInsideListItem extends BaseCustomViewGroup implements View.OnClickListener {

    private TextView tvDescription;
    //private TextView tvFavourite;
    //private TextView tvStar;
    private ImageView ivLine;
    private View vBottomDivider;
    private LinearLayout btnView;
    //private LinearLayout btnFavourite;
    private String id;


    public StageInsideListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public StageInsideListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public StageInsideListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public StageInsideListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_stage_inside, this);
    }

    private void initInstances() {
        // findViewById here
        tvDescription = (TextView) findViewById(R.id.stage_inside_tv_description);
        //tvFavourite = (TextView) findViewById(R.id.stage_inside_tv_favourite);
        //tvStar = (TextView) findViewById(R.id.stage_inside_tv_star);
        vBottomDivider = findViewById(R.id.stage_inside_bottom_divider);
        ivLine = (ImageView) findViewById(R.id.stage_inside_iv_line);
        btnView = (LinearLayout) findViewById(R.id.stage_inside_btn_info);
        //btnFavourite = (LinearLayout) findViewById(R.id.stage_inside_btn_favourite);
        btnView.setOnClickListener(this);
        //btnFavourite.setOnClickListener(this);
        /*btnView.setOnTouchListener(this);
        btnFavourite.setOnTouchListener(this);*/
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    public void setDescription(String description) {
        tvDescription.setText(description);
    }

    public void setVisibleBottomDivider(boolean visible) {
        if (visible) {
            vBottomDivider.setVisibility(View.VISIBLE);
        } else {
            vBottomDivider.setVisibility(View.GONE);
        }
    }

    public void setLineStatus(int state) {
        if (state == 1) {
            ivLine.setImageResource(R.color.white);
        } else {
            ivLine.setImageResource(R.color.transparent);
        }
    }

    public void setId(String id){
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        if (v == btnView) {
            SharedPreferences activitySharedPref =
                    Contextor.getInstance().getContext().getSharedPreferences("Event", Context.MODE_PRIVATE);
            activitySharedPref.edit().putString("EventID", id).apply();

            FragmentActivity fragmentActivity = (FragmentActivity) getContext();
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.stage_container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } /*else if (v == btnFavourite){
            if (!selected) {
                tvFavourite.setTextColor(ContextCompat.getColor(getContext(), R.color.highlightPinkColor));
                tvStar.setTextColor(ContextCompat.getColor(getContext(), R.color.highlightPinkColor));

            } else {
                tvFavourite.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                tvStar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
            selected = !selected;
        }*/
    }
}
