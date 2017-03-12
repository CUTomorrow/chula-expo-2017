package cuexpo.cuexpo2017.fragment;

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
import android.widget.Toast;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapter;
import cuexpo.cuexpo2017.datatype.InterestItem;


public class InterestListFragment extends Fragment {

    private ArrayList<InterestItem> interestItems;
    private GridView interestGrid;
    private InterestListAdapter interestListAdapter;

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
        interestListAdapter = new InterestListAdapter(getActivity(), interestItems);
        interestGrid.setAdapter(interestListAdapter);
        interestGrid.setOnItemClickListener(onItemClick);

    }

    private void setItem() {
        interestItems = new ArrayList<InterestItem>();

        String[] title = {
                "พลังงาน", "สังคมผู้สูงอายุ", "ร่างกาย", "จิตใจ", "ประเด็นสังคม", "สิ่งประดิษฐ์", "คุณภาพชีวิต", "อาหารและการเกษตร",
                "ชุมชน", "ศิลปวัฒนธรรม", "การแพทย์", "เศรษฐกิจ", "การขนส่ง", "เทคโนโลยี", "สิ่งแวดล้อม", "ภูมิปัญญาไทย", "การออกแบบ",
                "กฎหมาย", "การศึกษา", "พระราชกรณียกิจ", "ความงาม", "สัตว์และพันธุ์พืช", "ภาษาและการสื่อสาร", "กีฬา", "วิทยาศาสตร์",
                "บันเทิง", "ต่างประเทศ"
        };

        String[] titleEng = {
                "Energy", "Ageing Society", "Body","Mind", "Social Issue", "Invention", "Quality of Life", "Food & Agriculture",
                "Community", "Art & Culture", "Medication", "Economy", "Transportation", "Technology", "Environment",
                "Thai Wisdom", "Design", "Law", "Education", "Royal Duties", "Beauty",
                "Life", "Communication", "Sport", "Science", "Entertainment", "International Issue"
        };

        int[] backgroundUrl = {
                //MOCK ONLY
                R.drawable.energy_bg, R.drawable.aging_society_bg, R.drawable.body_bg, R.drawable.mind_bg, R.drawable.social_bg,
                R.drawable.invention_bg, R.drawable.qlt_of_life_bg, R.drawable.food_agriculture_bg, R.drawable.community_bg,
                R.drawable.art_cult_bg, R.drawable.med_interest_bg, R.drawable.economy_bg, R.drawable.transport_bg, R.drawable.technology_bg,
                R.drawable.environment_bg, R.drawable.thai_wisdom_bg, R.drawable.design_bg, R.drawable.law_interest_bg, R.drawable.education_bg,
                R.drawable.royal_duties_bg, R.drawable.beauty_bg, R.drawable.life_bg, R.drawable.communication_bg, R.drawable.sport_bg,
                R.drawable.science_bg, R.drawable.entertainment_bg, R.drawable.international_issue_bg

        };

        int[] iconUrl = {
                R.drawable.int_energy, R.drawable.int_aging, R.drawable.int_body, R.drawable.int_mind, R.drawable.int_social_issue,
                R.drawable.int_invention, R.drawable.int_qlt_of_life, R.drawable.int_food, R.drawable.int_community, R.drawable.int_art_cult,
                R.drawable.int_med, R.drawable.int_economy, R.drawable.int_transport, R.drawable.int_tech, R.drawable.int_environ,
                R.drawable.int_thai_wis, R.drawable.int_design, R.drawable.int_law, R.drawable.int_education, R.drawable.int_royal,
                R.drawable.int_beauty, R.drawable.int_life, R.drawable.int_communication, R.drawable.int_sport, R.drawable.int_science,
                R.drawable.int_entertainment, R.drawable.int_inter

        };

        boolean[] isInterest = {
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false,
                false, false, false
        };

        for (int i = 0; i<title.length ; i++) {
            InterestItem interest = new InterestItem(title[i], titleEng[i], backgroundUrl[i], iconUrl[i], isInterest[i]);
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
            Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.container, new TagPageFragment());
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        }
    };

}
