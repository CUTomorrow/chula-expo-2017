package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.FacultyListAdapter;
import cuexpo.cuexpo2017.datatype.InterestItem;
import cuexpo.cuexpo2017.utility.NormalPinMapEntity;
import cuexpo.cuexpo2017.utility.PopbusRouteMapEntity;


public class FacultyListFragment extends Fragment {

    GridView faculty;
    FacultyListAdapter adapter;
    List<InterestItem> facultyData = new ArrayList<>();

    private void initializeFacultyData() {
        try {
            JSONArray facultiesJSON = new JSONArray(
                    getContext().getResources().getString(R.string.jsonFacultyMap)
            );
            for (int i = 0; i < facultiesJSON.length(); i++) {
                JSONObject facData = facultiesJSON.getJSONObject(i);
                int id = facData.getInt("id");
                if (id>=21 && id<=45 && id != 43)
                    facultyData.add(new InterestItem(
                            facData.getInt("id"),
                            facData.getString("nameTh"),
                            facData.getString("nameEn")));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faculty_list, container, false);
        initializeFacultyData();
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        faculty = (GridView) rootView.findViewById(R.id.faculty_grid);
        adapter = new FacultyListAdapter(facultyData);
        faculty.setAdapter(adapter);
        faculty.setOnItemClickListener(facultyItemListener);
    }

    AdapterView.OnItemClickListener facultyItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Zone", Context.MODE_PRIVATE);
        activitySharedPref.edit().putString("ZoneName", (String) adapter.getItem(position)).apply();
        activitySharedPref.edit().putInt("FacultyId", (int) adapter.getItemId(position)).apply();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new ZoneMainPageFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }
    };

}
