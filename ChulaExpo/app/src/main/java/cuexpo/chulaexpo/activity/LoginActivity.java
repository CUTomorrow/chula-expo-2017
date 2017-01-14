package cuexpo.chulaexpo.activity;

<<<<<<< HEAD
<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
=======
import android.app.Activity;
>>>>>>> finish facebook login task
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
=======
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
>>>>>>> edit intent flow
=======
>>>>>>> finish facebook login task
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> finish facebook login task
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
=======

>>>>>>> finish facebook login task
import cuexpo.chulaexpo.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private List<String> permissionNeeds = Arrays.asList("user_photos", "email",
            "user_birthday", "public_profile");
    private CallbackManager callbackManager;
<<<<<<< HEAD
=======
import cuexpo.chulaexpo.R;

public class LoginActivity extends AppCompatActivity {
>>>>>>> edit intent flow
=======
>>>>>>> finish facebook login task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
<<<<<<< HEAD
        activity = this;

        ImageView facebookLogin = (ImageView) findViewById(R.id.login_facebook);
        TextView guestLogin = (TextView) findViewById(R.id.login_guest);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
=======
=======
        activity = this;
>>>>>>> finish facebook login task

        ImageView facebookLogin = (ImageView) findViewById(R.id.login_facebook);
        TextView guestLogin = (TextView) findViewById(R.id.login_guest);

<<<<<<< HEAD
>>>>>>> edit intent flow
=======
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
>>>>>>> finish facebook login task
        facebookLogin.setOnClickListener(facebookLoginOnClick);
        guestLogin.setOnClickListener(guestLoginOnClick);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    private View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
        }
    };

    private FacebookCallback facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), graphJSONObjectCallback);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.e("Login - facebook login", "cancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.e("Login - facebook login", error.toString());
        }
    };

    private GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // "http://graph.facebook.com/"+id+"/picture?type=large";
                editor.putString("id", object.getString("id"));
                editor.putString("name", object.getString("name"));
                editor.putString("email", object.getString("email"));
                editor.putString("birthday", object.getString("birthday"));
                editor.putString("gender", object.getString("gender"));
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            } catch (JSONException error) {
                Log.e("Login - parse json", error.toString());
            }
        }
    };

    private View.OnClickListener guestLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RoleActivity.class);
=======
    View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
=======
    private View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
>>>>>>> finish facebook login task
        @Override
        public void onClick(View v) {
            LoginManager.getInstance().logInWithReadPermissions(activity, permissionNeeds);
        }
    };

    private FacebookCallback facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), graphJSONObjectCallback);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.e("Login - facebook login", "cancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.e("Login - facebook login", error.toString());
        }
    };

    private GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                SharedPreferences sharedPref = getSharedPreferences("FacebookInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // "http://graph.facebook.com/"+id+"/picture?type=large";
                editor.putString("id", object.getString("id"));
                editor.putString("name", object.getString("name"));
                editor.putString("email", object.getString("email"));
                editor.putString("birthday", object.getString("birthday"));
                editor.putString("gender", object.getString("gender"));
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            } catch (JSONException error) {
                Log.e("Login - parse json", error.toString());
            }
        }
    };

    private View.OnClickListener guestLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
>>>>>>> edit intent flow
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }
    };
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> finish facebook login task

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
<<<<<<< HEAD

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
=======
>>>>>>> edit intent flow
=======
>>>>>>> finish facebook login task
}
