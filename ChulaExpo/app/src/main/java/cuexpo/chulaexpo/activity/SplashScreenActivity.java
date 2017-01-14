package cuexpo.chulaexpo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.chulaexpo.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("StartAppPref", MODE_PRIVATE);
                boolean hasLogedIn = sharedPreferences.getBoolean("hasLogedIn", false);
                if(!hasLogedIn){
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
