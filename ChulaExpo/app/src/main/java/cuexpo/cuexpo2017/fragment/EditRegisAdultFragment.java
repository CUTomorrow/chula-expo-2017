package cuexpo.cuexpo2017.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.adapter.InterestListAdapter;
import cuexpo.cuexpo2017.dao.EditAdultUser;
import cuexpo.cuexpo2017.dao.TagWrapper;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.datatype.InterestItem;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.Resource;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APTX-4869 (LOCAL) on 3/13/2017.
 */

public class EditRegisAdultFragment extends Fragment implements TextWatcher {

    private static EditRegisAdultFragment instance;
    EditText etRegisName,etEmail, etBirth, etCareer;
    Spinner spGender;
    View btnNext;
    ImageView ivRegisProfile;
    String id,name,email,gender,profile, workerJob, career;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String[] genderList;
    View rootView;
    int age;

    public static EditRegisAdultFragment newInstance() {
        instance = new EditRegisAdultFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    public static EditRegisAdultFragment getInstance(){
        if(instance == null) return newInstance();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_regis_adult_edit, container, false);
        rootView.setClickable(true);
        initInstances();
        rootView.findViewById(R.id.btnCancel).setOnClickListener(cancelOCL);
        rootView.findViewById(R.id.btnSave).setOnClickListener(saveOCL);

        //get SharedPref
        sharedPref = getActivity().getSharedPreferences("FacebookInfo", getContext().MODE_PRIVATE);
        profile = sharedPref.getString("profile","");
        name = sharedPref.getString("name", "");
        email = sharedPref.getString("email", "");
        gender = sharedPref.getString("gender", "Male");
        age = sharedPref.getInt("age",0);
        career = sharedPref.getString("workerJob","");
        Log.e("UserInfo","career:"+career);
        //birthday = sharedPref.getString("birthday", "");
        //editor.putString("type","Worker");
        //editor.putString("profile","http://graph.facebook.com/"+id+"/picture?type=large");

        etRegisName.setText(name);
        etEmail.setText(email);
        etBirth.setText(age+"");
        etCareer.setText(career);

        etRegisName.addTextChangedListener(this);
        //etEmail.addTextChangedListener(this);
        etBirth.addTextChangedListener(this);
        etCareer.addTextChangedListener(this);


        //Load Image
        Glide.with(this)
                .load(profile)
                .placeholder(R.drawable.iv_profile_temp)
                .error(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivRegisProfile);
        //Spinner
        genderList = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, genderList);
        spGender.setAdapter(adapterGender);
        if(gender.equals("Male"))spGender.setSelection(0,true);
        else if(gender.equals("Female"))spGender.setSelection(1,true);
        else spGender.setSelection(2,true);
        View spinnerSelectedView = spGender.getSelectedView();
        ((TextView)spinnerSelectedView).setTextColor(Color.WHITE);

        return rootView;
    }

    private void initInstances() {
        etRegisName = (EditText)rootView.findViewById(R.id.etRegisName);
        etEmail = (EditText)rootView.findViewById(R.id.etEmail);
        etBirth = (EditText) rootView.findViewById(R.id.etBirth);
        etCareer = (EditText) rootView.findViewById(R.id.etCareer);
        spGender = (Spinner) rootView.findViewById(R.id.spGender);
        ivRegisProfile = (ImageView) rootView.findViewById(R.id.ivRegisProfile);
        spGender.setOnItemSelectedListener(spGenderlistener);
    }

    View.OnClickListener cancelOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

    View.OnClickListener saveOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
            sharedPref = getActivity().getSharedPreferences("FacebookInfo", getContext().MODE_PRIVATE);
            editor = sharedPref.edit();
            name = etRegisName.getText().toString();
            try {
                age = Integer.parseInt(etBirth.getText().toString());
            } catch (NumberFormatException exception){
                age = 0;
            }
            workerJob = etCareer.getText().toString();

            //Save to sharedPref
            editor.putString("name",etRegisName.getText().toString());
            Log.d("regis","Name: "+etRegisName.getText().toString());
            editor.putString("email",etEmail.getText().toString());
            try {
                editor.putInt("age", Integer.parseInt(etBirth.getText().toString()));
            } catch (NumberFormatException exception){
                editor.putInt("age", 0);
            }
            editor.putString("gender",gender);
            editor.putString("workerJob",etCareer.getText().toString());
            editor.commit();

            //PUT API
            EditAdultUser editAdultUser = new EditAdultUser(name,age,gender,workerJob);
            Call<UserDao> callUserInfo = HttpManager.getInstance().getService().editAdultUserInfo(editAdultUser);
            callUserInfo.enqueue(callbackUserInfo);

            FragmentManager fragmentManager = getFragmentManager();
            for(Fragment fragment : fragmentManager.getFragments()){
                if(fragment instanceof ProfileFragment){
                    ((ProfileFragment) fragment).updateProfile();
                }
            }
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

    Callback<UserDao> callbackUserInfo = new Callback<UserDao>() {
        @Override
        public void onResponse(Call<UserDao> call, Response<UserDao> response) {
            if (response.isSuccessful()) {
                UserDao dao2 = response.body();
                Log.e("EditUser","worker:"+dao2.getResults().getWorker().getJob());
                Toast.makeText(Contextor.getInstance().getContext(), dao2.getSuccess() ? "Saved" : "Fail"
                        , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "Cannot save. Please try again.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<UserDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
            Log.e("EditProfile", t.toString());
        }
    };


    @Override
    public void onDestroy(){
        Log.d("EditProfile", "Clear Memory");
        instance = null;
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    AdapterView.OnItemSelectedListener spGenderlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View spinnerSelectedView = spGender.getSelectedView();
            ((TextView)spinnerSelectedView).
                    setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(),R.color.dark_blue));

            String gen = genderList[spGender.getSelectedItemPosition()];
            if(gen.equals("ชาย")) gender = "Male";
            else if(gen.equals("หญิง")) gender = "Female";
            else gender = "Other";
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
