package cuexpo.chulaexpo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.StageFragment;

public class StageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stage_container, StageFragment.newInstance())
                    .commit();
        }
    }
}
