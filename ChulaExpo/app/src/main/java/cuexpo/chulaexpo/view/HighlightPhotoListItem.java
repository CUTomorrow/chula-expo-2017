package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import cuexpo.chulaexpo.R;

/**
 * Created by James on 12/25/2016.
 */
public class HighlightPhotoListItem extends BaseCustomViewGroup {

    TextView tvHighlightTitle,tvHighlightDesc;
    ImageView ivHighlight;

    public HighlightPhotoListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public HighlightPhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public HighlightPhotoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public HighlightPhotoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_highlight_photo, this);
    }

    private void initInstances() {
        // findViewById here
        tvHighlightTitle = (TextView)findViewById(R.id.tvHighlightTitle);
        tvHighlightDesc = (TextView)findViewById(R.id.tvHighlightDesc);
        ivHighlight = (ImageView)findViewById(R.id.ivHighlight);
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
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width*1/2;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        //change Child View
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        //change Self View
        setMeasuredDimension(width,height);
    }

    public void setNameText(String text){
        tvHighlightTitle.setText(text);
    }

    public void setDescriptionText(String text){
        tvHighlightDesc.setText(text);
    }

    public void setImageUrl(String url){
        /*
        Glide.with(getContext())
                .load(url)
                .into(ivHighlight);*/
        //mock
        if(url.equals("0")) ivHighlight.setImageResource(R.drawable.highlight_vidva_1);
        else if(url.equals("1")) ivHighlight.setImageResource(R.drawable.highlight_stat_1);
        else ivHighlight.setImageResource(R.drawable.highlight_psy_1);
    }
}
