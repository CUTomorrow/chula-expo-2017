package cuexpo.chulaexpo.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.utility.CenteringHorizontalScrollView;

public class InterestActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
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
            false, true, false, false,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        LinearLayout imageGallery;
        imageGallery = (LinearLayout) findViewById(R.id.linearImage);
        for (int i = 0; i < images.length; i++) {
            RelativeLayout imageFrame = new RelativeLayout(InterestActivity.this);

            ImageView image = new ImageView(InterestActivity.this);
            image.setBackgroundResource(images[i]);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            image.setLayoutParams(imageParams);
            image.setId(i);
            imageFrame.addView(image);

            TextView titleText = new TextView(InterestActivity.this);
            titleText.setText(titles[i]);
            titleText.setTextColor(Color.BLACK);
            RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            titleParams.addRule(RelativeLayout.BELOW, image.getId());
            titleText.setLayoutParams(titleParams);
            titleText.setId(images.length + i);
            imageFrame.addView(titleText);

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
            imageFrame.invalidate();
            imageFrame.addView(descriptionText);

            imageGallery.addView(imageFrame);
        }
        CenteringHorizontalScrollView HSV = (CenteringHorizontalScrollView) findViewById(R.id.HSVImage);
        HSV.setCurrentItemAndCenter(1);
//        HSV.centerCurrentItem();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Interest Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener imgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            v.get
        }
    };
}
