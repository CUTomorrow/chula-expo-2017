package cuexpo.chulaexpo.utility;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import th.in.thinc.cuopenhouse2015.R;

/**
 * Created by Kasidit on 05-Nov-15.
 */
public class FacultyMapEntity implements IMapEntity {
    private Marker marker;
    private Polygon area;
    private PolygonOptions areaOption;
    private GoogleMap map;
    private MarkerOptions markerOption;
    private boolean visible = true;

    public String toString() {
        return "Faculty " + facultyId;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (marker != null) {
            marker.setVisible(visible);
            area.setVisible(visible);
        } else {
            markerOption.visible(visible);
            areaOption.visible(visible);
        }
    }

    private String nameTh;
    private String nameEn;

    public String getNameTh() {
        return nameTh;
    }

    public String getNameEn() {
        return nameEn;
    }

    private int color;
    private LatLng centerPoint;

    public int getFacultyId() {
        return facultyId;
    }

    private int facultyId;

    public int getMarkerIconDrawableResource() {
        return getMarkerIconDrawableResource(facultyId);
    }

    private int getMarkerIconDrawableResource(int facultyId) {
        switch (facultyId) {
            // Thanks Sublime Text
            case 21:
                return R.drawable.pin_21;
            case 22:
                return R.drawable.pin_22;
            case 23:
                return R.drawable.pin_23;
            case 24:
                return R.drawable.pin_24;
            case 25:
                return R.drawable.pin_25;
            case 26:
                return R.drawable.pin_26;
            case 27:
                return R.drawable.pin_27;
            case 28:
                return R.drawable.pin_28;
            case 29:
                return R.drawable.pin_29;
            case 30:
                return R.drawable.pin_30;
            case 31:
                return R.drawable.pin_31;
            case 32:
                return R.drawable.pin_32;
            case 33:
                return R.drawable.pin_33;
            case 34:
                return R.drawable.pin_34;
            case 35:
                return R.drawable.pin_35;
            case 37:
                return R.drawable.pin_37;
            case 38:
                return R.drawable.pin_38;
            case 39:
                return R.drawable.pin_39;
            case 40:
                return R.drawable.pin_40;

            default:
                throw new IllegalArgumentException("Invalid faculty id : " + facultyId);
        }
    }

    public FacultyMapEntity(JSONObject facultyJSON) {
        try {
            JSONObject centerPosition = facultyJSON.getJSONObject("centerPoint");
            centerPoint = new LatLng(
                    centerPosition.getDouble("lat"),
                    centerPosition.getDouble("lng")
            );

            color = Color.parseColor(facultyJSON.getString("color"));

            nameTh = facultyJSON.getString("nameTh");
            nameEn = facultyJSON.getString("nameEn");
            facultyId = facultyJSON.getInt("id");

            markerOption = new MarkerOptions()
                    .position(centerPoint)
                    .title(nameTh)
                    .visible(isVisible());

            areaOption = new PolygonOptions()
                    .visible(isVisible())
                    .strokeColor(color)
                    .fillColor(Color.argb(
                            0x80, Color.red(color), Color.green(color), Color.blue(color)
                    ))
                    .strokeWidth(3);
            JSONArray areaBorderVertices = facultyJSON.getJSONArray("facultyAreaBorder");
            for (int i = 0; i < areaBorderVertices.length(); i++) {
                JSONObject vertexPosition = areaBorderVertices.getJSONObject(i);
                areaOption.add(new LatLng(
                        vertexPosition.getDouble("lat"),
                        vertexPosition.getDouble("lng")
                ));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setMap(GoogleMap map) {
        if (this.map != null) {
            throw new RuntimeException("Cannot set map to the marker more than once.");
        }
        markerOption.icon(BitmapDescriptorFactory.fromResource(getMarkerIconDrawableResource()));
        this.map = map;
        marker = map.addMarker(markerOption);
        area = map.addPolygon(areaOption);
    }

    public Marker getMarker() {
        return marker;
    }
}
