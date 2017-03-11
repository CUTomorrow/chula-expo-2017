package cuexpo.cuexpo2017.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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


public class InterestActivity extends AppCompatActivity {
    private Activity activity;
    private ArrayList<InterestItem> interestItems = new ArrayList<>();
    private InterestListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        activity = this;
        adapter = new InterestListAdapter(this, interestItems);

        LayoutInflater inflater = getLayoutInflater();
        View gridViewFooter = inflater.inflate(R.layout.item_interest_footer, null);
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.grid_view);
        gridView.addFooterView(gridViewFooter);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onItemClick);

        ImageView doneBtn = (ImageView) findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(doneListener);

        initInterestItems();
    }

    private void initInterestItems(){
        try {
            JSONArray facultiesJSON = new JSONArray(
                    getResources().getString(R.string.jsonFacultyMap)
            );
            for (int i = 0; i < facultiesJSON.length(); i++) {
                JSONObject facData = facultiesJSON.getJSONObject(i);
                int id = facData.getInt("id");
                interestItems.add(new InterestItem(
                        facData.getString("nameTh"),
                        facData.getString("nameEn"),
                        Resource.getTagBg(id),
                        Resource.getTagIcon(id),
                        false)
                );
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        adapter.notifyDataSetChanged();
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
            ImageView checkImage = (ImageView) v.findViewById(R.id.check_image);
            if(interestItem.isInterest()) {
                interestItem.setInterest(false);
            }
            else {
                interestItem.setInterest(true);
            }

        }
    };
}

