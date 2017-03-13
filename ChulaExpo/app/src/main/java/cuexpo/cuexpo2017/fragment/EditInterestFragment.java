package cuexpo.cuexpo2017.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.DoneRegisterActivity;
import cuexpo.cuexpo2017.adapter.InterestListAdapter;
import cuexpo.cuexpo2017.datatype.InterestItem;
import cuexpo.cuexpo2017.utility.Resource;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by APTX-4869 (LOCAL) on 3/13/2017.
 */

public class EditInterestFragment extends Fragment {

    private static EditInterestFragment instance;
    private ArrayList<InterestItem> interestItems = new ArrayList<>();
    private InterestListAdapter adapter;
    private View rootView;
    private SharedPreferences sharedPref;

    public static EditInterestFragment newInstance() {
        instance = new EditInterestFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    public static EditInterestFragment getInstance(){
        if(instance == null) return newInstance();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_interest, container, false);
        rootView.findViewById(R.id.btnCancel).setOnClickListener(cancelOCL);
        rootView.findViewById(R.id.btnSave).setOnClickListener(saveOCL);

        initInterestItems();
        adapter = new InterestListAdapter(getActivity(), interestItems, 40);

        View gridViewFooter = inflater.inflate(R.layout.item_interest_footer, null);
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.grid_view);
        gridView.addFooterView(gridViewFooter);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onItemClick);

        sharedPref = getActivity().getSharedPreferences("FacebookInfo", getContext().MODE_PRIVATE);

        return rootView;
    }

    View.OnClickListener cancelOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

    View.OnClickListener saveOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tags = "";
            boolean isFirstTag = true;
            for(InterestItem interestItem: interestItems) {
                if(interestItem.isInterest()){
                    if(isFirstTag){
                        isFirstTag = false;
                        tags += interestItem.getNameEng();
                    } else {
                        tags += ", " + interestItem.getNameEng();
                    }
                }
            }
            Log.d("tags", tags);
            sharedPref.edit().putString("tags", tags).apply();
        }
    };

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
                if (id>=21 && id<=45 && id != 43) {
                    String name = tagData.getString("nameTh");
                    interestItems.add(new InterestItem(
                            Resource.getFacultyTagDisplayName(id, name),
                            tagData.getString("nameEn"),
                            Resource.getFaculltyTagBg(id),
                            Resource.getFaculltyTagIcon(id),
                            false)
                    );
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

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
    public void onDestroy(){
        Log.d("Destroy EditInterest", "done");
        instance = null;
        super.onDestroy();
    }
}
