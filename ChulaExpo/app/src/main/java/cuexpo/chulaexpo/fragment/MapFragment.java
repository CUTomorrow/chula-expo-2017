package cuexpo.chulaexpo.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.utility.FacultyMapEntity;
import cuexpo.chulaexpo.utility.IMapEntity;
import cuexpo.chulaexpo.utility.NormalPinMapEntity;
import cuexpo.chulaexpo.utility.PopbusRouteMapEntity;
import cuexpo.chulaexpo.utility.Resource;


public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private View rootView;
    protected static GoogleMap googleMap;
    private CardView pinList, infoCard;
    private boolean isShowingPinList = false;
    private boolean isShowingInfoCard = false;
    private ImageView showFaculty, showLandmark, showInfo, showInterest, showCanteen, showToilet,
            showBusStop, showBusLine1, showBusLine2, showBusLine3, closeInfoCard, pinIcon;
    private TextView facility, description;

//    private Application mainApp = getActivity().getApplication();
    HashMap<String, PopbusRouteMapEntity> popbusRoutes = new HashMap<>();
    HashMap<Integer, FacultyMapEntity> faculties = new HashMap<>();
    ArrayList<NormalPinMapEntity> infoPointsPins = new ArrayList<>();
    ArrayList<NormalPinMapEntity> landmarksPins = new ArrayList<>();
    ArrayList<NormalPinMapEntity> cuTourStationPins = new ArrayList<>();

    private void initializeFaculties() {
        try {
            JSONArray facultiesJSON = new JSONArray(
                    getContext().getResources().getString(R.string.jsonFacultyMap)
            );
            for (int i = 0; i < facultiesJSON.length(); i++) {
                JSONObject facultyData = facultiesJSON.getJSONObject(i);
                faculties.put(facultyData.getInt("id"), new FacultyMapEntity(facultyData));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeCUTourStation() {
        try {
            JSONArray cuTourStationJSON = new JSONArray(
                    getContext().getResources().getString(R.string.jsonCUTourStation)
            );
            for (int i = 0; i < cuTourStationJSON.length(); i++) {
                JSONObject cuTourStationData = cuTourStationJSON.getJSONObject(i);
                cuTourStationPins.add(new NormalPinMapEntity(cuTourStationData, NormalPinMapEntity.POPBUS_STATION_PIN));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void initializePopbusRoutes() {
        try {
            JSONArray routesJSON = new JSONArray(
                    getContext().getResources().getString(R.string.jsonPopbusRoutes)
            );
            for (int i = 0; i < routesJSON.length(); i++) {
                JSONObject routeData = routesJSON.getJSONObject(i);
                popbusRoutes.put(routeData.getString("name"), new PopbusRouteMapEntity(routeData));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeMapData() {
        initializeFaculties();
        initializePopbusRoutes();
//        initializeInfoPoints();
//        initializeLandmarks();
        initializeCUTourStation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        // Get View
        pinList = (CardView) rootView.findViewById(R.id.pin_list);
        infoCard = (CardView) rootView.findViewById(R.id.info_card);
        closeInfoCard = (ImageView) rootView.findViewById(R.id.close_info);
        showFaculty = (ImageView) rootView.findViewById(R.id.show_faculty_city);
        showLandmark = (ImageView) rootView.findViewById(R.id.show_landmark);
        showInfo = (ImageView) rootView.findViewById(R.id.show_info);
        showInterest = (ImageView) rootView.findViewById(R.id.show_interest);
        showCanteen = (ImageView) rootView.findViewById(R.id.show_canteen);
        showToilet = (ImageView) rootView.findViewById(R.id.show_toilet);
        showBusStop = (ImageView) rootView.findViewById(R.id.show_bus_stop);
        showBusLine1 = (ImageView) rootView.findViewById(R.id.show_bus_line_1);
        showBusLine2 = (ImageView) rootView.findViewById(R.id.show_bus_line_2);
        showBusLine3 = (ImageView) rootView.findViewById(R.id.show_bus_line_3);
        // Get Card Content
        pinIcon = (ImageView) rootView.findViewById(R.id.pin_icon);
        facility = (TextView) rootView.findViewById(R.id.facility);
        description = (TextView) rootView.findViewById(R.id.description);

        // Set OnClickListener
        rootView.findViewById(R.id.show_hide_pin).setOnClickListener(showPinListOnClick);
        rootView.findViewById(R.id.show_current_location).setOnClickListener(showCurrentLocation);
        pinList.setOnClickListener(focusOCL);
        rootView.findViewById(R.id.info_card).setOnClickListener(focusOCL);
        closeInfoCard.setOnClickListener(closeOCL);

        rootView.findViewById(R.id.faculty_city).setOnClickListener(showFacultyOCL);
        rootView.findViewById(R.id.landmark).setOnClickListener(showLandmarkOCL);
        rootView.findViewById(R.id.info).setOnClickListener(showInfoOCL);
        rootView.findViewById(R.id.interest).setOnClickListener(showInterestOCL);
        rootView.findViewById(R.id.canteen).setOnClickListener(showCanteenOCL);
        rootView.findViewById(R.id.toilet).setOnClickListener(showToiletOCL);
        rootView.findViewById(R.id.bus_stop).setOnClickListener(showBusStopOCL);
        showBusLine1.setOnClickListener(showBusLine1OCL);
        showBusLine2.setOnClickListener(showBusLine2OCL);
        showBusLine3.setOnClickListener(showBusLine3OCL);

        // Set visibility
        showFaculty.setSelected(true);
        showBusLine1.setSelected(true);
        showBusLine2.setSelected(true);
        showBusLine3.setSelected(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);
        initializeMapData();
        return rootView;
    }

    private View.OnClickListener showPinListOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideInfoCard();
            if (isShowingPinList) {
                isShowingPinList = false;
                ObjectAnimator.ofFloat(pinList, "x", dpToPx(12), dpToPx(-200)).start();
            } else {
                isShowingPinList = true;
                ObjectAnimator.ofFloat(pinList, "x", dpToPx(-200), dpToPx(12)).start();
            }
        }
    };

    private View.OnClickListener showCurrentLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hidePinList();
            Log.d("current location", "true");
            showInfoCard(-1, "Current Location", "iScale 404 ชั้น 4 ตึก 100 ปี คณะวิศวกรรมศาสาตร์", R.color.black);
        }
    };

    private View.OnClickListener showFacultyOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showFaculty.isSelected()){
                showFaculty.setSelected(false);
                setAllFacultiesVisibility(false);
            } else {
                showFaculty.setSelected(true);
                setAllFacultiesVisibility(true);
            }
        }
    };

    private void setAllFacultiesVisibility(boolean isVisible) {
        for (IMapEntity faculty : faculties.values()) {
            faculty.setVisible(isVisible);
        }
    }

    private View.OnClickListener showLandmarkOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showLandmark.isSelected()){
                showLandmark.setSelected(false);
            } else {
                showLandmark.setSelected(true);
            }
        }
    };

    private View.OnClickListener showInfoOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showInfo.isSelected()){
                showInfo.setSelected(false);
            } else {
                showInfo.setSelected(true);
            }
        }
    };

    private View.OnClickListener showInterestOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showInterest.isSelected()){
                showInterest.setSelected(false);
            } else {
                showInterest.setSelected(true);
            }
        }
    };

    private View.OnClickListener showCanteenOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showCanteen.isSelected()){
                showCanteen.setSelected(false);
            } else {
                showCanteen.setSelected(true);
            }
        }
    };

    private View.OnClickListener showToiletOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showToilet.isSelected()){
                showToilet.setSelected(false);
            } else {
                showToilet.setSelected(true);
            }
        }
    };

    private View.OnClickListener showBusStopOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusStop.isSelected()){
                showBusStop.setSelected(false);
            } else {
                showBusStop.setSelected(true);
            }
        }
    };

    private View.OnClickListener showBusLine1OCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusLine1.isSelected()){
                showBusLine1.setSelected(false);
                popbusRoutes.get("1").setVisible(false);
            } else {
                showBusLine1.setSelected(true);
                popbusRoutes.get("1").setVisible(true);
            }
        }
    };

    private View.OnClickListener showBusLine2OCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusLine2.isSelected()){
                showBusLine2.setSelected(false);
                popbusRoutes.get("2").setVisible(false);
            } else {
                showBusLine2.setSelected(true);
                popbusRoutes.get("2").setVisible(true);
            }
        }
    };

    private View.OnClickListener showBusLine3OCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusLine3.isSelected()){
                showBusLine3.setSelected(false);
                popbusRoutes.get("3").setVisible(false);
            } else {
                showBusLine3.setSelected(true);
                popbusRoutes.get("3").setVisible(true);
            }
        }
    };

