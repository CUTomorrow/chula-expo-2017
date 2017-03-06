package cuexpo.chulaexpo.utility;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.dao.Location;

/**
 * Created by Kasidit on 05-Nov-15.
 */
public class NormalPinMapEntity implements IMapEntity {
    private Marker marker;
    private GoogleMap map;
    private MarkerOptions markerOption;
    private boolean visible = true;

    @Override
    public boolean isVisible() {
        return visible;
    }

    public Marker getMarker() {
        return marker;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (marker != null) {

            marker.setVisible(visible);
        } else {
            markerOption.visible(visible);
        }
    }

    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    private int markerIconDrawableResource;

    public int getMarkerIconDrawableResource() {
        return markerIconDrawableResource;
    }

    public NormalPinMapEntity(String name, Location location, String type) {
        this.type = type;
        this.name = name;
        Log.d("pin type", type);
        switch (type) {
            case "Canteen":
                Log.d("type is canteen", "true");
                markerIconDrawableResource = R.drawable.food;
                break;
            case "Souvenir":
                markerIconDrawableResource = R.drawable.food;
                break;
            case "Registration":
                markerIconDrawableResource = R.drawable.regis;
                break;
            case "Information":
                markerIconDrawableResource = R.drawable.info;
                break;
            case "Toilet":
                markerIconDrawableResource = R.drawable.toilet;
                break;
            case "Rally":
                markerIconDrawableResource = R.drawable.rally;
                break;
            case "Carpark":
                markerIconDrawableResource = R.drawable.park;
                break;
            case "Emergency":
                markerIconDrawableResource = R.drawable.aid;
                break;
            // TODO change to correct drawable
            case "Prayer":
                markerIconDrawableResource = R.drawable.aid;
                break;

            default:
                Log.e("invalid pin type", type);
                throw new IllegalArgumentException("Invalid pinType value.");
        }

        markerOption = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .visible(isVisible());
    }

    @Override
    public void setMap(GoogleMap map) {
        if (this.map != null) {
            throw new RuntimeException("Cannot set map to the marker more than once.");
        }
        this.map = map;
        markerOption.icon(BitmapDescriptorFactory.fromResource(markerIconDrawableResource));
        marker = map.addMarker(markerOption);
    }
}
