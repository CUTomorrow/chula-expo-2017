package cuexpo.cuexpo2017.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapter;
import cuexpo.cuexpo2017.datatype.InterestItem;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InterestActivity extends AppCompatActivity {

    private Activity activity;
    ArrayList<InterestItem> interestItems;
    TextView selectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        activity = this;
        interestItems = new ArrayList<>();
        setInterestItems();

        LayoutInflater inflater = getLayoutInflater();
        View gridViewFooter = inflater.inflate(R.layout.item_interest_footer, null);

        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.grid_view);
        gridView.addFooterView(gridViewFooter);
        gridView.setAdapter(new InterestListAdapter(this, interestItems));
        gridView.setOnItemClickListener(onItemClick);

        ImageView doneBtn = (ImageView) findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(doneListener);

        selectedText = (TextView) findViewById(R.id.selected);
        setSelectedText();
    }

    private void setInterestItems(){
        String[] images = { "http://keenthemes.com/preview/metronic/theme/assets/global/plugins/jcrop/demos/demo_files/image1.jpg"
        };
        String[] titles = {
                "Architecture", "Energy", "Technology", "Economy", "Title", "Title", "Title", "Title",
                "Title", "Title", "Title", "Title", "Title", "Title"
        };
        boolean[] isInterested = { false, false, true, false, true, false, false, false, false,
                false, false, true, true, false
        };
        for(int i=0; i<titles.length; i++){
            InterestItem interestItem = new InterestItem(titles[i], images[0], isInterested[i]);
            interestItems.add(interestItem);
        }
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
            setSelectedText();
        }
    };

    private void setSelectedText(){
        int selectedItem = 0;
        int totalItem = 0;
        for(InterestItem interestItem: interestItems){
            if(interestItem.isInterest()) selectedItem++;
            totalItem++;
        }
        selectedText.setText(selectedItem + "/" + totalItem);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
