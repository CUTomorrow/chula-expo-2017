package cuexpo.chulaexpo.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import org.w3c.dom.Text;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.activity.DoneRegisterActivity;
import cuexpo.chulaexpo.activity.FavouriteActivity;
import cuexpo.chulaexpo.activity.LoginActivity;
import cuexpo.chulaexpo.activity.ReservedActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnEdit;
    private LinearLayout btnFavourite;
    private LinearLayout btnReserved;
    private LinearLayout btnSetting;
    private LinearLayout btnSetting2;
    private LinearLayout btnFaq;
    private LinearLayout btnAbout;
    private Button btnLogout;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvDescription;
    private TextView tvPlace;
    private ImageView ivQR;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private boolean access;

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
        ivQR.setOnClickListener(this);
        btnFavourite.setOnClickListener(this);
        btnReserved.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSetting2.setOnClickListener(this);
        btnFaq.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        sharedPref = getContext().getSharedPreferences("FacebookInfo", getContext().MODE_PRIVATE);
        editor = sharedPref.edit();
        access = !sharedPref.getString("id", "").equals("");


        if (access) {
            setName(sharedPref.getString("name", ""));
            setEmail(sharedPref.getString("email", ""));
            setGender(sharedPref.getString("gender", ""));
            setAge(sharedPref.getString("birthday", ""));
            if (sharedPref.getInt("role", 0) == 1) {
                setStudentDescription(sharedPref.getString("year", ""), "");
                setPlace(sharedPref.getString("school", ""));
            } else {
                setAdultDescription(sharedPref.getString("company", ""));
                setPlace(sharedPref.getString("company", ""));
            }
        }


        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        ivQR = (ImageView) rootView.findViewById(R.id.profile_toolbar_qr);
        btnEdit = (Button) rootView.findViewById(R.id.profile_edit_profile_btn);
        btnFavourite = (LinearLayout) rootView.findViewById(R.id.profile_favourite_btn);
        btnReserved = (LinearLayout) rootView.findViewById(R.id.profile_reserved_btn);
        btnSetting = (LinearLayout) rootView.findViewById(R.id.profile_setting_btn);
        btnSetting2 = (LinearLayout) rootView.findViewById(R.id.profile_setting2_btn);
        btnFaq = (LinearLayout) rootView.findViewById(R.id.profile_faq_btn);
        btnAbout = (LinearLayout) rootView.findViewById(R.id.profile_about_btn);
        btnLogout = (Button) rootView.findViewById(R.id.profile_logout_btn);
        tvName = (TextView) rootView.findViewById(R.id.profile_name);
        tvEmail = (TextView) rootView.findViewById(R.id.profile_email);
        tvAge = (TextView) rootView.findViewById(R.id.profile_age);
        tvGender = (TextView) rootView.findViewById(R.id.profile_gender);
        tvDescription = (TextView) rootView.findViewById(R.id.profile_description);
        tvPlace = (TextView) rootView.findViewById(R.id.profile_place);
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
        if (v == ivQR) {
            if (!access) {
                error();
            } else {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.main_overlay, QRFragment.newInstance());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else if (v == btnFavourite) {
            if (!access) {
                error();
            } else {
                Intent intent = new Intent(getActivity(), FavouriteActivity.class);
                getContext().startActivity(intent);
            }
        } else if (v == btnReserved) {
            if (!access) {
                error();
            } else {
                Intent intent = new Intent(getActivity(), ReservedActivity.class);
                getContext().startActivity(intent);
            }
        } else if (v == btnEdit) {
            if (!access) {
                error();
            }
        } else if (v == btnSetting) {
            if (!access) {
                error();
            }
        } else if (v == btnSetting2) {
            if (!access) {
                error();
            }
        } else if (v == btnFaq) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_overlay, FaqFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (v == btnAbout) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_overlay, AboutFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (v == btnLogout) {
            editor.putString("id", "");
            editor.putString("name", "");
            editor.putString("email", "");
            editor.putString("gender", "");
            editor.putString("birthday", "");
            editor.putString("year", "");
            editor.putString("school", "");
            editor.putString("company", "");
            editor.putInt("role", 0);
            editor.commit();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public void setName(String text) {
        tvName.setText(text);
    }

    public void setEmail(String text) {
        tvEmail.setText(text);
    }

    public void setAge(String text) {
        String age = "อายุ " + text;
        tvAge.setText(age);
    }

    public void setGender(String text) {
        String gender = "เพศ " + text;
        tvGender.setText(gender);
    }

    public void setStudentDescription(String text, String text2) {
        String description = text + " " + text2;
        tvDescription.setText(description);
    }

    public void setAdultDescription(String text) {
        tvDescription.setText(text);
    }

    public void setPlace(String text) {
        tvPlace.setText(text);
    }

    public void error() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("ขออภัย");
        alert.setMessage("ฟังก์ชันแก้ไขข้อมูลเเปิดให้เฉพาะ Facebook User เท่านั้น!");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert2 = alert.create();
        alert2.show();
    }

}
