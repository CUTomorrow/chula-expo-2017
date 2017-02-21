package cuexpo.chulaexpo.utility;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import java.lang.reflect.Field;

import cuexpo.chulaexpo.R;

/**
 * Created by APTX-4869 (LOCAL) on 2/17/2017.
 */

public class Resource {

    public static int getColor(String color) {
        try {
            return R.color.class.getField(color).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }
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
