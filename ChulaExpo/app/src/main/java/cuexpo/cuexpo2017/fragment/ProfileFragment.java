package cuexpo.cuexpo2017.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.FavouriteActivity;
import cuexpo.cuexpo2017.activity.LoginActivity;
import cuexpo.cuexpo2017.activity.ReservedActivity;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.DeleteResultDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.dao.UserResult;
import cuexpo.cuexpo2017.manager.DateConversionManager;
import cuexpo.cuexpo2017.manager.HttpManager;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private LinearLayout btnLogout;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvDescription;
    private TextView tvPlace;
    private TextView tvLogout;
    private ImageView ivQR;
    private ImageView ivProfile;
    private SharedPreferences sharedPref;
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

        access = !sharedPref.getString("fbToken", "").equals("");

        if (access) {
            setName(sharedPref.getString("name", ""));
            setEmail(sharedPref.getString("email", ""));
            Glide.with(this)
                    .load("http://graph.facebook.com/" + sharedPref.getString("id", "") + "/picture?type=large")
                    .placeholder(R.drawable.iv_profile_temp)
                    .error(R.drawable.iv_profile_temp)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(ivProfile);
        } else {
            tvLogout.setText("กลับไปหน้า Login");
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
        ivProfile = (ImageView) rootView.findViewById(R.id.profile_image);
        btnEdit = (Button) rootView.findViewById(R.id.profile_edit_profile_btn);
        btnFavourite = (LinearLayout) rootView.findViewById(R.id.profile_favourite_btn);
        btnReserved = (LinearLayout) rootView.findViewById(R.id.profile_reserved_btn);
        btnSetting = (LinearLayout) rootView.findViewById(R.id.profile_setting_btn);
        btnSetting2 = (LinearLayout) rootView.findViewById(R.id.profile_setting2_btn);
        btnFaq = (LinearLayout) rootView.findViewById(R.id.profile_faq_btn);
        btnAbout = (LinearLayout) rootView.findViewById(R.id.profile_about_btn);
        btnLogout = (LinearLayout) rootView.findViewById(R.id.profile_logout_btn);
        tvName = (TextView) rootView.findViewById(R.id.profile_name);
        tvEmail = (TextView) rootView.findViewById(R.id.profile_email);
        tvAge = (TextView) rootView.findViewById(R.id.profile_age);
        tvGender = (TextView) rootView.findViewById(R.id.profile_gender);
        tvDescription = (TextView) rootView.findViewById(R.id.profile_description);
        tvPlace = (TextView) rootView.findViewById(R.id.profile_place);
        tvLogout = (TextView) rootView.findViewById(R.id.profile_logout_tv);
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
        outState.putBoolean("access", access);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        access = savedInstanceState.getBoolean("access");
    }

    Callback<UserDao> callBackUserInfo = new Callback<UserDao>() {
        @Override
        public void onResponse(Call<UserDao> call, Response<UserDao> response) {
            if (response.isSuccessful()) {
                UserDao dao = response.body();
                Log.e("Profile Fragment", "MY NAME " + dao.getResults().getName());
                Log.e("Profile Fragment", "MY ACADEMIC " + dao.getResults().getAcademic());
                Log.e("Profile Fragment", "MY ACADEMIC YEAR " + dao.getResults().getAcademic().getAcademicYear());
                Log.e("Profile Fragment", "MY ACADEMIC LEVEL " + dao.getResults().getAcademic().getAcademicLevel());
                Log.e("Profile Fragment", "MY ACADEMIC SCHOOL " + dao.getResults().getAcademic().getAcademicSchool());
                Log.e("Profile Fragment", "MY TYPE " + dao.getResults().getType());
                Log.e("Profile Fragment", "MY WORKER " + dao.getResults().getWorker());
                Log.e("Profile Fragment", "MY EMAIL " + dao.getResults().getEmail());
                Log.e("Profile Fragment", "MY GENDER " + dao.getResults().getGender());
                Log.e("Profile Fragment", "MY AGE " + dao.getResults().getAge());
                if (dao.getResults().getType().equals("Academic")) {
                    setStudentDescription(sharedPref.getString("academicLevel", ""),
                            sharedPref.getString("academicYear", ""));
                    setPlace(sharedPref.getString("academicSchool", ""));
                } else if (dao.getResults().getType().equals("Worker")) {
                    setAdultDescription(sharedPref.getString("wokerJob", ""));
                }
                if (dao.getResults().getGender().equals("Male")) {
                    setGender("ชาย");
                } else if (dao.getResults().getGender().equals("Female")) {
                    setGender("เหญิง");
                } else if (dao.getResults().getGender().equals("Other")) {
                    setGender("เอื่นๆ");
                } else {
                    setGender("-");
                }
                if (dao.getResults().getAge() > 0) {
                    setAge(dao.getResults().getAge() + "");
                } else {
                    setAge("-");
                }
            } else {
                try {
                    Log.e("Profile Fragment", "Call Me Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<UserDao> call, Throwable t) {
            Log.e("Profile Fragment", "Call Me Fail");
        }
    };

    @Override
    public void onClick(View v) {
        if (v == ivQR) {
            /*if (!access) {
                error();
            } else {*/
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_overlay, new QRFragment());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //}
        } else if (v == btnFavourite) {
            Intent intent = new Intent(getActivity(), FavouriteActivity.class);
            getContext().startActivity(intent);

        } else if (v == btnReserved) {
            if (!access) {
                error("ดูการจอง Event ทั้งหมด");
            } else {
                Intent intent = new Intent(getActivity(), ReservedActivity.class);
                getContext().startActivity(intent);
            }
        } else if (v == btnEdit) {
            if (!access) {
                error("แก้ไขข้อมูล");
            } else {
                Call<UserDao> callUserInfo = HttpManager.
                        getInstance().getService().getUserInfo
                        ("name,_id,email,age,gender,profile,type,academic,academicLevel,academicYear,academicSchool");
                callUserInfo.enqueue(callBackUserInfo);
                //comingSoon();
            }
        } else if (v == btnSetting) {
            if (!access) {
                error("แก้ไขเนื้อหาที่สนใจ");
            } else {
                if(sharedPref.getString("apiToken", "").equals("")) error("แก้ไขเนื้อหาที่สนใจ");
                else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.main_overlay, EditInterestFragment.getInstance());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        } else if (v == btnSetting2) {
            if (!access) {
                error("แก้ไขคณะที่สนใจ");
            } else {
                comingSoon();
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
            SharedPreferences.Editor editor = sharedPref.edit();
            String facebookId = sharedPref.getString("id", "");
            editor.putString("fbToken", "");
            editor.putString("apiToken", "");
            editor.putString("id", "");
            editor.putString("name", "");
            editor.putString("email", "");
            editor.putString("gender", "");
            editor.putString("age", "");
            editor.putString("academicYear", "");
            editor.putString("academicSchool", "");
            editor.putString("academicLevel", "");
            editor.putString("workerJob", "");
            editor.putString("type", "");
            editor.apply();

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + facebookId + "/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }).executeAsync();
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

    public void comingSoon() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Coming Soon");
        alert.setMessage("พบกับฟังก์ชันนี้เร็ว ๆ นี้ ...");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert2 = alert.create();
        alert2.show();
    }

    public void error(String errorMsg) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("ขออภัย");
        alert.setMessage("ฟังก์ชัน" + errorMsg + "เปิดให้เฉพาะ Facebook User เท่านั้น!");
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
