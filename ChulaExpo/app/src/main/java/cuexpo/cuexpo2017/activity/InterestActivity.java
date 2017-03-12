package cuexpo.cuexpo2017.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapter;
import cuexpo.cuexpo2017.datatype.InterestItem;
import cuexpo.cuexpo2017.utility.Resource;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class InterestActivity extends AppCompatActivity {
    private Activity activity;
    private ArrayList<InterestItem> interestItems = new ArrayList<>();
    private InterestListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        activity = this;

        initInterestItems();
        adapter = new InterestListAdapter(this, interestItems, 40);

//        LayoutInflater inflater = getLayoutInflater();
//        View gridViewFooter = inflater.inflate(R.layout.item_interest_footer, null);
//        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.grid_view);
//        gridView.addFooterView(gridViewFooter);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onItemClick);

        View doneBtn = findViewById(R.id.btnNext);
        doneBtn.setOnClickListener(doneListener);
    }

    private void initInterestItems(){
        try {
            JSONArray interestTagJSON = new JSONArray(
                    getResources().getString(R.string.jsonInterestTag)
            );
            for (int i = 0; i < interestTagJSON.length(); i++) {
                JSONObject tagData = interestTagJSON.getJSONObject(i);
                int id = tagData.getInt("id");
                interestItems.add(new InterestItem(
                        tagData.getString("nameTh"),
                        tagData.getString("nameEn"),
                        Resource.getTagBg(id),
                        Resource.getTagIcon(id),
                        false)
                );
            }
            JSONArray facultyTagJSON = new JSONArray(
                    getResources().getString(R.string.jsonFacultyMap)
            );
            for (int i = 0; i < facultyTagJSON.length(); i++) {
                JSONObject tagData = facultyTagJSON.getJSONObject(i);
                int id = tagData.getInt("id");
                if (id != 41 && id >= 21 && id <= 42)
                    interestItems.add(new InterestItem(
                            Resource.getFacultyTagDisplayName(id, tagData.getString("nameTh")),
                            tagData.getString("nameEn"),
                            Resource.getFaculltyTagBg(id),
                            Resource.getFaculltyTagIcon(id),
                            false)
                    );
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private View.OnClickListener doneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, DoneRegisterActivity.class);
            startActivity(intent);
        }
    };

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            InterestItem interestItem = interestItems.get(position);
            if(interestItem.isInterest()) {
                interestItem.setInterest(false);
            }
            else {
                interestItem.setInterest(true);
            }
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

