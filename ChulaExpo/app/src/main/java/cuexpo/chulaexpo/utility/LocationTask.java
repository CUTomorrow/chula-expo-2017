package cuexpo.chulaexpo.utility;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class LocationTask extends TimerTask{
    private WifiManager wifiManager;
    public LocationTask(WifiManager wifiManager){
        this.wifiManager = wifiManager;
    }

    @Override
    public void run() {
        List<ScanResult> wifiList = wifiManager.getScanResults();
        JSONArray wifiInfoList = new JSONArray();
        for (ScanResult wifi: wifiList) {
            JSONObject json = new JSONObject();
            try {
                json.put("ssid", wifi.SSID);
                json.put("mac address", wifi.BSSID);
                json.put("rssi", wifi.level);
                wifiInfoList.put(json);
            }catch (JSONException e) {
                Log.e("Wifi: cannot build JSON", e.toString());
            }
        }
//        Log.d("wifiInfoList", wifiInfoList.toString());
        //TODO send wifiInfoList
    }
}

