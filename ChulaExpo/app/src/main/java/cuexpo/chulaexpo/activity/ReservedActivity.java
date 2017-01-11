package cuexpo.chulaexpo.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.FavouriteFragment;
import cuexpo.chulaexpo.fragment.ReservedFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReservedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reserved_acitivity_content_container, new ReservedFragment()).commit();

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
