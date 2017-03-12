package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;
import cuexpo.cuexpo2017.fragment.ReservedFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReservedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reserved_acitivity_content_container, new ReservedFragment())
                    .commit();

        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
