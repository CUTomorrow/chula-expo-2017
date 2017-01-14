package cuexpo.chulaexpo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cuexpo.chulaexpo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView facebookLogin = (ImageView) findViewById(R.id.login_facebook);
        TextView guestLogin  = (TextView) findViewById(R.id.login_guest);

        facebookLogin.setOnClickListener(facebookLoginOnClick);
        guestLogin.setOnClickListener(guestLoginOnClick);
    }

    View.OnClickListener facebookLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
            // TODO pass facebook information through intent / store in database / sharepref
//            intent.putExtra("facebook", facebook);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }
    };

    View.OnClickListener guestLoginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }
    };
}
