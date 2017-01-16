package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import org.w3c.dom.Text;

import cuexpo.chulaexpo.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StageInsideListItem extends BaseCustomViewGroup implements View.OnTouchListener {

    TextView tvDescription;
    ImageView ivStatus;
    View vBottomDivider;
    TextView btnView;
    /*LinearLayout btnReserve;
    LinearLayout btnShare;
    LinearLayout btnFavourite;*/

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
        vBottomDivider = findViewById(R.id.stage_inside_bottom_divider);
        ivStatus = (ImageView) findViewById(R.id.stage_inside_iv_status);
        btnView = (TextView) findViewById(R.id.stage_inside_view);
        btnView.setOnTouchListener(this);
        /*btnReserve = (LinearLayout) findViewById(R.id.stage_inside_reserve);
        btnShare = (LinearLayout) findViewById(R.id.stage_inside_share);
        btnFavourite = (LinearLayout) findViewById(R.id.stage_inside_favourite);
        btnReserve.setOnTouchListener(this);
        btnShare.setOnTouchListener(this);
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
            ivStatus.setImageResource(R.color.stage_soon);
        } else if (state == 2) {
            ivStatus.setImageResource(R.color.stage_pass);
        } else {
            ivStatus.setImageResource(R.color.transparent);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == btnView) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    btnView.setBackgroundResource(R.drawable.border_selected);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    btnView.setBackgroundResource(R.drawable.border);
                    break;
                }
            }
        }
        /*if (v == btnReserve) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    btnReserve.setBackgroundResource(R.drawable.border_selected);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    btnReserve.setBackgroundResource(R.drawable.border);
                    break;
                }
            }
        } else if (v == btnShare) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    btnShare.setBackgroundResource(R.drawable.border2_selected);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    btnShare.setBackgroundResource(R.drawable.border2);
                    break;
                }
            }
        }else if (v == btnFavourite) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    btnFavourite.setBackgroundResource(R.drawable.border3_selected);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    btnFavourite.setBackgroundResource(R.drawable.border3);
                    break;
                }
            }
        }*/
        return true;
    }
}
