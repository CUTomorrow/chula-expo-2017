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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.InterestListAdapter;
import cuexpo.chulaexpo.datatype.InterestItem;
import cuexpo.chulaexpo.utility.CenteringHorizontalScrollView;

public class InterestActivity extends AppCompatActivity {

    private Activity activity;
    String[] images = { "http://keenthemes.com/preview/metronic/theme/assets/global/plugins/jcrop/demos/demo_files/image1.jpg"
    };
    String[] titles = {
            "Architecture", "Energy", "Technology", "Economy", "Title", "Title", "Title", "Title",
            "Title", "Title", "Title", "Title", "Title", "Title"
    };
    boolean[] isInterested = { false, false, true, false, true, false, false, false, false,
            false, false, true, true, false
    };
    ArrayList<InterestItem> interestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        activity = this;
        interestItems = new ArrayList<>();
        for(int i=0; i<titles.length; i++){
            InterestItem interestItem = new InterestItem(titles[i], images[0], isInterested[i]);
            interestItems.add(interestItem);
        }
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new InterestListAdapter(this, interestItems));
        gridView.setOnItemClickListener(onItemClick);

        ImageView doneBtn = (ImageView) findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(doneListener);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    private View.OnClickListener doneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
    };

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            InterestItem interestItem = interestItems.get(position);
            ImageView checkImage = (ImageView) v.findViewById(R.id.check_image);
            if(interestItem.isInterest()) {
                checkImage.setVisibility(View.INVISIBLE);
                interestItem.setInterest(false);
            }
            else {
                checkImage.setVisibility(View.VISIBLE);
                interestItem.setInterest(true);
            }
        }
    };

}
