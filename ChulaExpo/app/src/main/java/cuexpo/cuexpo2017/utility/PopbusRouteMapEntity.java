package cuexpo.cuexpo2017.utility;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kasidit on 05-Nov-15.
 */
public class PopbusRouteMapEntity implements IMapEntity {
    private PolylineOptions polylineOption;
    private GoogleMap map;
    private Polyline polyline;
    private boolean visible = true;
    private String routeName;
    private float lineWidth = 10;

    public String toString() {
        return "CU Tour Route : " + routeName;
    }

    public PopbusRouteMapEntity(JSONObject routeData) {
        try {
            routeName = routeData.getString("name");

            polylineOption = new PolylineOptions()
                    .color(Color.parseColor('#' + routeData.getString("color")))
                    .visible(isVisible());

            updateWidth();

            // Set pathway
            JSONArray pathwayData = routeData.getJSONArray("pathway");
            for (int i = 0; i < pathwayData.length(); i++) {
                polylineOption.add(new LatLng(
                        pathwayData.getJSONObject(i).getDouble("latitude"),
                        pathwayData.getJSONObject(i).getDouble("longitude")
                ));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (polyline != null) {
            polyline.setVisible(visible);
        } else {
            polylineOption.visible(visible);
        }
    }

    public void setMap(GoogleMap map) {
        if (this.map != null) {
            throw new RuntimeException("Cannot set map to the polyline more than once.");
        }
        this.map = map;
        updateWidth();
        polyline = map.addPolyline(polylineOption);
    }

    public void updateWidth() {
        if (this.map != null) {
            lineWidth = Math.min(20, Math.max(5, (float) (
                    Math.pow(this.map.getCameraPosition().zoom, 2) / 30.0
            )));

            if (polyline == null) {
                polylineOption.width(lineWidth);
            } else {
                polyline.setWidth(lineWidth);
            }

        } else {
            polylineOption.width(lineWidth);
        }
    }
}
