package cuexpo.cuexpo2017.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
            JSONArray facultyJSON = new JSONArray(
                    getContext().getResources().getString(R.string.jsonFacultyMap)
            );
            for (int i = 21; i <=42; i++) {
                if (i != 41) {
                    JSONObject facData = facultyJSON.getJSONObject(i);
                    facultyData.add(new InterestItem(
                            facData.getInt("id"),
                            facData.getString("nameTh"),
                            facData.getString("nameEn")));
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public FacultyListFragment() {
        super();
        initializeFacultyData();
    }

    @SuppressWarnings("unused")
    public static FragmentTemplateFull newInstance() {
        FragmentTemplateFull fragment = new FragmentTemplateFull();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faculty_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        faculty = (GridView) rootView.findViewById(R.id.faculty_grid);
        adapter = new FacultyListAdapter(facultyData);
        faculty.setAdapter(adapter);
        faculty.setOnItemClickListener(facultyItemListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    /**
     * listener
     */

    AdapterView.OnItemClickListener facultyItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Zone", Context.MODE_PRIVATE);
        activitySharedPref.edit().putString("ZoneName", (String) adapter.getItem(position)).apply();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new ZoneMainPageFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }
    };

}
