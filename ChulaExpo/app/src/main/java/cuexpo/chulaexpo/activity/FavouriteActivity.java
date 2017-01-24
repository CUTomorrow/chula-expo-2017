package cuexpo.chulaexpo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.FavouriteFragment;
import cuexpo.chulaexpo.fragment.StageFragment;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.favourite_acitivity_content_container, new FavouriteFragment()).commit();

        }

    }


}
