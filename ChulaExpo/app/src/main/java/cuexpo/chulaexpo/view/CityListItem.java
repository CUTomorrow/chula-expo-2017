package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo.R;


public class CityListItem extends BaseCustomViewGroup {

    TextView cityTitle, cityTag;
    ImageView cityBg;
    ImageView cityIcon;

    public CityListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CityListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public CityListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CityListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_city, this);
    }

    private void initInstances() {
        // findViewById here
        // TODO: remove log
        Log.d("init", "init CITY");
        cityTitle = (TextView) findViewById(R.id.city_title);
        cityTag = (TextView) findViewById(R.id.city_tag);
        cityBg = (ImageView) findViewById(R.id.city_background);
        cityIcon = (ImageView) findViewById(R.id.city_icon);
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

    public void setCityTitle(String cityTitle) { this.cityTitle.setText(cityTitle);}


    public void setCityTag(String tag, int textColor, int tagBg) {
        this.cityTag.setText(tag);
        this.cityTag.setTextColor(textColor);
        this.cityTag.setBackgroundColor(tagBg);
    }

    public void setCityIcon(int res) {
        /*Glide.with(getContext())
                .load(iconUrl)
                .into(cityIcon);*/
        cityIcon.setImageResource(res);
    }

    public void setImageUrl(int res) {
        /*Glide.with(getContext())
                .load(imageUrl)
                .into(bgImage);*/
        cityBg.setImageResource(res);
    }

}
