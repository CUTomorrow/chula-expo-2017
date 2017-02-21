package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.EventDetailFragment;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StageInsideListItem extends BaseCustomViewGroup implements View.OnTouchListener {

    TextView tvDescription;
    TextView tvFavourite;
    TextView tvStar;
    ImageView ivLine;
    View vBottomDivider;
    LinearLayout btnView;
    LinearLayout btnFavourite;
    boolean selected = false;

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
        tvFavourite = (TextView) findViewById(R.id.stage_inside_tv_favourite);
        tvStar = (TextView) findViewById(R.id.stage_inside_tv_star);
        vBottomDivider = findViewById(R.id.stage_inside_bottom_divider);
        ivLine = (ImageView) findViewById(R.id.stage_inside_iv_line);
        btnView = (LinearLayout) findViewById(R.id.stage_inside_btn_info);
        btnFavourite = (LinearLayout) findViewById(R.id.stage_inside_btn_favourite);
        btnView.setOnTouchListener(this);
        btnFavourite.setOnTouchListener(this);
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

    public boolean getSelected() {
        return selected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == btnView) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    btnView.setBackgroundResource(R.drawable.shape_card_stroke_selected);
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.stage_overlay, new EventDetailFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    btnView.setBackgroundResource(R.drawable.shape_card_stroke);
                    break;
                }
            }
        } else if (v == btnFavourite) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if (!selected) {
                        btnFavourite.setBackgroundResource(R.drawable.shape_card_stroke_selected);
                        tvFavourite.setTextColor(ContextCompat.getColor(getContext(), R.color.highlightPinkColor));
                        tvStar.setTextColor(ContextCompat.getColor(getContext(), R.color.highlightPinkColor));

                    } else {
                        btnFavourite.setBackgroundResource(R.drawable.shape_card_stroke);
                        tvFavourite.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        tvStar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    }
                    selected = !selected;
                    break;
                }
            }
        }
        return true;
    }


}
