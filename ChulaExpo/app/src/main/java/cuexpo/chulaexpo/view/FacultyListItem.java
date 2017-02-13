package cuexpo.chulaexpo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
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


public class FacultyListItem extends BaseCustomViewGroup {

    public ImageView facultyBg;
    ImageView facultyIcon;
    TextView facultyTag, facultyTitle, facultyTitleEng;
    private String imageUrl;
    private String iconUrl;

    public FacultyListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public FacultyListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public FacultyListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public FacultyListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.item_faculty, this);
    }

    private void initInstances() {
        // findViewById here
        Log.d("init", "init FACULTY");
        facultyBg = (ImageView) findViewById(R.id.faculty_bg);
        facultyIcon = (ImageView) findViewById(R.id.faculty_icon);
        facultyTag = (TextView) findViewById(R.id.faculty_tag);
        facultyTitle = (TextView) findViewById(R.id.faculty_title);
        facultyTitleEng = (TextView) findViewById(R.id.faculty_title_eng);
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

    public TextView getFacultyTag() { return facultyTag;}
    public Drawable getFacultyBg() { return facultyBg.getDrawable();}
    public Drawable getfacultyIcon() { return facultyIcon.getDrawable();}
    public TextView getFacultyTitle() {return facultyTitle;}
    public TextView getFacultyTitleEng() {return facultyTitleEng;}

    public void setFacultyBg(String imageUrl) {
        /*Glide.with(getContext())
                .load(imageUrl)
                .into(facultyBg);*/
        //mock
        this.imageUrl = imageUrl;
        if (imageUrl == "0") facultyBg.setImageResource(R.drawable.faculty_1);
        else if (imageUrl == "1") facultyBg.setImageResource(R.drawable.faculty_2);
        else if (imageUrl == "2") facultyBg.setImageResource(R.drawable.faculty_3);
        else facultyBg.setImageResource(R.drawable.faculty_2);

    }

    public void setFacultyIcon(String iconUrl) {
        /*Glide.with(getContext())
                .load(iconUrl)
                .into(facultyIcon);*/
        //mock
        this.iconUrl = iconUrl;
        facultyIcon.setImageResource(R.drawable.cir_mock);
    }

    public void setFacultyTag(String text, int textColor, int tagColor) {
        facultyTag.setText(text);
        facultyTag.setTextColor(textColor);
        facultyTag.setBackgroundColor(tagColor);
    }

    public void setFacultyTitle(String title) {
        facultyTitle.setText(title);
    }

    public void setFacultyTitleEng(String engTitle) {
        facultyTitleEng.setText(engTitle);
    }

}
