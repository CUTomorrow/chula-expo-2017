package cuexpo.chulaexpo2017.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo2017.R;
import cuexpo.chulaexpo2017.fragment.StageFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        int stageNo  = getIntent().getExtras().getInt("stageNo");
        String stageId  = getIntent().getExtras().getString("stageId");
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stage_container, StageFragment.newInstance(stageNo, stageId))
                    .commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
