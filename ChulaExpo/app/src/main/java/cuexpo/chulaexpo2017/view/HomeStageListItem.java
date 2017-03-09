package cuexpo.chulaexpo2017.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo2017.R;

/**
 * Created by James on 12/25/2016.
 */
public class HomeStageListItem extends BaseCustomViewGroup {

    TextView tvStageId, tvStageLocation, tvStageTitle, tvStageTime;

    public HomeStageListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public HomeStageListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public HomeStageListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public HomeStageListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_stage, this);
    }

    private void initInstances() {
        // findViewById here
        tvStageId = (TextView) findViewById(R.id.tvStageId);
        tvStageLocation = (TextView) findViewById(R.id.tvStageLocation);
        tvStageTitle = (TextView)findViewById(R.id.tvStageTitle);
        tvStageTime = (TextView)findViewById(R.id.tvStageTime);

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
}
