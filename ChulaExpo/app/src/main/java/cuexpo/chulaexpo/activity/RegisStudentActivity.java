package cuexpo.chulaexpo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cuexpo.chulaexpo.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisStudentActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText  etRegisName,etEmail, etBirth,etSchool,etYear;
    Spinner   spGender;
    View    btnNext;
    ImageView ivRegisProfile;
    String id,name,email,gender,birthday;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public static final int STUDENT = 1;
    public static final int ADULT = 2;

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
        gender = sharedPref.getString("gender","male");
        birthday = sharedPref.getString("birthday","");
        editor.putInt("role",STUDENT);
        editor.putString("year",etYear.getText().toString());
        editor.putString("school",etSchool.getText().toString());
        editor.commit();

        etRegisName.setText(name);
        etEmail.setText(email);
        etBirth.setText(birthday);

        etRegisName.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etBirth.addTextChangedListener(this);
        etSchool.addTextChangedListener(this);
        etYear.addTextChangedListener(this);

        //Load Image
        Glide.with(this)
                .load("http://graph.facebook.com/"+id+"/picture?type=large")
                .placeholder(R.drawable.iv_profile_temp)
                .error(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivRegisProfile);
        //Spinner
        String[] genderList = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, genderList);
        spGender.setAdapter(adapterGender);
        spGender.setSelection(gender.equals("male")? 0 : 1,true);
        View spinnerSelectedView = spGender.getSelectedView();
        ((TextView)spinnerSelectedView).setTextColor(Color.WHITE);
    }

    private void initInstances() {
        etRegisName = (EditText)findViewById(R.id.etRegisName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etBirth = (EditText) findViewById(R.id.etBirth);
        etSchool = (EditText) findViewById(R.id.etSchool);
        etYear = (EditText)findViewById(R.id.etYear);
        spGender = (Spinner) findViewById(R.id.spGender);
        ivRegisProfile = (ImageView) findViewById(R.id.ivRegisProfile);
        btnNext = findViewById(R.id.btnNext);
        spGender.setOnItemSelectedListener(spGenderlistener);
    }


    @Override
    public void onClick(View v) {
        if(v == btnNext){
            Intent intent = new Intent(this, InterestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s == etRegisName) editor.putString("name",etRegisName.toString());
        if(s == etEmail) editor.putString("email",etEmail.toString());
        if(s == etBirth) editor.putString("birthday",etBirth.toString());
        if(s == etSchool) editor.putString("school",etSchool.toString());
        if(s == etYear) editor.putString("year",etYear.toString());
        editor.commit();
    }

    AdapterView.OnItemSelectedListener spGenderlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View spinnerSelectedView = spGender.getSelectedView();
            ((TextView)spinnerSelectedView).setTextColor(Color.WHITE);
            editor.putString("gender",spGender.getSelectedItemPosition() == 0? "male":"female");
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
