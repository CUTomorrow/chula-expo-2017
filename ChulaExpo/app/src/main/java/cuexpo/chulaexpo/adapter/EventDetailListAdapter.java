package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.dao.ActivityItemDao;
import cuexpo.chulaexpo.dao.PlaceItemDao;
import cuexpo.chulaexpo.dao.PlaceItemResultDao;
import cuexpo.chulaexpo.dao.RoundDao;
import cuexpo.chulaexpo.dao.RoundResult;
import cuexpo.chulaexpo.fragment.EventDetailFragment;
import cuexpo.chulaexpo.manager.HttpManager;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APTX-4869 (LOCAL) on 1/23/2017.
 */

public class EventDetailListAdapter extends BaseAdapter implements OnMapReadyCallback {
    private static LayoutInflater inflater;
    private Context context;
    private String id, room, place, contact, time, description;
    public double lat, lng;
    private String[] imageUrls;
    private boolean canReserve = true;

    public EventDetailListAdapter(Context context, String id, String place, String contact,
                                  String time, String description, double lat, double lng,
                                  String[] imageUrls) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.id = id;
        this.place = place;
        this.contact = contact;
        this.time = time;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.imageUrls = imageUrls;

        Call<PlaceItemDao> call = HttpManager.getInstance().getService().loadPlaceItem(place);
        call.enqueue(callbackPlace);

//        Call<RoundDao> roundCall = HttpManager.getInstance().getService().loadRoundsById(id);
//        roundCall.enqueue(callbackPlace);
    }

    Callback<PlaceItemDao> callbackPlace = new Callback<PlaceItemDao>() {
        @Override
        public void onResponse(Call<PlaceItemDao> call, Response<PlaceItemDao> response) {
            if (response.isSuccessful()) {
                PlaceItemResultDao dao = response.body().getResults();
                place = dao.getName().getTh();
                notifyDataSetChanged();
            } else {
                try {
                    Log.e("fetch error", response.errorBody().string());
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(Call<PlaceItemDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View eventDetailView;

        switch (position) {
            case 0:
                convertView = inflater.inflate(R.layout.item_event_detail_schedule, null);
                if (time == null) ((TextView) convertView.findViewById(R.id.schedule)).setText("-");
                else ((TextView) convertView.findViewById(R.id.schedule)).setText(time);
                if (contact == null) ((TextView) convertView.findViewById(R.id.responsible_person)).setText("-");
                else ((TextView) convertView.findViewById(R.id.responsible_person)).setText(contact);
                if (place == null) ((TextView) convertView.findViewById(R.id.location)).setText("-");
                else ((TextView) convertView.findViewById(R.id.location)).setText(place);
                convertView.findViewById(R.id.reserve_button).setOnClickListener(reserveOCL);
                break;
            case 1:
                convertView = inflater.inflate(R.layout.item_event_detail_detail, null);
                TextView detail = (TextView) convertView.findViewById(R.id.detail);
                detail.setText(description);
                LinearLayout pictureLayout = (LinearLayout) convertView.findViewById(R.id.picture_layout);
                if (imageUrls.length == 0) convertView.findViewById(R.id.image_scroll_view).setVisibility(View.GONE);
                for(String imageUrl: imageUrls){
                    ImageView image = new ImageView(context);
                    RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(
                            dpToPx(44), dpToPx(44));
                    image.setLayoutParams(imageParam);
                    Glide.with(context)
                            .load("https://staff.chulaexpo.com" + imageUrl)
                            .placeholder(R.color.blackOverlay)
                            .centerCrop()
                            .into(image);
                    pictureLayout.addView(image);
                }
                break;
            case 2:
                convertView = inflater.inflate(R.layout.item_event_detail_map, null);
                MapView mapView = (MapView) convertView.findViewById(R.id.mapView);
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
                break;
        }
        eventDetailView = convertView;

        return eventDetailView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
//        googleMap.setOnMarkerClickListener(this);
//        googleMap.setOnMapClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(18)
                        .build()
        ));
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_21))
        );
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener reserveOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO for Boom-sama
            if(canReserve){
                
            } else {

            }
        }
    };
}
