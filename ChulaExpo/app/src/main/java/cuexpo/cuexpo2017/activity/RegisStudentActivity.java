package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import cuexpo.cuexpo2017.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisStudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  etRegisName,etEmail, etBirth,etSchool;
    Spinner   spGender, spAcademicYear, spAcademicLevel;
    View    btnNext;
    ImageView ivRegisProfile;
    String id,name,email,gender;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String[] academicYear1List, academicLevelList, academicYear2List, academicYear3List, academicYear4List, genderList;
    ArrayAdapter<String> adapterGender, adapterAcademicLevel ,adapterAcademicYear1, adapterAcademicYear2, adapterAcademicYear3, adapterAcademicYear4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_student);
        initInstances();

        //TODO:Resize for fragmentation
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //Click Action
        btnNext.setOnClickListener(this);


        //get SharedPref
        sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
        editor = sharedPref.edit();
        id = sharedPref.getString("id","");
        name = sharedPref.getString("name","");
        email = sharedPref.getString("email","");
        gender = sharedPref.getString("gender","Male");
        //birthday = sharedPref.getString("birthday","");
        editor.putString("type","Academic");
        //editor.putString("academicYear",etYear.getText().toString());
        editor.putString("academicSchool",etSchool.getText().toString());
        editor.putString("profile","http://graph.facebook.com/"+id+"/picture?type=large");
        editor.commit();

        etRegisName.setText(name);
        etEmail.setText(email);
        //etBirth.setText(birthday);

        etRegisName.addTextChangedListener(textWatcher);
        etEmail.addTextChangedListener(textWatcher);
        etBirth.addTextChangedListener(textWatcher);
        etSchool.addTextChangedListener(textWatcher);

        //Load Image
        Glide.with(this)
                .load("http://graph.facebook.com/"+id+"/picture?type=large")
                .placeholder(R.drawable.iv_profile_temp)
                .error(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivRegisProfile);
        //Spinner
        genderList = getResources().getStringArray(R.array.gender);
        academicLevelList = getResources().getStringArray(R.array.academicLevel);
        academicYear1List = getResources().getStringArray(R.array.academicYear1);
        academicYear2List = getResources().getStringArray(R.array.academicYear2);
        academicYear3List = getResources().getStringArray(R.array.academicYear3);
        academicYear4List = getResources().getStringArray(R.array.academicYear4);

        adapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, genderList);
        adapterAcademicLevel = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, academicLevelList);
        adapterAcademicYear1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, academicYear1List);
        adapterAcademicYear2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, academicYear2List);
        adapterAcademicYear3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, academicYear3List);
        adapterAcademicYear4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, academicYear4List);

        spGender.setAdapter(adapterGender);
        spGender.setSelection(gender.equals("Male")? 0 : 1,true);
        View spinnerSelectedView = spGender.getSelectedView();
        ((TextView)spinnerSelectedView).setTextColor(Color.WHITE);

        spAcademicLevel.setAdapter(adapterAcademicLevel);
        spAcademicLevel.setSelection(1,true);
        View spAcademicLevelSelectedView = spAcademicLevel.getSelectedView();
        ((TextView)spAcademicLevelSelectedView).setTextColor(Color.WHITE);

        spAcademicYear.setAdapter(adapterAcademicYear2);
        spAcademicYear.setSelection(0,true);
        View spAcademicYearSelectedView = spAcademicYear.getSelectedView();
        ((TextView)spAcademicYearSelectedView).setTextColor(Color.WHITE);
    }

    private void initInstances() {
        etRegisName = (EditText)findViewById(R.id.etRegisName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etBirth = (EditText) findViewById(R.id.etBirth);
        etSchool = (EditText) findViewById(R.id.etSchool);
        spGender = (Spinner) findViewById(R.id.spGender);
        spAcademicLevel = (Spinner) findViewById(R.id.spAcademicLevel);
        spAcademicYear = (Spinner) findViewById(R.id.spAcademicYear);
        ivRegisProfile = (ImageView) findViewById(R.id.ivRegisProfile);
        btnNext = findViewById(R.id.btnNext);
        spGender.setOnItemSelectedListener(spGenderlistener);
        spAcademicLevel.setOnItemSelectedListener(spAcademicLevelListener);
        spAcademicYear.setOnItemSelectedListener(spAcademicYearListener);
    }


    @Override
    public void onClick(View v) {
        if(v == btnNext){
//            Intent intent = new Intent(this, InterestActivity.class);
            Intent intent = new Intent(this, DoneRegisterActivity.class);
            startActivity(intent);
            RegisStudentActivity.this.finish();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString("name", etRegisName.getText().toString());
            editor.putString("email", etEmail.getText().toString());
            try{
            editor.putInt("age",Integer.parseInt(etBirth.getText().toString()));
            } catch (NumberFormatException exception){
                editor.putInt("age", 0);
            }
            editor.putString("academicSchool", etSchool.getText().toString());
            //editor.putString("year", etYear.getText().toString());
            editor.commit();
        }
    };

    AdapterView.OnItemSelectedListener spGenderlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View spinnerSelectedView = spGender.getSelectedView();
            ((TextView)spinnerSelectedView)
                .setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(),R.color.dark_blue));
            String gen = genderList[spGender.getSelectedItemPosition()];
            if(gen.equals("ชาย")) gen = "Male";
            else if(gen.equals("หญิง")) gen = "Female";
            else gen = "Other";
            editor.putString("gender",gen);
            editor.commit();
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
            editor.putString("academicLevel", academicLevelList[spAcademicLevel.getSelectedItemPosition()]);
            editor.commit();
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
            if(spAcademicLevel.getSelectedItemPosition() == 0) editor.putString("academicYear", academicYear1List[spAcademicYear.getSelectedItemPosition()]);
            else if(spAcademicLevel.getSelectedItemPosition() == 1) editor.putString("academicYear", academicYear2List[spAcademicYear.getSelectedItemPosition()]);
            else if(spAcademicLevel.getSelectedItemPosition() == 2) editor.putString("academicYear", academicYear3List[spAcademicYear.getSelectedItemPosition()]);
            else if(spAcademicLevel.getSelectedItemPosition() == 3) editor.putString("academicYear", academicYear4List[spAcademicYear.getSelectedItemPosition()]);
            else editor.putString("academicYear", academicYear3List[spAcademicYear.getSelectedItemPosition()]);
            editor.commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
