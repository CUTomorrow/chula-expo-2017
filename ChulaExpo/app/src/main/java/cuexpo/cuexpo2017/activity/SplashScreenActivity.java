package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                /*test
                if(sharedPreferences.getString("email","").equals("darkjame_2539@hotmail.com")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("uid","");
                    editor.apply();
                }*/
                String token = sharedPreferences.getString("apiToken", "");
                String uid = sharedPreferences.getString("uid","");
                MainApplication.setApiToken(token);
                if(!token.equals("") && uid.equals("")){
                    LoginActivity.missUID = true;
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
                else if(token.equals("") || uid.equals("")){
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, RoleActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
