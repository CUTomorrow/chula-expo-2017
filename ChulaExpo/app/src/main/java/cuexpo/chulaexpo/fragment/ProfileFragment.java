package cuexpo.chulaexpo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.activity.FavouriteActivity;
import cuexpo.chulaexpo.activity.ReservedActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button btnEdit;
    LinearLayout btnFavourite;
    LinearLayout btnReserved;
    LinearLayout btnSetting;
    LinearLayout btnSetting2;
    LinearLayout btnFaq;
    LinearLayout btnAbout;
    //Button btnFb;
    Button btnLogout;
    TextView tvName;
    TextView tvSurname;
    TextView tvEmail;
    TextView tvYear;
    TextView tvSchool;
    TextView tvAge;
    TextView tvGender;

    public ProfileFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView, savedInstanceState);
        btnFavourite.setOnClickListener(this);
        btnReserved.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSetting2.setOnClickListener(this);
        btnFaq.setOnClickListener(this);
        //btnFb.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnEdit = (Button) rootView.findViewById(R.id.profile_edit_profile_btn);

        btnFavourite = (LinearLayout) rootView.findViewById(R.id.profile_favourite_btn);
        btnReserved = (LinearLayout) rootView.findViewById(R.id.profile_reserved_btn);
        btnSetting = (LinearLayout) rootView.findViewById(R.id.profile_setting_btn);
        btnSetting2 = (LinearLayout) rootView.findViewById(R.id.profile_setting2_btn);
        btnFaq = (LinearLayout) rootView.findViewById(R.id.profile_faq_btn);
        btnAbout = (LinearLayout) rootView.findViewById(R.id.profile_about_btn);

        //btnFb = (Button) rootView.findViewById(R.id.profile_fb_btn);
        btnLogout = (Button) rootView.findViewById(R.id.profile_logout_btn);
        tvName = (TextView) rootView.findViewById(R.id.profile_name);
        tvSurname= (TextView) rootView.findViewById(R.id.profile_surname);
        tvEmail = (TextView) rootView.findViewById(R.id.profile_email);
        tvYear = (TextView) rootView.findViewById(R.id.profile_year);
        tvSchool = (TextView) rootView.findViewById(R.id.profile_school);
        tvAge = (TextView) rootView.findViewById(R.id.profile_age);
        tvGender = (TextView) rootView.findViewById(R.id.profile_gender);
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
        if (v == btnFavourite) {
            Intent intent = new Intent(getActivity(), FavouriteActivity.class);
            getContext().startActivity(intent);
        } else if (v == btnReserved) {
            Intent intent = new Intent(getActivity(), ReservedActivity.class);
            getContext().startActivity(intent);
        } else if (v == btnEdit) {

        } else if (v == btnSetting) {

        } else if (v == btnSetting2) {

        }/*else if( v== btnFb){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1053263171395979"));
                startActivity(intent);
            } catch (Exception e) {
                Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1053263171395979"));
                startActivity(intent);
            }

        }*/else if( v== btnFaq){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_overlay, FaqFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else if( v== btnAbout){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_overlay, AboutFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            /*final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.fragment_about);
            dialog.setCancelable(true);
            dialog.show();*/

        }else if( v== btnLogout){

        }
    }

    public void setName(String text){
        tvName.setText(text);
    }

    public void setSurname(String text){
        tvSurname.setText(text);
    }

    public void setEmail(String text){
        tvEmail.setText(text);
    }

    public void setAge(String text){
        tvAge.setText(text);
    }

    public void setGender(String text){
        tvGender.setText(text);
    }

    public void setYear(String text){
        tvYear.setText(text);
    }

    public void setSchool(String text){
        tvSchool.setText(text);
    }


}
