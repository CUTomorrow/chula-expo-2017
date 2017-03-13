package cuexpo.cuexpo2017.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import cuexpo.cuexpo2017.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by kosate on 13/02/2017.
 */
public class LoginUserActivity extends AppCompatActivity {

    private Context context;

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

        findViewById(R.id.login_user_submit).setOnClickListener(submitOnClick);
    }

    private View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user = ((EditText) findViewById(R.id.login_user_input)).getText().toString();
            String pass = ((EditText) findViewById(R.id.login_password_input)).getText().toString();

            System.out.println("LOGIN USER : " + user + " " + pass);

            if(user.length() == 0) {
                Toast.makeText(context, "กรุณาใส่ ชือผู้ใช้", Toast.LENGTH_SHORT).show();
            } else if(pass.length() == 0) {
                Toast.makeText(context, "กรุณาใส่ รหัสผ่าน", Toast.LENGTH_SHORT).show();
            } else {
            }
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
