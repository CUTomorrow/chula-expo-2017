package cuexpo.cuexpo2017.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cuexpo.cuexpo2017.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagPageFragment extends Fragment {



    public TagPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_page, container, false);
    }

}
