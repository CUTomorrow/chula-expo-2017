package cuexpo.cuexpo2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.FavouriteListAdapter;
import cuexpo.cuexpo2017.view.ExpandableHeightListView;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FavouriteFragment extends Fragment implements View.OnClickListener {

    ImageView back;
    ExpandableHeightListView upComingEventListView;
    ExpandableHeightListView previousEventListView;
    FavouriteListAdapter adapter;

    public FavouriteFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        upComingEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.favourite_content_container);
        previousEventListView = (ExpandableHeightListView) rootView.findViewById(R.id.favourite_content_container2);
        back = (ImageView) rootView.findViewById(R.id.favourite_back);
        adapter = new FavouriteListAdapter();

        upComingEventListView.setAdapter(adapter);
        upComingEventListView.setExpanded(true);
        upComingEventListView.setFocusable(false);

        previousEventListView.setAdapter(adapter);
        previousEventListView.setExpanded(true);
        previousEventListView.setFocusable(false);
        back.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v==back){
            getActivity().finish();
        }
    }

}
