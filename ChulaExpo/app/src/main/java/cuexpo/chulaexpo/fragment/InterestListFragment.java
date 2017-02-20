package cuexpo.chulaexpo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.adapter.InterestListAdapterNew;
import cuexpo.chulaexpo.datatype.InterestItem;


public class InterestListFragment extends Fragment {

    private ArrayList<InterestItem> interestItems;
    private GridView interestGrid;
    private InterestListAdapterNew interestListAdapter;

    public InterestListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static InterestListFragment newInstance() {
        InterestListFragment fragment = new InterestListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_interest_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        setItem();

        interestGrid = (GridView) rootView.findViewById(R.id.interest_grid);
        interestListAdapter = new InterestListAdapterNew(getActivity(), interestItems);
        interestGrid.setAdapter(interestListAdapter);
        interestGrid.setOnItemClickListener(onItemClick);

    }

    private void setItem() {
        interestItems = new ArrayList<InterestItem>();

        String[] title = {
                "สุขภาพ", "อาหาร", "การเงิน", "กฏหมาย", "ไอเดีย", "ยานพาหนะ", "ภาษา", "การศึกษา",
                "การแพทย์", "ธรรมชาติ", "คอมพิวเตอร์", "ศิลปะ", "Title", "Title"
        };

        String[] titleEng = {
                "Health", "Food", "Economic", "Law", "Idea", "Vehicle", "Language", "Education",
                "Medical", "Nature", "Computer", "Arts", "Title", "Title"
        };

        String[] backgroundUrl = {
                //MOCK ONLY
                "https://upload.wikimedia.org/wikipedia/commons/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg",
                "http://www.shedoesthecity.com/wp-content/uploads/files/2015/08/5-Questions-to-Ask-Yourself-When-Building-a-Health-Plan.jpg",
                "http://morestaurants.org/wp-content/uploads/2016/03/economic-growth.jpg",
                "http://i.huffpost.com/gen/1720141/images/o-LAW-facebook.jpg"

        };

        String[] iconUrl = {
        };

        boolean[] isInterest = {
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false
        };

        for (int i = 0; i<title.length ; i++) {
            InterestItem interest = new InterestItem(title[i], titleEng[i], backgroundUrl[i%4], null, isInterest[i]);
            interestItems.add(interest);
        }



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

    //Listener
    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "wow much interest", Toast.LENGTH_SHORT).show();
        }
    };

}
