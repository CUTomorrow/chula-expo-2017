package cuexpo.chulaexpo;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by James on 25/12/2559.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize things
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
