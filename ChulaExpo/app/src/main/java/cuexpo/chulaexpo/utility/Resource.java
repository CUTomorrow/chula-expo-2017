package cuexpo.chulaexpo.utility;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import java.lang.reflect.Field;

import cuexpo.chulaexpo.R;

/**
 * Created by APTX-4869 (LOCAL) on 2/17/2017.
 */

public class Resource {
    private static Field idField;

    private static int getResourceId(String resourceName, Class<?> c) {
        try {
            idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }


    public static int  getColor( String color ) {
        int colorId = 0;

        try {
            Class res = R.color.class;
            Field field = res.getField( color );
            colorId = field.getInt(null);
        } catch ( Exception e ) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }

        return colorId;
    }
}
