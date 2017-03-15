package cuexpo.cuexpo2017.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cuexpo.cuexpo2017.MainApplication;
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
    private AccessTokenTracker accessTokenTracker;
    private FrameLayout container;
    private ProgressBar progress;
    private RelativeLayout facebookLogin;
    private int countClick = 0;
    public static boolean missUID = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        facebookLogin = (RelativeLayout) findViewById(R.id.login_fb);
        RelativeLayout userLogin = (RelativeLayout) findViewById(R.id.login_user);
        TextView guestLogin = (TextView) findViewById(R.id.login_guest);
        container = (FrameLayout) findViewById(R.id.containerLogin);
        progress = (ProgressBar) findViewById(R.id.progress);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);

        facebookLogin.setOnClickListener(facebookLoginOnClick);
        guestLogin.setOnClickListener(guestLoginOnClick);
        userLogin.setOnClickListener(userLoginClick);
        if(missUID) notifyMissUIDError();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            Log.e("LoginFB","updateToken");
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            LoginActivity.this.startActivity(intent);
//            LoginActivity.this.finish();
        } else {
            Log.e("LoginFB", "No access token");
        }
    }

    private View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //container.setClickable(true);
            countClick++;
            Log.e("LoginFB","Click count = " + countClick);
            facebookLogin.setClickable(false);
            if(AccessToken.getCurrentAccessToken() != null){
                Log.e("user id key", AccessToken.USER_ID_KEY);
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/"+AccessToken.USER_ID_KEY+"/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                        Toast.makeText(activity, "Please try logging in again", Toast.LENGTH_SHORT).show();
                        facebookLogin.setClickable(true);
//                        LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
                    }
                }).executeAsync();
            }
            else LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
        }
    };

    private View.OnClickListener userLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, LoginUserActivity.class);
            LoginActivity.this.startActivity(intent);
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
            //container.setClickable(false);
            facebookLogin.setClickable(true);
            Log.e("LoginFB","facebook login canceled");
        }
        @Override
        public void onError(FacebookException e) {
            //container.setClickable(false);
            facebookLogin.setClickable(true);
            if(AccessToken.getCurrentAccessToken() !=  null) Log.e("access token", AccessToken.getCurrentAccessToken().getToken());
            else Log.e("LoginFB", "facebook login error " + e.toString());
            Toast.makeText(Contextor.getInstance().getContext(),"อินเตอร์เน็ตไม่เสถียร กรุณาเช็คการเชื่อมต่ออินเตอร์เน็ต",Toast.LENGTH_SHORT).show();
        }
    };

    private GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {

                Toast.makeText(Contextor.getInstance().getContext(), "กำลังเข้าสู่ระบบ...", Toast.LENGTH_SHORT).show();

                Log.e("LoginFB","facebook login complete");
                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // "http://graph.facebook.com/"+id+"/picture?type=large";
                editor.putString("id", object.getString("id"));
                //Log.e("LoginFB","id: "+object.getString("id"));
                editor.putString("name", object.getString("name"));
                editor.putString("email", object.getString("email"));
                //Log.e("LoginFB","birthday:"+object.getString("birthday"));
                //editor.putString("birthday", object.getString("birthday"));
                String gen = object.getString("gender");
                if(gen.equals("male")) gen = "Male";
                else gen = "Female";
                editor.putString("gender", gen);
                //Log.e("LoginFB","gender:"+object.getString("gender"));
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
                                MainApplication.setApiToken(dao.getResults().getToken());
//                                Log.e("LoginFB","apiToken = " + dao.getResults().getToken());
                                editor.apply();

                                progress.setVisibility(View.VISIBLE);

                                new AsyncTask<Void, Void, Void>() {

                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        try {
                                            Thread.sleep(250);
                                        } catch(Exception e) {}
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        LoginActivity.this.startActivity(intent);
                                        LoginActivity.this.finish();
                                        return null;
                                    }

                                }.execute();

                            } else {
                                if(dao.getErrors().getCode() == 2){
                                    Log.e("LoginFB","Account doesn't exist");
                                    Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                } else {
                                    Toast.makeText(Contextor.getInstance().getContext(),dao.getErrors().getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            try {
                                Log.e("LoginFB","facebook login not success " + response.errorBody().string());
                                facebookLogin.setClickable(true);
                                //Toast.makeText(Contextor.getInstance().getContext(),errorDao.getErrors().getMessage(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginDao> call, Throwable t) {
                        Log.e("LoginFB","facebook login Failure" + t.toString());
                        facebookLogin.setClickable(true);
                        Toast.makeText(Contextor.getInstance().getContext(),"No Connection",Toast.LENGTH_SHORT);
                    }
                });


//                Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
//                LoginActivity.this.startActivity(intent);
//                LoginActivity.this.finish();
            } catch (JSONException error) {
                Log.e("LoginFB","Parse JSON" + error.toString());
                facebookLogin.setClickable(true);
            }
        }
    };

    private View.OnClickListener guestLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    container.setClickable(true);
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(250);
                    } catch(Exception e) {}
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                    return null;
                }

            }.execute();
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

    private void notifyMissUIDError(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ขออภัย");
        alert.setMessage("เนื่องจากเกิดความผิดพลาดในการบันทึกข้อมูลในแอพพลิเคชันรุ่นเก่า กรุณาลงทะเบียนอีกครั้ง ขออภัยในความไม่สะดวก");
        missUID = false;
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert2 = alert.create();
        alert2.show();
    }
}
