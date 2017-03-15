package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.dao.LoginResultDao;
import cuexpo.cuexpo2017.dao.LoginUser;
import cuexpo.cuexpo2017.dao.LoginUsernameDao;
import cuexpo.cuexpo2017.dao.LoginUsernameResultDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.dao.UserResult;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by kosate on 13/02/2017.
 */
public class LoginUserActivity extends AppCompatActivity {

    private Context context;
    private String apiToken;
    private Button submitButton;

    public LoginUserActivity() {
        super();

        context = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_user);

        findViewById(R.id.login_user_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitButton = (Button)findViewById(R.id.login_user_submit);
        submitButton.setOnClickListener(submitOnClick);

    }

    private View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                submitButton.setClickable(false);
                String user = ((EditText) findViewById(R.id.login_user_input)).getText().toString();
                String pass = ((EditText) findViewById(R.id.login_password_input)).getText().toString();

                Toast.makeText(Contextor.getInstance().getContext(), "กำลังเข้าสู่ระบบ...", Toast.LENGTH_SHORT).show();

                if (user.length() == 0) {
                    Toast.makeText(context, "กรุณาใส่ ชือผู้ใช้", Toast.LENGTH_SHORT).show();
                } else if (pass.length() == 0) {
                    Toast.makeText(context, "กรุณาใส่ รหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else {
                    Call<LoginUsernameDao> loginStatusList = HttpManager.getInstance().getService().loginWithUsername(
                        new LoginUser(user, pass)
                    );
                    loginStatusList.enqueue(callbackLoginStatus);
                }
            } catch(NullPointerException e) {
                Toast.makeText(context, "ไม่สามารถ Login ได้ (errNo: 001)", Toast.LENGTH_LONG).show();
            }
        }
    };

    Callback<LoginUsernameDao> callbackLoginStatus = new Callback<LoginUsernameDao>() {
        @Override
        public void onResponse(Call<LoginUsernameDao> call, Response<LoginUsernameDao> response) {
            if (response.isSuccessful()) {
                LoginUsernameDao dao = response.body();
                if(dao.getSuccess()) {
                    apiToken = dao.getResults().getToken();

                    SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("apiToken", apiToken);
                    MainApplication.setApiToken(apiToken);
                    editor.apply();

                    HttpManager.getInstance().setAPIKey(apiToken);

                    Call<UserDao> loginStatusList = HttpManager.getInstance().getService().loadUserInfo();
                    loginStatusList.enqueue(callbackUserInfo);

                }
            } else {
                if(response.code() == 401) {
                    Toast.makeText(Contextor.getInstance().getContext(), "ชื่อผู้ใช้ หรือ รหัสผ่าน ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Contextor.getInstance().getContext(), "การเชื่อมต่อ Server มีปัญหา (errNo: 002)", Toast.LENGTH_LONG).show();
                }
                submitButton.setClickable(true);

            }
        }

        @Override
        public void onFailure(Call<LoginUsernameDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ต เพื่อเข้าสู่ระบบ", Toast.LENGTH_LONG).show();
            Log.e("LoginUser", t.toString());
            submitButton.setClickable(true);
        }
    };

    Callback<UserDao> callbackUserInfo = new Callback<UserDao>() {

        @Override
        public void onResponse(Call<UserDao> call, Response<UserDao> response) {
            if(response.isSuccessful()) {

                UserResult result = response.body().getResults();

                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("id", result.getId());
                editor.putString("name", result.getName());
                editor.putString("email", result.getEmail());
                editor.putString("type", result.getType());
                String gen = result.getGender();
                if(gen.equals("male") || gen.equals("Male")) gen = "Male";
                else gen = "Female";
                editor.putString("gender", gen);
                editor.apply();

                findViewById(R.id.login_user_progress).setVisibility(View.VISIBLE);

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(500);
                        } catch(Exception e) {}
                        Intent intent = new Intent(LoginUserActivity.this, MainActivity.class);
                        LoginUserActivity.this.startActivity(intent);
                        LoginUserActivity.this.finish();
                        return null;
                    }

                }.execute();
            } else {
                Log.e("LOGIN USER", response.code() + " " + response.errorBody());
                Toast.makeText(Contextor.getInstance().getContext(), "การเชื่อมต่อ Server มีปัญหา (errNo: 004)", Toast.LENGTH_LONG).show();
                submitButton.setClickable(true);
            }
        }

        @Override
        public void onFailure(Call<UserDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), "กรุณาเชื่อมต่ออินเตอร์เน็ต เพื่อเข้าสู่ระบบ", Toast.LENGTH_LONG).show();
            Log.e("LoginUser", t.toString());
            submitButton.setClickable(true);
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
