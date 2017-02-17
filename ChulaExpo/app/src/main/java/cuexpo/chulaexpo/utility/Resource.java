package cuexpo.chulaexpo.utility;

import java.lang.reflect.Field;

import cuexpo.chulaexpo.R;

/**
 * Created by APTX-4869 (LOCAL) on 2/17/2017.
 */

public class Resource {

    private static int getResourceId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    public static int getColor(String color) {
        return getResourceId(color, R.color.class);
    }
}
