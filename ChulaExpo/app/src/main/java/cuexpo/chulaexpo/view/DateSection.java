package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cuexpo.chulaexpo.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DateSection extends BaseCustomViewGroup {

    TextView tvDate;
    TextView tvDate2;

    public DateSection(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DateSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public DateSection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public DateSection(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_date_section, this);
    }

    private void initInstances() {
        // findViewById here
        tvDate = (TextView) findViewById(R.id.favourite_tv_date);
        tvDate2 = (TextView) findViewById(R.id.favourite_tv_date2);
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

    public void setDate(String text) {
        Calendar c = Calendar.getInstance();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String date = df.format(c.getTime());
        df = new SimpleDateFormat("MM");
        String month = df.format(c.getTime());
        String realDate;
        if (date.equals(text) && month.equals("03")) {
            realDate = "Today";
        } else if (date.equals((Integer.parseInt(text) - 1) + "") && month.equals("03")) {
            realDate = "Tomorrow";
        } else if (date.equals((Integer.parseInt(text) + 1) + "") && month.equals("03")) {
            realDate = "Yesterday";
        } else {
            //String day = checkDate(text);
            realDate = text + " March 2017";
        }
        tvDate.setText(realDate);
        tvDate2.setText(text);
    }

    public String checkDate(String text) {
        switch (text) {
            case "15":
                return "Mon";
            case "16":
                return "Tue";
            case "17":
                return "Wed";
            case "18":
                return "Thu";
            case "19":
                return "Fri";
            default:
                return "Null";
        }
    }
}
