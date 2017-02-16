package cuexpo.chulaexpo;

import android.app.Application;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.HashMap;
import java.util.Timer;

import cuexpo.chulaexpo.utility.LocationTask;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MainApplication extends Application implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static WifiManager wifiManager;
    public static GoogleApiClient googleApiClient;
    public static Location lastLocation;

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

        // Wifi
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        // GeoLocation
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();

        // start background task
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new LocationTask(), 30000, 30000); // 30s
    }

    @Override
    public void onTerminate() {
        googleApiClient.disconnect();
        super.onTerminate();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } catch (SecurityException e) {
//            Snackbar.make(MainActivity.this.rootView, "" + e, Snackbar.LENGTH_SHORT);
        }

        if (lastLocation != null) {
            Log.d("geoLocation", String.valueOf(lastLocation.getLatitude()) + " " + String.valueOf(lastLocation.getLongitude()));

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static HashMap<String, String> getCurrentLocation(){
        HashMap<String, String> location = new HashMap<>();
        location.put("lat", String.valueOf(lastLocation.getLatitude()));
        location.put("long", String.valueOf(lastLocation.getLongitude()));
        return location;
    }
}
