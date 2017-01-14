package cuexpo.chulaexpo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize things
        Contextor.getInstance().init(getApplicationContext());
        /**
         * Facebook
         */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
