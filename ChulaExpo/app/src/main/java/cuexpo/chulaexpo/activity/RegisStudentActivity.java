package cuexpo.chulaexpo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cuexpo.chulaexpo.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RegisStudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  etRegisName,etEmail, etBirth,etSchool,etYear;
    Spinner   spGender;
    Button    btnNext;
    ImageView ivRegisProfile;
    int currentPeople;
    public static final int STUDENT = 1;
    public static final int WORKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_student);
        initInstances();

        //TODO:Resize for fragmentation
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        btnNext.setWidth(width/2);
        //Click Action
        btnNext.setOnClickListener(this);


        //Load Image
        Glide.with(this)
                .load(R.drawable.iv_profile)
                .placeholder(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivRegisProfile);
        //Spinner
        String[] genderList = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, genderList);
        spGender.setAdapter(adapterGender);
        spGender.setSelection(0,true);
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
        btnNext = (Button)findViewById(R.id.btnNext);
        currentPeople = STUDENT;
    }


    @Override
    public void onClick(View v) {
        if(v == btnNext){
            Intent intent = new Intent(this, InterestActivity.class);
            startActivity(intent);
        }
    }
}
