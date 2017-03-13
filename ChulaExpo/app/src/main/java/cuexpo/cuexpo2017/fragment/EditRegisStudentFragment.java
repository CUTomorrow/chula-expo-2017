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

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.EditAdultUser;
import cuexpo.cuexpo2017.dao.EditStudentUser;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APTX-4869 (LOCAL) on 3/13/2017.
 */

public class EditRegisStudentFragment extends Fragment implements TextWatcher {

    private static EditRegisStudentFragment instance;
    EditText  etRegisName,etEmail, etBirth,etSchool;
    Spinner   spGender, spAcademicYear, spAcademicLevel;
    View    btnNext, rootView;
    ImageView ivRegisProfile;
    String id,name,email,gender, profile,academicLevel, academicYear, academicSchool;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String[] academicYear1List, academicLevelList, academicYear2List, academicYear3List, academicYear4List, genderList;
    ArrayAdapter<String> adapterGender, adapterAcademicLevel ,adapterAcademicYear1, adapterAcademicYear2, adapterAcademicYear3, adapterAcademicYear4;
    int age;

    public static EditRegisStudentFragment newInstance() {
        instance = new EditRegisStudentFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    public static EditRegisStudentFragment getInstance(){
        if(instance == null) return newInstance();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_regis_student_edit, container, false);

        rootView.findViewById(R.id.btnCancel).setOnClickListener(cancelOCL);
        rootView.findViewById(R.id.btnSave).setOnClickListener(saveOCL);

        //get SharedPref
        sharedPref = getActivity().getSharedPreferences("FacebookInfo", getContext().MODE_PRIVATE);
        profile = sharedPref.getString("profile","");
        name = sharedPref.getString("name", "");
        email = sharedPref.getString("email", "");
        gender = sharedPref.getString("gender", "Male");
        age = sharedPref.getInt("age",0);
        academicLevel = sharedPref.getString("academicLevel","");
        academicYear = sharedPref.getString("academicYear","");
        academicSchool = sharedPref.getString("academicSchool","");

        //birthday = sharedPref.getString("birthday", "");
        //editor.putString("type","Worker");
        //editor.putString("profile","http://graph.facebook.com/"+id+"/picture?type=large");

        etRegisName.setText(name);
        etEmail.setText(email);
        etBirth.setText(age);


        etRegisName.addTextChangedListener(this);
        //etEmail.addTextChangedListener(this);
        etBirth.addTextChangedListener(this);
        etSchool.addTextChangedListener(this);


        //Load Image
        Glide.with(this)
                .load(profile)
                .placeholder(R.drawable.iv_profile_temp)
                .error(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivRegisProfile);
        //Spinner
        genderList = getResources().getStringArray(R.array.gender);
        academicLevelList = getResources().getStringArray(R.array.academicLevel);
        academicYear1List = getResources().getStringArray(R.array.academicYear1);
        academicYear2List = getResources().getStringArray(R.array.academicYear2);
        academicYear3List = getResources().getStringArray(R.array.academicYear3);
        academicYear4List = getResources().getStringArray(R.array.academicYear4);

        adapterGender = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, genderList);
        adapterAcademicLevel = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, academicLevelList);
        adapterAcademicYear1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, academicYear1List);
        adapterAcademicYear2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, academicYear2List);
        adapterAcademicYear3 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, academicYear3List);
        adapterAcademicYear4 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, academicYear4List);

        spGender.setAdapter(adapterGender);
        if(gender.equals("Male"))spGender.setSelection(0,true);
        else if(gender.equals("Female"))spGender.setSelection(1,true);
        else spGender.setSelection(2,true);
        View spinnerSelectedView = spGender.getSelectedView();
        ((TextView)spinnerSelectedView).setTextColor(Color.WHITE);

        spAcademicLevel.setAdapter(adapterAcademicLevel);
        spAcademicLevel.setSelection(1,true);
        for(int i=0;i<academicLevelList.length-1;i++){
            if(academicLevel.equals(academicLevelList[i])){
                spAcademicLevel.setSelection(i,true);
            }
        }
        View spAcademicLevelSelectedView = spAcademicLevel.getSelectedView();
        ((TextView)spAcademicLevelSelectedView).setTextColor(Color.WHITE);

        spAcademicYear.setAdapter(adapterAcademicYear2);
        spAcademicYear.setSelection(0,true);
        if(spAcademicLevel.getSelectedItemPosition() == 0){
            spAcademicYear.setAdapter(adapterAcademicYear1);
            spAcademicYear.setSelection(0,true);
            for(int i=0;i<academicYear1List.length-1;i++){
                if(academicYear.equals(academicYear1List[i])){
                    spAcademicYear.setSelection(i,true);
                }
            }
        } else if(spAcademicLevel.getSelectedItemPosition() == 1) {
            spAcademicYear.setAdapter(adapterAcademicYear2);
            spAcademicYear.setSelection(0,true);
            for(int i=0;i<academicYear2List.length-1;i++){
                if(academicYear.equals(academicYear2List[i])){
                    spAcademicYear.setSelection(i,true);
                }
            }
        }else if(spAcademicLevel.getSelectedItemPosition() == 2) {
            spAcademicYear.setAdapter(adapterAcademicYear3);
            spAcademicYear.setSelection(0,true);
            for(int i=0;i<academicYear3List.length-1;i++){
                if(academicYear.equals(academicYear3List[i])){
                    spAcademicYear.setSelection(i,true);
                }
            }
        }else if(spAcademicLevel.getSelectedItemPosition() == 3) {
            spAcademicYear.setAdapter(adapterAcademicYear4);
            spAcademicYear.setSelection(0,true);
            for(int i=0;i<academicYear4List.length-1;i++){
                if(academicYear.equals(academicYear4List[i])){
                    spAcademicYear.setSelection(i,true);
                }
            }
        } else {
            spAcademicYear.setAdapter(adapterAcademicYear3);
            spAcademicYear.setSelection(0,true);
            for(int i=0;i<academicYear3List.length-1;i++){
                if(academicYear.equals(academicYear3List[i])){
                    spAcademicYear.setSelection(i,true);
                }
            }
        }
        View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
        ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);

        return rootView;
    }

    private void initInstances() {
        etRegisName = (EditText)rootView.findViewById(R.id.etRegisName);
        etEmail = (EditText)rootView.findViewById(R.id.etEmail);
        etBirth = (EditText) rootView.findViewById(R.id.etBirth);
        etSchool = (EditText) rootView.findViewById(R.id.etSchool);
        spGender = (Spinner) rootView.findViewById(R.id.spGender);
        spAcademicLevel = (Spinner) rootView.findViewById(R.id.spAcademicLevel);
        spAcademicYear = (Spinner) rootView.findViewById(R.id.spAcademicYear);
        ivRegisProfile = (ImageView) rootView.findViewById(R.id.ivRegisProfile);
        btnNext = rootView.findViewById(R.id.btnNext);
        spGender.setOnItemSelectedListener(spGenderlistener);
        spAcademicLevel.setOnItemSelectedListener(spAcademicLevelListener);
        spAcademicYear.setOnItemSelectedListener(spAcademicYearListener);
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
            name = etRegisName.getText().toString();
            try {
                age = Integer.parseInt(etBirth.getText().toString());
            } catch (NumberFormatException exception){
                age = 0;
            }
            academicSchool = etSchool.getText().toString();

            EditStudentUser editStudentUser = new EditStudentUser(name,age,gender,academicLevel,academicYear,academicSchool);
            Call<UserDao> callUserInfo = HttpManager.getInstance().getService().editStudentUserInfo(editStudentUser);
            callUserInfo.enqueue(callbackUserInfo);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    };

    Callback<UserDao> callbackUserInfo = new Callback<UserDao>() {
        @Override
        public void onResponse(Call<UserDao> call, Response<UserDao> response) {
            if (response.isSuccessful()) {
                UserDao dao2 = response.body();
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

    AdapterView.OnItemSelectedListener spAcademicLevelListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View spinnerSelectedView = spAcademicLevel.getSelectedView();
            ((TextView) spinnerSelectedView)
                    .setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.dark_blue));
            academicLevel = academicLevelList[spAcademicLevel.getSelectedItemPosition()];
            if(spAcademicLevel.getSelectedItemPosition() == 0){
                spAcademicYear.setAdapter(adapterAcademicYear1);
                spAcademicYear.setSelection(0,true);
                View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
                ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
            } else if(spAcademicLevel.getSelectedItemPosition() == 1) {
                spAcademicYear.setAdapter(adapterAcademicYear2);
                spAcademicYear.setSelection(0,true);
                View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
                ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
            }else if(spAcademicLevel.getSelectedItemPosition() == 2) {
                spAcademicYear.setAdapter(adapterAcademicYear3);
                spAcademicYear.setSelection(0,true);
                View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
                ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
            }else if(spAcademicLevel.getSelectedItemPosition() == 3) {
                spAcademicYear.setAdapter(adapterAcademicYear4);
                spAcademicYear.setSelection(0,true);
                View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
                ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
            } else {
                spAcademicYear.setAdapter(adapterAcademicYear3);
                spAcademicYear.setSelection(0,true);
                View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
                ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener spAcademicYearListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View spinnerSelectedView = spAcademicYear.getSelectedView();
            ((TextView) spinnerSelectedView)
                    .setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.dark_blue));
            if(spAcademicLevel.getSelectedItemPosition() == 0) academicYear = academicYear1List[spAcademicYear.getSelectedItemPosition()];
            else if(spAcademicLevel.getSelectedItemPosition() == 1) academicYear =  academicYear2List[spAcademicYear.getSelectedItemPosition()];
            else if(spAcademicLevel.getSelectedItemPosition() == 2) academicYear = academicYear3List[spAcademicYear.getSelectedItemPosition()];
            else if(spAcademicLevel.getSelectedItemPosition() == 3) academicYear = academicYear4List[spAcademicYear.getSelectedItemPosition()];
            else academicYear = academicYear3List[spAcademicYear.getSelectedItemPosition()];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
