package cuexpo.cuexpo2017.utility;

import android.graphics.Color;
import android.util.Log;

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

import cuexpo.cuexpo2017.R;

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
    private String type;

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

    public int getColor() {
        if (facultyId == 34) return Color.parseColor("#ABABAB");
        return color;
    }

    private int facultyId;

    public int getMarkerIconDrawableResource() {
        return getMarkerIconDrawableResource(facultyId);
    }

    public String getType() {
        return type;
    }

    private int getMarkerIconDrawableResource(int facultyId) {
        Log.d("faculty id", ""+facultyId);
//        return Resource.getDrawable("pin_"+facultyId);
        switch (facultyId) {
            // Area
            case 1: return R.drawable.pin_1;
            case 2: return R.drawable.pin_2;
            case 3: return R.drawable.pin_3;
            case 4: return R.drawable.pin_4;
            case 5: return R.drawable.shop;
            case 6: return R.drawable.sponsor;
            case 7: return R.drawable.sponsor;
            // City
            case 10: return R.drawable.pin_10;
            case 11: return R.drawable.pin_11;
            case 12: return R.drawable.pin_12;
            case 13: return R.drawable.pin_12;
            // Faculty
            case 21: return R.drawable.pin_21;
            case 22: return R.drawable.pin_22;
            case 23: return R.drawable.pin_23;
            case 24: return R.drawable.pin_24;
            case 25: return R.drawable.pin_25;
            case 26: return R.drawable.pin_26;
            case 27: return R.drawable.pin_27;
            case 28: return R.drawable.pin_28;
            case 29: return R.drawable.pin_29;
            case 30: return R.drawable.pin_30;
            case 31: return R.drawable.pin_31;
            case 20: return R.drawable.pin_31;
            case 32: return R.drawable.pin_32;
            case 33: return R.drawable.pin_33;
            case 34: return R.drawable.pin_34;
            case 35: return R.drawable.pin_35;
            case 36: return R.drawable.pin_36;
            case 37: return R.drawable.pin_37;
            case 38: return R.drawable.pin_38;
            case 39: return R.drawable.pin_39;
            case 40: return R.drawable.pin_40;
            case 41: return R.drawable.pin_41;
            case 42: return R.drawable.pin_42;
            case 43: return R.drawable.pin_43;
            case 44: return R.drawable.pin_44;
            case 45: return R.drawable.pin_45;
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
            type = facultyJSON.getString("type");
            facultyId = facultyJSON.getInt("id");

            markerOption = new MarkerOptions()
                    .position(centerPoint)
//                    .title(nameTh)
                    .visible(isVisible());

            areaOption = new PolygonOptions()
                    .visible(isVisible())
                    .strokeColor(color)
                    .fillColor(Color.argb(
                            0x80, Color.red(color), Color.green(color), Color.blue(color)
                    ))
                    .strokeWidth(3);
            JSONArray areaBorderVertices = facultyJSON.getJSONArray("areaBorder");
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
