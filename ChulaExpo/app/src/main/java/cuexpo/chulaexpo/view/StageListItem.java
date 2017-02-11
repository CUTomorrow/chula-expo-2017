package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
<<<<<<< HEAD
import android.graphics.Typeface;
=======
>>>>>>> Commit
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo.R;

/**
<<<<<<< HEAD
 * Created by nuuneoi on 11/16/2014.
 */
public class StageListItem extends BaseCustomViewGroup {

    TextView tvStartTime;
    TextView tvEndTime;
    TextView tvName;
    ImageView ivStatus;
    ImageView ivDrop;
    ImageView ivUpper;
    ImageView kuy;
    ImageView ivLower;
=======
 * Created by James on 12/25/2016.
 */
public class StageListItem extends BaseCustomViewGroup {

    TextView tvStageId, tvStageLocation, tvStageTitle, tvStageTime;
    ImageView ivStageStatus;
>>>>>>> Commit

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
<<<<<<< HEAD
        inflate(getContext(), R.layout.list_stage, this);
=======
        inflate(getContext(), R.layout.list_item_stage, this);
>>>>>>> Commit
    }

    private void initInstances() {
        // findViewById here
<<<<<<< HEAD
        tvStartTime = (TextView) findViewById(R.id.stage_tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.stage_tv_end_time);
        ivStatus = (ImageView) findViewById(R.id.stage_iv_status_circle);
        ivDrop = (ImageView) findViewById(R.id.stage_iv_dropdown);
        ivUpper = (ImageView) findViewById(R.id.stage_iv_up_line);
        ivLower = (ImageView) findViewById(R.id.stage_iv_down_line);
        //kuy = (ImageView) findViewById(R.id.whatthefuck);
        tvName = (TextView) findViewById(R.id.stage_tv_name);
=======
        tvStageId = (TextView) findViewById(R.id.tvStageId);
        tvStageLocation = (TextView) findViewById(R.id.tvStageLocation);
        tvStageTitle = (TextView)findViewById(R.id.tvStageTitle);
        tvStageTime = (TextView)findViewById(R.id.tvStageTime);
        ivStageStatus = (ImageView) findViewById(R.id.ivStageStatus);

>>>>>>> Commit
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

<<<<<<< HEAD
    public int[] getStartTime() {
        String time = tvStartTime.getText().toString();
        int param[] = new int[2];
        param[0] = Integer.parseInt(time.substring(0, time.indexOf(".")));
        param[1] = Integer.parseInt(time.substring(time.indexOf(".") + 1));
        return param;
    }

    public int[] getEndTime() {
        String time = tvEndTime.getText().toString();
        int param[] = new int[2];
        param[0] = Integer.parseInt(time.substring(0, time.indexOf(".")));
        param[1] = Integer.parseInt(time.substring(time.indexOf(".") + 1));
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
        } else {
            ivUpper.setImageResource(R.color.white);
            ivLower.setImageResource(R.color.transparent);
        }
    }

=======
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //change Child View
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
        //change Self View
        setMeasuredDimension(width,height);
        */
    }

    public void setTvStageId(String text) {
        tvStageId.setText(text);
    }

    public void setTvStageLocation(String text) {
        tvStageLocation.setText(text);
    }

    public void setTvStageTitle(String  text) {
        tvStageTitle.setText(text);
    }

    public void setTvStageTime(String text) {
        tvStageTime.setText(text);
    }

    public void setIvStageStatus(int status){
        //TODO : Input Time Logic
        if(status == 1) ivStageStatus.setImageResource(R.drawable.shape_stage_circle);
        else  ivStageStatus.setImageResource(R.drawable.shape_stage_circle_off);
    }
>>>>>>> Commit
}
