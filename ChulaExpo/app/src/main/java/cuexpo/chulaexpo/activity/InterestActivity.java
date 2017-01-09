package cuexpo.chulaexpo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.utility.CenteringHorizontalScrollView;

public class InterestActivity extends AppCompatActivity {

    private Activity activity;
    private CenteringHorizontalScrollView HSV;
    int[] images = { R.drawable.cir_invisible, R.drawable.cir_mock, R.drawable.cir_mock,
            R.drawable.cir_mock, R.drawable.cir_mock, R.drawable.cir_mock, R.drawable.cir_mock,
            R.drawable.cir_mock, R.drawable.cir_mock, R.drawable.cir_mock, R.drawable.cir_invisible
    };
    String[] titles = {
            "", "Energy", "Technology", "Economy", "Title", "Title", "Title", "Title", "Title", "Title", ""
    };
    String[] descriptions = {
            "Technology is the collection of techniques, skills, methods, and processes used in the" +
                    " production of goods or services in the accomplishment of objectives"
    };
    boolean[] isInterested = { false, false, true, false, true, false, false, false, false,
            false, false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        activity = this;

        setImageGallery();
        HSV = (CenteringHorizontalScrollView) findViewById(R.id.HSVImage);
        HSV.setCurrentItemAndCenter(1);

        ImageView doneBtn = (ImageView) findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(doneListener);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener imgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            RelativeLayout layout = (RelativeLayout) v;
            ImageView check = (ImageView) layout.getChildAt(1);
            if(isInterested[id]){
                check.setVisibility(View.INVISIBLE);
                isInterested[id] = false;
            } else {
                check.setVisibility(View.VISIBLE);
                isInterested[id] = true;
            }
        }
    };

    private View.OnClickListener doneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
    };

    private void setImageGallery(){
        LinearLayout imageGallery;
        imageGallery = (LinearLayout) findViewById(R.id.linearImage);
        for (int i = 0; i < images.length; i++) {
            RelativeLayout frame = new RelativeLayout(InterestActivity.this);
            RelativeLayout imageFrame = new RelativeLayout(InterestActivity.this);
            RelativeLayout.LayoutParams imageFrameParams = new RelativeLayout.LayoutParams(
                    dpToPx(170), dpToPx(170));
            imageFrameParams.setMargins(0, dpToPx(51), 0, 0);
            imageFrame.setLayoutParams(imageFrameParams);

            ImageView image = new ImageView(InterestActivity.this);
            image.setBackgroundResource(images[i]);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            image.setLayoutParams(imageParams);
            imageFrame.addView(image);

            ImageView check = new ImageView(InterestActivity.this);
            check.setBackgroundResource(R.drawable.interest_check);
            RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            checkParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            check.setLayoutParams(checkParams);
            if(isInterested[i]) check.setVisibility(View.VISIBLE);
            else check.setVisibility(View.INVISIBLE);
            imageFrame.addView(check);
            imageFrame.setId(i);
            imageFrame.setOnClickListener(imgListener);
            frame.addView(imageFrame);

            TextView titleText = new TextView(InterestActivity.this);
            titleText.setText(titles[i]);
            titleText.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            titleParams.addRule(RelativeLayout.BELOW, imageFrame.getId());
            titleText.setLayoutParams(titleParams);
            titleText.setId(images.length + i);
            frame.addView(titleText);

            TextView descriptionText = new TextView(InterestActivity.this);
            descriptionText.setText(descriptions[0]);
            descriptionText.setWidth(dpToPx(310));
            descriptionText.setGravity(Gravity.CENTER_HORIZONTAL);
            descriptionText.setTextSize(17);
            descriptionText.setPadding(0, dpToPx(30), 0, 0);
            titleText.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams descriptionParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            descriptionParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            descriptionParams.addRule(RelativeLayout.BELOW, titleText.getId());
            descriptionText.setLayoutParams(descriptionParams);
            descriptionText.setVisibility(View.GONE);
            descriptionText.setTextColor(Color.parseColor("#95989A"));
            frame.addView(descriptionText);

            frame.invalidate();
            imageGallery.addView(frame);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        HSV.post(new Runnable() {
            public void run() {
                HSV.smoothScrollTo(429 - 156, 0);
            }
        });
    }
}
