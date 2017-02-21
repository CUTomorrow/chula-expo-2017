package cuexpo.chulaexpo.utility;

import android.app.Application;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.indoorlocalization.Localization;
import com.example.indoorlocalization.OnTaskCompleteListener;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import cuexpo.chulaexpo.MainApplication;
import cuexpo.chulaexpo.activity.MainActivity;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class LocationTask extends TimerTask{
    private WifiManager wifiManager = MainApplication.wifiManager;
    private GoogleApiClient googleApiClient = MainApplication.googleApiClient;

    @Override
    public void run() {
        int state = wifiManager.getWifiState();
        JSONObject fp = new JSONObject();
        if (state == WifiManager.WIFI_STATE_ENABLED) {
            if (wifiManager.startScan()) {
                List<ScanResult> results = wifiManager.getScanResults();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < results.size(); i++) {
                    JSONObject accesspoint = new JSONObject();
                    accesspoint.put("BSSID", results.get(i).BSSID);
                    accesspoint.put("SSID", results.get(i).SSID);
                    accesspoint.put("RSSI", results.get(i).level + "");
                    jsonArray.add(accesspoint);
                }
                fp.put("user_id", "xxxxxxxx");
                Location location = MainApplication.getCurrentLocation();
                fp.put("latitude", location.getLatitude());
                fp.put("longitude", location.getLongitude());
                fp.put("ap", jsonArray);

            }
        }
        Log.d("jsonAP", ""+fp.get("ap"));
        Localization localization = new Localization(completeListener);
        localization.execute(fp);
    }

    private OnTaskCompleteListener completeListener = new OnTaskCompleteListener() {
        @Override
        public void onCompleteListerner(JSONObject result) {
            String location = result.get("faculty_id") + " " + result.get("building_id") + " " +
                    result.get("floor") + " " + result.get("room_number");
            MainApplication.setCurrentLocationDetail(location);
        }
    };
}

