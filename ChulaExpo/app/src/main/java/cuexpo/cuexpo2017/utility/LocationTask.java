package cuexpo.cuexpo2017.utility;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.indoorlocalization.Localization;
import com.example.indoorlocalization.OnTaskCompleteListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.TimerTask;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.dao.MyLocation;
import cuexpo.cuexpo2017.dao.WhereDao;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class LocationTask extends TimerTask{
    private WifiManager wifiManager = MainApplication.wifiManager;
    private GoogleApiClient googleApiClient = MainApplication.googleApiClient;

    @Override
    public void run() {
        sentLocationRequest();
    }

    public void sentLocationRequest(){
        int state = wifiManager.getWifiState();
        JSONObject fp = new JSONObject();
        if (state == WifiManager.WIFI_STATE_ENABLED) {
            if (wifiManager.startScan()) {
                try {
                    List<ScanResult> results = wifiManager.getScanResults();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < results.size(); i++) {
                        JSONObject accesspoint = new JSONObject();
                        accesspoint.put("BSSID", results.get(i).BSSID);
                        accesspoint.put("SSID", results.get(i).SSID);
                        accesspoint.put("RSSI", results.get(i).level + "");
                        jsonArray.add(accesspoint);
                    }
                    fp.put("ap", jsonArray);
                }catch (SecurityException e){
                    Log.e("Location Task", e.toString());
                    MainApplication.setCurrentLocationDetail("Please allow location permission");
                    return;
                }
            }
        }

        fp.put("user_id", MainApplication.getApiToken());
        Log.d("user_id", MainApplication.getApiToken());

        try {
            Location location = MainApplication.getCurrentLocation();
            fp.put("latitude", location.getLatitude());
            fp.put("longitude", location.getLongitude());
            Localization localization = new Localization(completeListener);
            localization.execute(fp);
        } catch (NullPointerException e) {
            Log.e("Location Task", e.toString());
            MainApplication.setCurrentLocationDetail("Please enable your Wi-Fi and GPS");
        }
    }

    private OnTaskCompleteListener completeListener = new OnTaskCompleteListener() {
        @Override
        public void onCompleteListerner(JSONObject result) {
            String location;
            if (result.get("faculty_id") == null) {
                location = "Fetching data...";
                MainApplication.setCurrentLocationDetail(location);
                callWhereAmI();
            } else if (result.get("building_id").equals("-1")) {
                location = "Fetching data...";
                MainApplication.setCurrentLocationDetail(location);
                callWhereAmI();
            } else {
                location = "คณะ " + result.get("faculty_id") + " ตึก " + result.get("building_id");
                if (!result.get("floor").equals("-1"))
                    location += " ชั้น " + result.get("floor") + " ห้อง " + result.get("room_number");
                MainApplication.setCurrentLocationDetail(location);
            }
        }
    };

    private void callWhereAmI(){
        Location loc = MainApplication.getCurrentLocation();
        Call<WhereDao> callWhere = HttpManager.getInstance().getService().getLocationInfo(
                new MyLocation(loc.getLatitude(), loc.getLongitude()));
        callWhere.enqueue(callbackWhere);
    }

    Callback<WhereDao> callbackWhere = new Callback<WhereDao>() {
        @Override
        public void onResponse(Call<WhereDao> call, Response<WhereDao> response) {
            if (response.isSuccessful()) {
                WhereDao dao = response.body();
                MainApplication.setCurrentLocationDetail(dao.getResults().getText().getTh());
                Log.d("whereAmI success", dao.getResults().getText().getTh());
            } else {
                Log.e("whereAmI not success", "TToTT");
            }
        }

        @Override
        public void onFailure(Call<WhereDao> call, Throwable t) {
            Log.e("whereAmI fail", t.toString());
        }
    };
}

