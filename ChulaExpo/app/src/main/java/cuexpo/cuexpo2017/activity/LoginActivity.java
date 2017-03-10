package cuexpo.cuexpo2017.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private List<String> permissionNeeds = Arrays.asList("user_photos", "email",
            "user_birthday", "public_profile");
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private String token, kind;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        RelativeLayout facebookLogin = (RelativeLayout) findViewById(R.id.login_fb);
        TextView guestLogin = (TextView) findViewById(R.id.login_guest);


        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);

        facebookLogin.setOnClickListener(facebookLoginOnClick);
        guestLogin.setOnClickListener(guestLoginOnClick);
    }

    private View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
        }
    };


    private FacebookCallback facebookCallback =  new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResults) {
            Log.e("LoginFB","facebook login yeah yeah yeah yeah yeah yeah");
            GraphRequest request = GraphRequest.newMeRequest(loginResults.getAccessToken(), graphJSONObjectCallback);
            token = loginResults.getAccessToken().getToken();
            Log.e("LoginFB","Token Token Token Token Token Token Token Token Token Token");
            SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("fbToken",token);
            editor.apply();
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
        @Override
        public void onCancel() {
            Log.e("LoginFB","facebook login canceled");
        }
        @Override
        public void onError(FacebookException e) {
            Log.e("LoginFB", "facebook login failed error" + e.toString());
            Toast.makeText(Contextor.getInstance().getContext(),"Weak Connection, please check the Internet and reconnect",Toast.LENGTH_SHORT);
        }
    };

    private GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                Log.e("LoginFB","facebook login complete");
                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // "http://graph.facebook.com/"+id+"/picture?type=large";
                editor.putString("id", object.getString("id"));
                editor.putString("name", object.getString("name"));
                editor.putString("email", object.getString("email"));
                //editor.putString("birthday", object.getString("birthday"));
                editor.putString("gender", object.getString("gender"));
                editor.apply();

                //api
                Call<LoginDao> callLogin = HttpManager.getInstance().getService().accessFacebook(token);
                /*
                try {
                    LoginDao loginDao = callLogin.execute().body();
                    Log.e("LoginFB","Sync: "+ loginDao.getSuccess().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

                callLogin.enqueue(new Callback<LoginDao>() {
                    @Override
                    public void onResponse(Call<LoginDao> call, Response<LoginDao> response) {
                        Log.e("LoginFB","facebook login onResponse");
                        if(response.isSuccessful()) {
                            LoginDao dao = response.body();
                            if(dao.getSuccess()){
                                Log.e("LoginFB","Success=true");

                                //keep token
                                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("apiToken",dao.getResults().getToken());
                                Log.e("LoginFB","apiToken = " + dao.getResults().getToken());
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                if(dao.getErrors().getCode() == 2){
                                    Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                } else {
                                    Toast.makeText(Contextor.getInstance().getContext(),dao.getErrors().getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.e("LoginFB","facebook login not success");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDao> call, Throwable t) {
                        Log.e("LoginFB","facebook login Failure" + t.toString());
                        Toast.makeText(Contextor.getInstance().getContext(),"No Connection",Toast.LENGTH_SHORT);
                    }
                });


//                Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
//                LoginActivity.this.startActivity(intent);
//                LoginActivity.this.finish();
            } catch (JSONException error) {
                Log.e("Login - parse json", error.toString());
            }
        }
    };

    private View.OnClickListener guestLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