//    private void enableMyLocation() {
//        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission to access the location is missing.
//            PermissionUtils.requestPermission((AppCompatActivity) this.getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        } else if (googleMap != null) {
//            try {
//                googleMap.setMyLocationEnabled(true);
//
//                if (locationSource == null) {
//                    locationSource = new MyLocationSource(this.getContext());
//                    googleMap.setLocationSource(locationSource);
//                }
//
//                Location location = locationSource.getLastKnownLocation();
//                if (location != null) {
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                            new LatLng(location.getLatitude(), location.getLongitude()),
//                            17
//                    ), 1000, null);
//                    Snackbar.make(rootView, "Showing your current location...", Snackbar.LENGTH_SHORT).show();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

    private View.OnClickListener myLocationListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                enableMyLocation();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
//        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
//            return;
//        }
//
//        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
//                Manifest.permission.ACCESS_FINE_LOCATION)) {
//            // Enable the my location layer if the permission has been granted.
//            enableMyLocation();
//        } else {
//            // Display the missing permission error dialog when the fragments resume.
//            PermissionUtils.PermissionDeniedDialog
//                    .newInstance(true).show(this.getChildFragmentManager(), "dialog");
//        }
    }

    private void showInfoCard(int icon, String facilityString, String descriptionString, int color) {
        hidePinList();

        // Set Content
        if (icon != -1) {
            pinIcon.setImageResource(icon);
            pinIcon.setVisibility(View.VISIBLE);
        } else {
            pinIcon.setVisibility(View.GONE);
        }
        facility.setText(facilityString);
        facility.setTextColor(getResources().getColor(color));
        description.setText(descriptionString);

        // Animate
        if (isShowingInfoCard) return;
        isShowingInfoCard = true;
        infoCard.setVisibility(View.VISIBLE);
        closeInfoCard.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(infoCard, "alpha", 0, 1).setDuration(300).start();
        ObjectAnimator.ofFloat(closeInfoCard, "alpha", 0, 1).setDuration(300).start();
    }

    private View.OnClickListener closeOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideInfoCard();
        }
    };

    private void hidePinList() {
        if (isShowingPinList) {
            isShowingPinList = false;
            pinList.animate().translationX(dpToPx(0));
        }
    }

    private void hideInfoCard() {
        if (!isShowingInfoCard) return;
        isShowingInfoCard = false;

        ObjectAnimator.ofFloat(closeInfoCard, "alpha", 1, 0).setDuration(300).start();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(infoCard, "alpha", 1, 0);
        alpha.setDuration(300);
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                infoCard.setVisibility(View.GONE);
                closeInfoCard.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        alpha.start();
    }

    private View.OnClickListener focusOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Add Faculty
        for (IMapEntity facultyEntry : faculties.values()) {
            facultyEntry.setMap(googleMap);
        }
        // Add Popbus routes
        for (PopbusRouteMapEntity routeEntry : popbusRoutes.values()) {
            routeEntry.setMap(googleMap);
        }

        googleMap.setOnMarkerClickListener(markerOCL);
    }

    GoogleMap.OnMarkerClickListener markerOCL = new GoogleMap.OnMarkerClickListener() {
        public boolean onMarkerClick(Marker marker) {
            if (MapFragment.googleMap != null) {
                MapFragment.googleMap.animateCamera(
                        CameraUpdateFactory.newLatLng(marker.getPosition()),
                        500,
                        null
                );
            }

            for (FacultyMapEntity facultyEntry : faculties.values()) {
                if (facultyEntry.getMarker().equals(marker)) {
                    int id = facultyEntry.getFacultyId();
                    int icon = Resource.getDrawable("pin_" + id);
                    String description = facultyEntry.getNameTh();
                    showInfoCard(icon, "Faculty", description, Resource.getColor("f"+id));
                    return true;
                }
            }
            return true;
        }
    };
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
