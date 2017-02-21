package cuexpo.chulaexpo.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.StageFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        int stage  = getIntent().getExtras().getInt("stageNo");
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stage_container, StageFragment.newInstance(stage))
                    .commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
