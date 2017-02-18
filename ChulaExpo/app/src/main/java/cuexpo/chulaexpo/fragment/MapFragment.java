package cuexpo.chulaexpo.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cuexpo.chulaexpo.R;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private CardView pinList;
    private boolean isShowingPinList = false;
    private ImageView showFaculty, showLandmark, showInfo, showInterest, showCanteen, showToilet,
            showBusStop, showBusLine1, showBusLine2, showBusLine3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        pinList = (CardView) rootView.findViewById(R.id.pin_list);
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
        // Set OnClickListener
        rootView.findViewById(R.id.show_hide_pin).setOnClickListener(showPinListOnClick);
        rootView.findViewById(R.id.show_current_location).setOnClickListener(showCurrentLocation);
        showFaculty.setOnClickListener(showFacultyOCL);
        showLandmark.setOnClickListener(showLandmarkOCL);
        showInfo.setOnClickListener(showInfoOCL);
        showInterest.setOnClickListener(showInterestOCL);
        showCanteen.setOnClickListener(showCanteenOCL);
        showToilet.setOnClickListener(showToiletOCL);
        showBusStop.setOnClickListener(showBusStopOCL);
        showBusLine1.setOnClickListener(showBusLine1OCL);
        showBusLine2.setOnClickListener(showBusLine2OCL);
        showBusLine3.setOnClickListener(showBusLine3OCL);

//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main_map);
//        mapFragment.getMapAsync(this);
        return rootView;
    }

    private View.OnClickListener showPinListOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isShowingPinList) {
                isShowingPinList = false;
                pinList.animate().translationX(dpToPx(0));
            } else {
                isShowingPinList = true;
                pinList.animate().translationX(dpToPx(212));
            }
        }
    };

    private View.OnClickListener showCurrentLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hidePinList();
//            if (isShowingPinList) {
//                isShowingPinList = false;
//                pinList.animate().translationY(dpToPx(0));
//            } else {
//                isShowingPinList = true;
//                pinList.animate().translationY(dpToPx(212));
//            }
        }
    };

    private View.OnClickListener showFacultyOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showFaculty.isSelected()){
                showFaculty.setSelected(false);
            } else {
                showFaculty.setSelected(true);
            }
        }
    };

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
            } else {
                showBusLine1.setSelected(true);
            }
        }
    };

    private View.OnClickListener showBusLine2OCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusLine2.isSelected()){
                showBusLine2.setSelected(false);
            } else {
                showBusLine2.setSelected(true);
            }
        }
    };

    private View.OnClickListener showBusLine3OCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showBusLine3.isSelected()){
                showBusLine3.setSelected(false);
            } else {
                showBusLine3.setSelected(true);
            }
        }
    };

    private void hidePinList() {
        if (isShowingPinList) {
            isShowingPinList = false;
            pinList.animate().translationX(dpToPx(0));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
