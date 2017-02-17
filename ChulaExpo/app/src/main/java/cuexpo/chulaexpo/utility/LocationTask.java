package cuexpo.chulaexpo.utility;

import android.app.Application;
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
                HashMap<String, String> location = MainApplication.getCurrentLocation();
                fp.put("latitude", location.get("lat"));
                fp.put("longitude", location.get("long"));
                fp.put("ap", jsonArray);
            }
        }
        Localization localization = new Localization(completeListener);
        localization.execute(fp);
    }

    private OnTaskCompleteListener completeListener = new OnTaskCompleteListener() {
        @Override
        public void onCompleteListerner(JSONObject result) {
            Log.d("location", result.get("faculty_id") + "\n" + result.get("building_id") + "\n" + result.get("floor") + "\n" + result.get("room_number"));
        }
    };
}

