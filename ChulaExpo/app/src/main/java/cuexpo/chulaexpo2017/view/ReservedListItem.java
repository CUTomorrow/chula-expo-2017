package cuexpo.chulaexpo2017.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo2017.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ReservedListItem extends BaseCustomViewGroup {

    TextView tvName;
    TextView tvTime;
    TextView tvFac;
    TextView tvReserved;
    ImageView ivThumbnail;


    public ReservedListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ReservedListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public ReservedListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ReservedListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_reserved, this);
    }

    private void initInstances() {
        // findViewById here
        tvName = (TextView) findViewById(R.id.reserved_tv_name);
        tvTime = (TextView) findViewById(R.id.reserved_tv_time);
        tvFac = (TextView) findViewById(R.id.reserved_tv_fac);
        tvReserved = (TextView) findViewById(R.id.reserved_tv_reserved);
        ivThumbnail = (ImageView) findViewById(R.id.reserved_iv_thumbnail);
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

    public void setName(String text) {
        tvName.setText(text);
    }

    public void setTime(String text) {
        tvTime.setText(text);
    }

    public void setFaculty(String text) {
        tvFac.setText(text);
    }

    public void setReserved(String text){ tvReserved.setText(text);}

    public void setImage(int resId) {
        ivThumbnail.setImageResource(resId);
    }

}
