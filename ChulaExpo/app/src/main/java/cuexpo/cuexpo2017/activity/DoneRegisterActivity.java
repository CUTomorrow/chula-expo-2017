package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.dao.Token;
import cuexpo.cuexpo2017.dao.UserProfile;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DoneRegisterActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String email, name, gender, profile, type, tags, academicLevel, academicYear, academicSchool, workerJob, facebook;
    int age;
    Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_register);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainAct();
            }
        });
    }

    private void gotoMainAct() {
        sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
        token = new Token();
        token.setAccessToken(sharedPref.getString("fbToken",""));
        token.setKind("facebook");
        Token[] tokens = {token};
        facebook = sharedPref.getString("id","");
        email = sharedPref.getString("email","");
        name = sharedPref.getString("name","");
        gender = sharedPref.getString("gender","");
        profile = sharedPref.getString("profile","");
        type = sharedPref.getString("type","");
        tags = sharedPref.getString("tags","");
        Log.d("tagsDone", tags);
        academicLevel = sharedPref.getString("academicLevel","");
        academicYear = sharedPref.getString("academicYear","");
        workerJob = sharedPref.getString("workerJob","");
        academicSchool = sharedPref.getString("academicSchool","");
        tags = sharedPref.getString("tags", "");
        try {
            age = sharedPref.getInt("age", 0);
        } catch (Exception e){
            age = 0;
        }

        final UserProfile userProfile = new UserProfile(email,tokens,name,gender,age,profile,
                type,tags,academicLevel,academicYear,academicSchool,workerJob, facebook);
        Call<LoginDao> callRegister = HttpManager.getInstance().getService().registerUser(userProfile);
        callRegister.enqueue(new Callback<LoginDao>() {
            @Override
            public void onResponse(Call<LoginDao> call, Response<LoginDao> response) {
                if(response.isSuccessful()){
                    LoginDao dao = response.body();
                    if(dao.getSuccess()){
                        Log.e("signup","signup success" + userProfile.getName() + ": " + userProfile.getFacebook() +">" + userProfile.getTokens()[0]);
                        sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("apiToken",dao.getResults().getToken());
                        MainApplication.setApiToken(dao.getResults().getToken());
                        //Log.e("signup","apitoken: "+ dao.getResults().getToken());
                        editor.apply();
                    }
                } else {
                    Log.e("signup","Signup Not Success");
                }
            }

            @Override
            public void onFailure(Call<LoginDao> call, Throwable t) {
                Log.d("signup","Signup Fail" + t.toString());
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
