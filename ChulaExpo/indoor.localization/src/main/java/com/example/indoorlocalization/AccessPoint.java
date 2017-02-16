package com.example.indoorlocalization;

public class AccessPoint {
    private String BSSID;
    private String SSID;
    private int RSSI;

    public AccessPoint(String BSSID, String SSID, int RSSI) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.RSSI = RSSI;
    }

    public String getBSSID() {
        return this.BSSID;
    }

    public String getSSID() {
        return this.SSID;
    }

    public int getRSSI() {
        return this.RSSI;
    }
}
