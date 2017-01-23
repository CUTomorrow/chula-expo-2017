package cuexpo.chulaexpo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize things
        Contextor.getInstance().init(getApplicationContext());
        // facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        // font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ThaiSansNeue-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
