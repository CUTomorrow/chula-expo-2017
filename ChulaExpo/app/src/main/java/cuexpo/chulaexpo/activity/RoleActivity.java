package cuexpo.chulaexpo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RoleActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStudent,btnAdult;

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
        btnStudent = (Button) findViewById(R.id.btnStudent);
        btnAdult = (Button) findViewById(R.id.btnAdult);
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
}
