package com.example.indoorlocalization;

import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Localization extends AsyncTask<JSONObject, Void, JSONObject> {

    private boolean isPrint = true;
    private String server = "161.200.92.6/localization/requests/";
    private OnTaskCompleteListener taskDone;

    public Localization(OnTaskCompleteListener taskdone) {
        this.taskDone = taskdone;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... fingerprint) {
        JSONArray jsonAP = (JSONArray) fingerprint[0].get("ap");
        ArrayList<AccessPoint> listAP = getOrderedAP(jsonAP);
        JSONArray jsonOrderAP = arrayListToJSON(listAP);
        JSONObject jsonToServer = jsonToServer(fingerprint[0], jsonOrderAP);
        return sendToServer(jsonToServer);
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        taskDone.onCompleteListerner(result);
    }

    private JSONObject sendToServer(JSONObject jsonToServer) {
        try {
            String returnMessage;
            String url = "http://" + server + "requests.php";
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write("data=" + jsonToServer.toString());
            wr.flush();

            //Return message.
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                returnMessage = sb.toString();
            } else {
                returnMessage = con.getResponseMessage();
            }
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(returnMessage);
        } catch (Exception e) {
            printLog(e.toString());
            return new JSONObject();
        }
    }

    private JSONObject jsonToServer(JSONObject fingerprint, JSONArray jsonOrderAP) {
        JSONObject jsonToServer = new JSONObject();
        jsonToServer.put("user_id", fingerprint.get("user_id"));
        jsonToServer.put("latitude", fingerprint.get("latitude"));
        jsonToServer.put("longitude", fingerprint.get("longitude"));
        jsonToServer.put("ap", jsonOrderAP);
        return jsonToServer;
    }

    private ArrayList<AccessPoint> getOrderedAP(JSONArray jsonAP) {
        ArrayList<AccessPoint> listAP = new ArrayList<AccessPoint>();
        for (int i = 0; i < jsonAP.size(); i++) {
            String bssid = (String) ((JSONObject) jsonAP.get(i)).get("BSSID");
            String ssid = (String) ((JSONObject) jsonAP.get(i)).get("SSID");
            int rssi = Integer.parseInt((String) ((JSONObject) jsonAP.get(i)).get("RSSI"));
            listAP.add(new AccessPoint(bssid, ssid, rssi));
        }
        Collections.sort(listAP, new RSSIComparator());
        return listAP;
    }

    private JSONArray arrayListToJSON(ArrayList<AccessPoint> listAP) {
        JSONArray jsonOrderAP = new JSONArray();
        for (int i = 0; i < listAP.size(); i++) {
            JSONObject tempAP = new JSONObject();
            tempAP.put("BSSID", listAP.get(i).getBSSID());
            tempAP.put("SSID", listAP.get(i).getSSID());
            tempAP.put("RSSI", listAP.get(i).getRSSI() + "");
            jsonOrderAP.add(tempAP);
        }
        return jsonOrderAP;
    }

    private void printLog(String msg) {
        if (isPrint) Log.d("Teerapat", msg);
    }

}
