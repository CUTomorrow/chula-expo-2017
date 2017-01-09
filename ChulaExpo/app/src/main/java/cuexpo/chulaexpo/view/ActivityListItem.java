package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import org.w3c.dom.Text;

import cuexpo.chulaexpo.R;

/**
 * Created by James on 12/25/2016.
 */
public class ActivityListItem extends BaseCustomViewGroup {

    TextView tvActivityTitle, tvActivityDesc, tvActivityTime,tvActivityFaculty,tvActivityBookingCount;
    ImageView ivActivity;

    public ActivityListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ActivityListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public ActivityListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ActivityListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_activity, this);
    }

    private void initInstances() {
        // findViewById here
        tvActivityTitle = (TextView)findViewById(R.id.tvActivityTitle);
        tvActivityDesc = (TextView)findViewById(R.id.tvActivityDesc);
        ivActivity = (ImageView)findViewById(R.id.ivActivity);
        tvActivityTime = (TextView) findViewById(R.id.tvActivityTime);
        tvActivityFaculty = (TextView) findViewById(R.id.tvActivityFaculty);
        tvActivityBookingCount = (TextView) findViewById(R.id.tvActivityBookingCount);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //change Child View
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
        //change Self View
        setMeasuredDimension(width,height);
        */
    }

    public void setNameText(String text){
        tvActivityTitle.setText(text);
    }

    public void setDescriptionText(String text){
        tvActivityDesc.setText(text);
    }

    public void setTimeText(String text){
        tvActivityTime.setText(text);
    }

    public void setFacultyText(String text, int color){
        tvActivityFaculty.setText(text);
        tvActivityFaculty.setBackgroundColor(color);
    }

    public void setBookingCountText(int booked, int capacity){
        tvActivityBookingCount.setText(booked+"/"+capacity);
        if(booked == capacity)  tvActivityBookingCount.setBackgroundResource(R.drawable.shape_rounded_rectangle_red);
        else if( (double)(capacity - booked) / (double)booked <= 0.2 ) tvActivityBookingCount.setBackgroundResource(R.drawable.shape_rounded_rectangle_orange);
        else tvActivityBookingCount.setBackgroundResource(R.drawable.shape_rounded_rectangle_green);
    }

    public void setImageUrl(String url){
        /*
        Glide.with(getContext())
                .load(url)
                .into(ivActivity);*/
        //mock
        if(url.equals("0")) ivActivity.setImageResource(R.drawable.highlight_vidva_1);
        else if(url.equals("1")) ivActivity.setImageResource(R.drawable.highlight_stat_1);
        else ivActivity.setImageResource(R.drawable.highlight_psy_1);
    }
}
