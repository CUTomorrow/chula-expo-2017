package cuexpo.chulaexpo2017.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo2017.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StageListItem extends BaseCustomViewGroup {

    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvName;
    private ImageView ivStatus;
    private ImageView ivDrop;
    private ImageView ivUpper;
    private ImageView ivLower;
    private boolean selected;
    private int day;

    public StageListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public StageListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public StageListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public StageListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_stage, this);
    }

    private void initInstances() {
        // findViewById here
        tvStartTime = (TextView) findViewById(R.id.stage_tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.stage_tv_end_time);
        ivStatus = (ImageView) findViewById(R.id.stage_iv_status_circle);
        ivDrop = (ImageView) findViewById(R.id.stage_iv_dropdown);
        ivUpper = (ImageView) findViewById(R.id.stage_iv_up_line);
        ivLower = (ImageView) findViewById(R.id.stage_iv_down_line);
        tvName = (TextView) findViewById(R.id.stage_tv_name);
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

    public int[] getStartTime() {
        String time = tvStartTime.getText().toString();
        int param[] = new int[2];
        if (time.indexOf(".") != -1) {
            param[0] = Integer.parseInt(time.substring(0, time.indexOf(".")));
            param[1] = Integer.parseInt(time.substring(time.indexOf(".") + 1));
        } else {
            param[0] = Integer.parseInt(time.substring(0, time.indexOf(":")));
            param[1] = Integer.parseInt(time.substring(time.indexOf(":") + 1));
        }
        return param;
    }

    public int[] getEndTime() {
        String time = tvEndTime.getText().toString();
        int param[] = new int[2];
        if (time.indexOf(".") != -1) {
            param[0] = Integer.parseInt(time.substring(0, time.indexOf(".")));
            param[1] = Integer.parseInt(time.substring(time.indexOf(".") + 1));
        } else {
            param[0] = Integer.parseInt(time.substring(0, time.indexOf(":")));
            param[1] = Integer.parseInt(time.substring(time.indexOf(":") + 1));
        }
        return param;
    }

    public void setTime(String startTime, String endTime) {
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
    }

    public void setStatus(int status) {
        if (status == 1) {
            ivStatus.setImageResource(R.drawable.soon_event);
            tvStartTime.setTypeface(tvStartTime.getTypeface(), Typeface.NORMAL);
            tvEndTime.setTypeface(tvEndTime.getTypeface(), Typeface.NORMAL);
        } else if (status == 2) {
            ivStatus.setImageResource(R.drawable.pass_event);
            tvStartTime.setTypeface(tvStartTime.getTypeface(), Typeface.NORMAL);
            tvEndTime.setTypeface(tvEndTime.getTypeface(), Typeface.NORMAL);
        } else {
            ivStatus.setImageResource(R.drawable.now_event);
            tvStartTime.setTypeface(tvStartTime.getTypeface(), Typeface.BOLD);
            tvEndTime.setTypeface(tvEndTime.getTypeface(), Typeface.BOLD);
        }
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setNameHighlight(int state) {
        if (state == 1) {
            tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.highlightPinkColor));
            String text = tvName.getText().toString();
            if (text.indexOf("★") == -1) {
                text += " ★";
                tvName.setText(text);
            }
        } else {
            tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            String text = tvName.getText().toString();
            if (text.indexOf("★") != -1) {
                text = text.substring(0, text.indexOf("★") - 1);
                tvName.setText(text);
            }
        }
    }

    public void setDrop(int state) {
        if (state == 1)
            ivDrop.setImageResource(R.drawable.down);
        else
            ivDrop.setImageResource(R.drawable.up);
    }

    public void setLineMode(int state) {
        if (state == 1) {
            ivUpper.setImageResource(R.color.white);
            ivLower.setImageResource(R.color.white);
        } else if (state == 2) {
            ivUpper.setImageResource(R.color.transparent);
            ivLower.setImageResource(R.color.white);
        } else if (state == 3){
            ivUpper.setImageResource(R.color.white);
            ivLower.setImageResource(R.color.transparent);
        } else{
            ivUpper.setImageResource(R.color.transparent);
            ivLower.setImageResource(R.color.transparent);
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
