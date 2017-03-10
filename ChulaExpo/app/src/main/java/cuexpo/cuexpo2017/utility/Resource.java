package cuexpo.cuexpo2017.utility;

import android.graphics.Color;

import cuexpo.cuexpo2017.R;

/**
 * Created by APTX-4869 (LOCAL) on 2/17/2017.
 */

public class Resource {
    public static String[] lightZone = {"SCI", "ECON", "LAW", "VET"};

    public static int getColor(String color) {
        try {
            return R.color.class.getField(color).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }
    }

    public static int getTagColor(String zoneShortName){
        for(String zone: lightZone) if(zoneShortName.equals(zone)) return Color.BLACK;
        return Color.WHITE;

    }

    public static int getDrawable(String drawable) {
        try {
            return R.drawable.class.getField(drawable).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }
    }
}
