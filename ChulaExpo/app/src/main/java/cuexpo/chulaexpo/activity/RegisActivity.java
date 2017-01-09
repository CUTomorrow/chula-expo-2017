package cuexpo.chulaexpo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cuexpo.chulaexpo.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RegisActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  etRegisName,etEmail,etAge,etWorkingPlace,etYear;
    Spinner   spGender;
    TextView  tvWorkingPlace,tvYear;
    Button btnStudent,btnWorker,btnNext;
    ImageView ivRegisProfile,ivCheckCircle,ivCheckCircle2;
    int currentPeople;
    public static final int STUDENT = 1;
    public static final int WORKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        initInstances();

        //TODO:Resize for fragmentation
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        btnNext.setWidth(width/2);
        //Click Action
        btnStudent.setOnClickListener(this);
        btnWorker.setOnClickListener(this);
        btnNext.setOnClickListener(this);


        //Load Image
        Glide.with(this)
                .load(R.drawable.iv_profile)
                .placeholder(R.drawable.iv_profile)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivRegisProfile);
        //Spinner
        String[] genderList = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, genderList);
        spGender.setAdapter(adapterGender);
    }

    private void initInstances() {
        etRegisName = (EditText)findViewById(R.id.etRegisName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etAge = (EditText) findViewById(R.id.etAge);
        etWorkingPlace = (EditText) findViewById(R.id.etWorkingPlace);
        etYear = (EditText)findViewById(R.id.etYear);
        spGender = (Spinner) findViewById(R.id.spGender);
        tvWorkingPlace = (TextView) findViewById(R.id.tvWorkingPlace);
        tvYear = (TextView)findViewById(R.id.tvYear);
        btnStudent = (Button) findViewById(R.id.btnStudent);
        btnWorker = (Button) findViewById(R.id.btnWorker);
        ivRegisProfile = (ImageView) findViewById(R.id.ivRegisProfile);
        btnNext = (Button)findViewById(R.id.btnNext);
        currentPeople = STUDENT;

    }


    @Override
    public void onClick(View v) {
        if(v == btnStudent && currentPeople == WORKER){
            currentPeople = STUDENT;
            btnWorker.setBackgroundColor(Color.TRANSPARENT);
            btnWorker.setTextColor(Color.WHITE);
            btnWorker.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            btnStudent.setBackgroundResource(R.drawable.shape_rounded_rectangle_white);
            btnStudent.setTextColor(Color.BLACK);
            btnStudent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_pink,0,0,0);
            tvWorkingPlace.setText("SCHOOL/UNIVERSITY");
            etWorkingPlace.setText("Chulalongkorn University");
            tvYear.setText("GRADE/YEAR");
            etYear.setText("4");
        }
        else if(v == btnWorker && currentPeople == STUDENT){
            currentPeople = WORKER;
            btnStudent.setBackgroundColor(Color.TRANSPARENT);
            btnStudent.setTextColor(Color.WHITE);
            btnStudent.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            btnWorker.setBackgroundResource(R.drawable.shape_rounded_rectangle_white);
            btnWorker.setTextColor(Color.BLACK);
            btnWorker.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_pink,0,0,0);
            tvWorkingPlace.setText("COMPANY/ORGANIZATION");
            etWorkingPlace.setText("Example Company");
            tvYear.setText("POSITION");
            etYear.setText("Software Engineer");
        }
        if(v == btnNext){
            Intent intent = new Intent(this, InterestActivity.class);
            startActivity(intent);
        }
    }
}
