package com.example.indoorlocalization;

import android.net.wifi.ScanResult;
import java.util.Comparator;

public  class RSSIComparator implements Comparator<AccessPoint> {

    @Override
    public int compare(AccessPoint result1, AccessPoint result2) {
        String s1 = result1.getRSSI() + "";
        String s2 = result2.getRSSI() + "";
        int v = s1.compareTo(s2);
        if (v == 0) {
            return result1.getBSSID().compareTo(result2.getBSSID());
        } else {
            return v;
        }
    }
}
