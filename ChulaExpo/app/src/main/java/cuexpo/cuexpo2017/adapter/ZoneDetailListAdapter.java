package cuexpo.cuexpo2017.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.utility.IGoToMapable;
import cuexpo.cuexpo2017.utility.NormalPinMapEntity;
import cuexpo.cuexpo2017.utility.Resource;

public class ZoneDetailListAdapter extends BaseAdapter implements OnMapReadyCallback {
    private static LayoutInflater inflater;
    private Context context;
    private Fragment fragment;
    private String id, type, website, description;
    private List<ActivityItemResultDao> eventList = new ArrayList<>();
    public double lat, lng;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    private int facultyId = 1;

    public ZoneDetailListAdapter(Fragment fragment, Context context, int facultyId, String id, String type, String website,
                                 String description, double lat, double lng) {
        this.fragment = fragment;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.facultyId = facultyId;
        this.id = id;
        this.type = type;
        this.website = website;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    public void setEventList(List<ActivityItemResultDao> eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        if (eventList.size() == 0) return 1;
        return 2 + eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View zoneDetailView;
        switch (position) {
            case 0:
                convertView = inflater.inflate(R.layout.item_zone_detail_detail, null);
                TextView detail = (TextView) convertView.findViewById(R.id.detail);
                detail.setText(description);
                MapView mapView = (MapView) convertView.findViewById(R.id.mapView);
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
                break;
            case 1:
                convertView = inflater.inflate(R.layout.item_list_header, null);
                ((TextView) convertView.findViewById(R.id.title)).setText("RELATED EVENT");
                ((TextView) convertView.findViewById(R.id.description)).setText("Event ที่เกี่ยวข้องทั้งหมด");
                ((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.ic_event);
                break;
            default:
                convertView = inflater.inflate(R.layout.item_event, null);
                ActivityItemResultDao event = eventList.get(position-2);
                ((TextView) convertView.findViewById(R.id.title)).setText(event.getName().getTh());
                String time = DateUtil.getDateThai(event.getStart());
                ((TextView) convertView.findViewById(R.id.time)).setText(time);

                SharedPreferences sharedPref = context.getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
                String zoneShortName = sharedPref.getString(event.getZone(), "");
                TextView eventTag = (TextView) convertView.findViewById(R.id.event_tag);
                eventTag.setText(zoneShortName);
                eventTag.setBackgroundResource(Resource.getColor(zoneShortName));
                for(int i=0;i<lightZone.length-1;i++){
                    if(zoneShortName.equals(lightZone[i])) {
                        eventTag.setTextColor(Color.BLACK);
                        break;
                    }
                }

                Glide.with(context)
                        .load("https://api.chulaexpo.com"+event.getBanner())
                        .placeholder(R.drawable.banner)
                        .centerCrop()
                        .into((ImageView) convertView.findViewById(R.id.event_image));
                break;
        }
        zoneDetailView = convertView;
        return zoneDetailView;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(18)
                        .build()
        ));
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.fromResource(Resource.getDrawable("pin_"+facultyId)))
        );
        googleMap.setOnMarkerClickListener(mapOCL);
    }

    private GoogleMap.OnMarkerClickListener mapOCL = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            goToBiggerMap();
            return true;
        }
    };

    private void goToBiggerMap() {
        Activity act = fragment.getActivity();
        if (act instanceof IGoToMapable) {
            ((IGoToMapable) act).goToMap(facultyId);
        }
        FragmentManager fragmentManager = fragment.getFragmentManager();
        int stackCount = fragmentManager.getBackStackEntryCount();
        for(int i=0; i<stackCount; i++) fragmentManager.popBackStack();
    }
}
