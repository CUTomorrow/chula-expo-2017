package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DateSelector extends BaseCustomViewGroup {

    TextView dateSelectorDate;
    TextView dateSelectorMonth;

    public DateSelector(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DateSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public DateSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public DateSelector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.date_selector, this);
    }

    private void initInstances() {
        // findViewById here
        dateSelectorMonth = (TextView) findViewById(R.id.date_selector_month);
        dateSelectorDate = (TextView) findViewById(R.id.date_selector_date);
    }

    public void setDate(String date){
        dateSelectorDate.setText(date);
    }

    public void setToggle(int state){
        if(state==1){
            dateSelectorDate.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            dateSelectorMonth.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        }else{
            dateSelectorDate.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            dateSelectorMonth.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        }
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

}
