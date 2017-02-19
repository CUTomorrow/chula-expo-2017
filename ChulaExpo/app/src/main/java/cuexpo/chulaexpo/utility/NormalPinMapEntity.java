package cuexpo.chulaexpo.utility;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import cuexpo.chulaexpo.R;

/**
 * Created by Kasidit on 05-Nov-15.
 */
public class NormalPinMapEntity implements IMapEntity {
    private Marker marker;
    private GoogleMap map;
    private MarkerOptions markerOption;
    private boolean visible = true;
    private int pinType;

    public final static int INFO_POINT_PIN = 0;
    public final static int LANDMARK_PIN = 1;
    public final static int POPBUS_STATION_PIN = 2;

    public String toString() {
        switch (pinType) {
            case INFO_POINT_PIN:
                return "Information Pin : " + nameEn;
            case LANDMARK_PIN:
                return "Landmark Pin : " + nameEn;
            case POPBUS_STATION_PIN:
                return "Popbus Station Pin : " + nameEn;
            default:
                return "Normal Pin";
        }
    }

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

    private String nameTh;
    private String nameEn;

    public String getNameEn() {
        return nameEn;
    }

    public String getNameTh() {
        return nameTh;
    }

    private int markerIconDrawableResource;

    public int getMarkerIconDrawableResource() {
        return markerIconDrawableResource;
    }

    public NormalPinMapEntity(JSONObject dataJSON, int pinType) {
        this.pinType = pinType;
        try {
            nameTh = dataJSON.getString("nameTh");
            nameEn = dataJSON.getString("nameEn");

            switch (pinType) {
                case INFO_POINT_PIN:
                    markerIconDrawableResource = R.drawable.pin_information;
                    break;
                case LANDMARK_PIN:
                    markerIconDrawableResource = R.drawable.pin_landmark;
                    break;
                case POPBUS_STATION_PIN:
                    markerIconDrawableResource = R.drawable.pin_cutour;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid pinType value.");
            }

            markerOption = new MarkerOptions()
                    .position(new LatLng(dataJSON.getDouble("lat"), dataJSON.getDouble("lng")))
                    .title(nameTh)
                    .visible(isVisible());

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
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
