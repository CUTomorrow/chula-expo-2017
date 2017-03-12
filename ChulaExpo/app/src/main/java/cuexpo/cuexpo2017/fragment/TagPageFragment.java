package cuexpo.cuexpo2017.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapterNew;
import cuexpo.cuexpo2017.adapter.TagPageAdapter;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;

public class TagPageFragment extends Fragment {

    private ListView list;
    private TagPageAdapter adapter;
    private List<ActivityItemResultDao> eventList;

    public TagPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tag_page, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        list = (ListView) rootView.findViewById(R.id.list_view);
        adapter = new TagPageAdapter(getContext());
        list.setAdapter(adapter);

    }

}
