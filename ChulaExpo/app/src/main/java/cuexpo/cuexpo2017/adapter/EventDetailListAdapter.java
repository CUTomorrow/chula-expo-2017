package cuexpo.cuexpo2017.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemDao;
import cuexpo.cuexpo2017.dao.DeleteResultDao;
import cuexpo.cuexpo2017.dao.PlaceItemDao;
import cuexpo.cuexpo2017.dao.PlaceItemResultDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.fragment.ReservedCheckFragment;
import cuexpo.cuexpo2017.manager.DateConversionManager;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.IGoToMapable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by APTX-4869 (LOCAL) on 1/23/2017.
 */

public class EventDetailListAdapter extends BaseAdapter implements OnMapReadyCallback {
    private static LayoutInflater inflater;
    private Context context;
    private String id, room, place, contact, time, description, title;
    public double lat, lng;
    private String[] imageUrls;
    private boolean canReserve = true;
    private boolean isReserve = false;
    private Fragment fragment;
    private RoundDao activityDao;
    private RoundDao myReserveDao;
    private String reserveId;

    public EventDetailListAdapter(Fragment fragment, Context context, String id, String place,
                                  String contact, String time, String description,
                                  double lat, double lng, String[] imageUrls, String title) {
        this.fragment = fragment;
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
        this.title = title;

        Call<PlaceItemDao> call = HttpManager.getInstance().getService().loadPlaceItem(place);
        call.enqueue(callbackPlace);

        checkId();
    }

    private void checkId() {
        JSONObject range = new JSONObject();
        try {
            String startString = "2017-03-15T00:00:00.000Z";
            range.put("gte", startString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<RoundDao> roundCall = HttpManager.getInstance().getService().loadRoundsById(id, "start", range);
        roundCall.enqueue(callbackRound);
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

    Callback<RoundDao> callbackRound = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                activityDao = response.body();
                if (activityDao.getResults().size() == 0) {
                    canReserve = false;
                } else if (activityDao.getResults().get(0).getSeats().getFullCapacity() == 0) {
                    canReserve = false;
                } else {
                    JSONObject range = new JSONObject();
                    try {
                        String startString = "2017-03-15T00:00:00.000Z";
                        String endString = "2017-03-19T23:59:00.000Z";
                        range.put("gte", startString);
                        range.put("lte", endString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Call<RoundDao> callReservedList = HttpManager.getInstance().getService().loadReservedRounds(range);
                    callReservedList.enqueue(callbackReservedList);
                }
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    Callback<RoundDao> callbackReservedList = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                myReserveDao = response.body();
                search:
                {
                    for (int i = 0; i < myReserveDao.getResults().size(); i++) {
                        for (int j = 0; j < activityDao.getResults().size(); j++) {
                            if (myReserveDao.getResults().get(i).getActivityId().
                                    equals(activityDao.getResults().get(j).getActivityId())) {
                                if (myReserveDao.getResults().get(i).getId().
                                        equals(activityDao.getResults().get(j).getId())) {
                                    reserveId = myReserveDao.getResults().get(i).getId();
                                    isReserve = true;
                                    canReserve = false;
                                    notifyDataSetChanged();
                                    break search;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
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
                if (contact == null)
                    ((TextView) convertView.findViewById(R.id.responsible_person)).setText("-");
                else
                    ((TextView) convertView.findViewById(R.id.responsible_person)).setText(contact);
                if (place == null)
                    ((TextView) convertView.findViewById(R.id.location)).setText("-");
                else ((TextView) convertView.findViewById(R.id.location)).setText(place);
                convertView.findViewById(R.id.reserve_button).setOnClickListener(reserveOCL);
                if (canReserve) {
                    ((TextView) convertView.findViewById(R.id.button_title)).setText("จอง EVENT");
                    ((TextView) convertView.findViewById(R.id.button_detail)).setText("Event นี้ต้องทำการจองเพื่อเข้าร่วม");
                    ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_ticket_black);
                } else {
                    if (isReserve) {
                        ((TextView) convertView.findViewById(R.id.button_title)).setText("ยกเลิกการจอง Event");
                        ((TextView) convertView.findViewById(R.id.button_detail)).setText("Event นี้สามารถยกเลิกได้");
                        ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_ticket_black);
                    } else {
                        ((TextView) convertView.findViewById(R.id.button_title)).setText("สนใจ Event");
                        ((TextView) convertView.findViewById(R.id.button_detail)).setText("Event นี้สามารถเข้าร่วมได้โดยไม่ต้องจอง");
                        ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_ticket_black);
                    }
                }
                break;
            case 1:
                convertView = inflater.inflate(R.layout.item_event_detail_detail, null);
                TextView detail = (TextView) convertView.findViewById(R.id.detail);
                detail.setText(description);
                LinearLayout pictureLayout = (LinearLayout) convertView.findViewById(R.id.picture_layout);
                if (imageUrls.length == 0)
                    convertView.findViewById(R.id.image_scroll_view).setVisibility(View.GONE);
                for (String imageUrl : imageUrls) {
                    ImageView image = new ImageView(context);
                    RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(
                            dpToPx(44), dpToPx(44));
                    image.setLayoutParams(imageParam);
                    Glide.with(context)
                            .load("https://staff.chulaexpo.com" + imageUrl)
                            .placeholder(R.drawable.thumb)
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
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fav))
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
            ((IGoToMapable) act).goToMap(lat, lng);
        }
        fragment.getFragmentManager().popBackStack();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private View.OnClickListener reserveOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO for Boom-sama
            if (canReserve) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.event_detail_overlay, ReservedCheckFragment.newInstance(id, title));
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                if (isReserve) {
                    System.out.println("Already Reserved");
                    Call<DeleteResultDao> callDelete = HttpManager.getInstance().getService().removeRound(reserveId);
                    callDelete.enqueue(callbackDelete);
                    canReserve = true;
                    notifyDataSetChanged();
                } else {
                    System.out.println("Let's Love");
                }
            }
        }
    };

    Callback<DeleteResultDao> callbackDelete = new Callback<DeleteResultDao>() {
        @Override
        public void onResponse(Call<DeleteResultDao> call, Response<DeleteResultDao> response) {
            if (response.isSuccessful()) {
                DeleteResultDao dao = response.body();
                Toast.makeText(Contextor.getInstance().getContext(), dao.getSuccess() + dao.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                //Handle
                Log.e("HomeActivity", "Load Activities Not Success");
            }
        }

        @Override
        public void onFailure(Call<DeleteResultDao> call, Throwable t) {
            Log.e("HomeActivity", "Load Activities Fail");
        }
    };
}
