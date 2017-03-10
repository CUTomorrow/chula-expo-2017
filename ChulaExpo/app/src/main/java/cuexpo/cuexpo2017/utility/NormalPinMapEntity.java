package cuexpo.cuexpo2017.utility;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.Location;

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
    private int color;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    private int markerIconDrawableResource;

    public int getMarkerIconDrawableResource() {
        return markerIconDrawableResource;
    }

    public NormalPinMapEntity(String name, Location location, String type) {
        this.type = type;
        this.name = name;
        switch (type) {
            case "Event":
                markerIconDrawableResource = R.drawable.pin_event;
                color = Color.parseColor("#e40434");
                break;
            case "Canteen":
                markerIconDrawableResource = R.drawable.food;
                color = Color.parseColor("#ff9915");
                break;
            case "Souvenir":
                markerIconDrawableResource = R.drawable.shop;
                color = Color.parseColor("#ff9915");
                break;
            case "Registration":
                markerIconDrawableResource = R.drawable.regis;
                color = Color.parseColor("#15b345");
                break;
            case "Information":
                markerIconDrawableResource = R.drawable.info;
                color = Color.parseColor("#01cab9");
                break;
            case "Toilet":
                markerIconDrawableResource = R.drawable.toilet;
                color = Color.parseColor("#3d93bf");
                break;
            case "Rally":
                markerIconDrawableResource = R.drawable.rally;
                color = Color.parseColor("#5c4083");
                break;
            case "Carpark":
                markerIconDrawableResource = R.drawable.park;
                color = Color.parseColor("#3e6b94");
                break;
            case "Emergency":
                markerIconDrawableResource = R.drawable.emer;
                color = Color.parseColor("#cc0d1f");
                break;
            case "Prayer":
                markerIconDrawableResource = R.drawable.pray;
                color = Color.parseColor("#786043");
                break;
            case "BusStop":
                markerIconDrawableResource = R.drawable.bus;
                color = Color.parseColor("#ff2977");
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

    public void clearMarker() {
        if(marker != null) marker.remove();
    }
}
