package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


        //**** MOCK *******//
        ArrayList<FacultyListItem> data = new ArrayList<>();
        for (int position = 0; position < 17; position++) {
            FacultyListItem faculty = new FacultyListItem(this.getContext());
            if (position%4 == 0) {
                faculty.setFacultyBg("1");
                faculty.setFacultyIcon("0");
                faculty.setFacultyTag("ENG", Color.WHITE, Color.rgb(156, 11, 16));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
            }
            else if (position%4 == 1) {
                faculty.setFacultyBg("0");
                faculty.setFacultyIcon("0");
                faculty.setFacultyTag("ART", Color.WHITE, Color.rgb(85, 85, 85));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
            }
            else if (position%4 == 2) {
                faculty.setFacultyBg("2");
                faculty.setFacultyIcon("0");
                faculty.setFacultyTag("SC", Color.GRAY, Color.rgb(254, 198, 1));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
            }
            else {
                faculty.setFacultyBg("1");
                faculty.setFacultyIcon("0");
                faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
            }
            data.add(faculty);
            // TODO: remove log
            Log.d("data", "init data " + position);
        }
        //**** END OF MOCK ******//
//        facultyView = (RecyclerView) rootView.findViewById(R.id.faculty_grid);
//        layoutManager = new GridLayoutManager(this.getContext(), 2);
//        adapter = new FacultyListAdapter(data);
//        facultyView.setAdapter(adapter);
//        facultyView.setLayoutManager(layoutManager);
//

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
            Toast.makeText(getActivity(), "*gogo*", Toast.LENGTH_SHORT).show();
        }
    };

}
