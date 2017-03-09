package cuexpo.chulaexpo2017.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cuexpo.chulaexpo2017.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RoleActivity extends AppCompatActivity implements View.OnClickListener {

    View btnStudent, btnAdult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initInstances();

        //Click Action
        btnStudent.setOnClickListener(this);
        btnAdult.setOnClickListener(this);

    }

    private void initInstances() {
        btnStudent = findViewById(R.id.btnStudent);
        btnAdult = findViewById(R.id.btnAdult);
    }


    @Override
    public void onClick(View v) {
        if(v == btnStudent){
            Intent intent = new Intent(this, RegisStudentActivity.class);
            startActivity(intent);
        }
        else if(v == btnAdult){
            Intent intent = new Intent(this, RegisAdultActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
