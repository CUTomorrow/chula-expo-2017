package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.FacultyListAdapter;
import cuexpo.chulaexpo.view.FacultyListItem;


public class FacultyListFragment extends Fragment {

    GridView faculty;
    FacultyListAdapter adapter;
//
//    private FacultyListAdapter adapter;
//    private RecyclerView facultyView;
//    private RecyclerView.LayoutManager layoutManager;

    public FacultyListFragment() {
        super();
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
        adapter = new FacultyListAdapter();
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
        switch(position) {
            case 0:
                activitySharedPref.edit().putString("ZoneName", "ENG").apply();
                break;
            case 1:
                activitySharedPref.edit().putString("ZoneName", "ARTS").apply();
                break;
            case 2:
                activitySharedPref.edit().putString("ZoneName", "SCI").apply();
                break;
            case 3:
                activitySharedPref.edit().putString("ZoneName", "POLSCI").apply();
                break;
            case 4:
                activitySharedPref.edit().putString("ZoneName", "ARCH").apply();
                break;
            case 5:
                activitySharedPref.edit().putString("ZoneName", "BANSHI").apply();
                break;
            case 6:
                activitySharedPref.edit().putString("ZoneName", "EDU").apply();
                break;
            case 7:
                activitySharedPref.edit().putString("ZoneName", "COMMARTS").apply();
                break;
            case 8:
                activitySharedPref.edit().putString("ZoneName", "ECON").apply();
                break;
            case 9:
                activitySharedPref.edit().putString("ZoneName", "MED").apply();
                break;
            case 10:
                activitySharedPref.edit().putString("ZoneName", "VET").apply();
                break;
            case 11:
                activitySharedPref.edit().putString("ZoneName", "DENT").apply();
                break;
            case 12:
                activitySharedPref.edit().putString("ZoneName", "PHARM").apply();
                break;
            case 13:
                activitySharedPref.edit().putString("ZoneName", "LAW").apply();
                break;
            case 14:
                activitySharedPref.edit().putString("ZoneName", "FAA").apply();
                break;
            case 15:
                activitySharedPref.edit().putString("ZoneName", "NUR").apply();
                break;
            case 16:
                activitySharedPref.edit().putString("ZoneName", "AHS").apply();
                break;
            case 17:
                activitySharedPref.edit().putString("ZoneName", "PSY").apply();
                break;
            case 18:
                activitySharedPref.edit().putString("ZoneName", "SPSC").apply();
                break;
            case 19:
                activitySharedPref.edit().putString("ZoneName", "SAR").apply();
                break;
            case 20:
                activitySharedPref.edit().putString("ZoneName", "GRAD").apply();
                break;
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new ZoneMainPageFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }
    };

}
