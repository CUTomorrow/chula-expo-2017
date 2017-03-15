package cuexpo.cuexpo2017.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.MainActivity;
import cuexpo.cuexpo2017.dao.DeleteResultDao;
import cuexpo.cuexpo2017.dao.PlaceItemDao;
import cuexpo.cuexpo2017.dao.PlaceItemResultDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;
import cuexpo.cuexpo2017.fragment.ReservedCheckFragment;
import cuexpo.cuexpo2017.manager.HttpManager;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.utility.IGoToMapable;
import cuexpo.cuexpo2017.utility.NormalPinMapEntity;
import cuexpo.cuexpo2017.utility.NotificationReceiver;
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
    private boolean isFavourite = false;
    private Fragment fragment;
    private RoundDao activityDao;
    private RoundDao myReserveDao;
    private String reserveId;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private boolean access;
    private SharedPreferences.Editor favouritePlaceEditor;
    private SharedPreferences.Editor reservedPlaceEditor;
    private SharedPreferences favouritePlace;
    private SharedPreferences reservedPlace;
    private String startTimeISO;
    private String endTimeISO;
    private HashMap<String, Integer> NotificationIDMapping;
    private boolean isLoading = true;
    public static int notiID = 0;

    public EventDetailListAdapter(
            Fragment fragment, Context context, String id, String place,
            String contact, String time, String startTimeISO, String endTimeISO, String description,
            double lat, double lng, String[] imageUrls, String title
    ) {

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

        this.startTimeISO = startTimeISO;
        this.endTimeISO = endTimeISO;

        NotificationIDMapping = new HashMap<>();

        SharedPreferences sharedPref2 = context.getSharedPreferences("FacebookInfo", context.MODE_PRIVATE);
        access = !sharedPref2.getString("fbToken", "").equals("");

        sharedPref = context.getSharedPreferences("favouriteActivity", Context.MODE_PRIVATE);
        favouritePlace = context.getSharedPreferences("FavouritePlaces", Context.MODE_PRIVATE);
        reservedPlace = context.getSharedPreferences("ReservedPlaces", Context.MODE_PRIVATE);

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

        Call<RoundDao> roundCall = HttpManager.getInstance().getService().loadRoundsById(id, range, "start");
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
                    Log.e("EventDetail", "Call Place Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PlaceItemDao> call, Throwable t) {
            Log.e("EventDetail", "Call Place Fail");
        }
    };

    Callback<RoundDao> callbackRound = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                activityDao = response.body();
                if (activityDao.getResults().size() == 0) {
                    canReserve = false;
                    isReserve = false;
                    if (sharedPref.contains(id)) {
                        isFavourite = true;
                    } else {
                        isFavourite = false;
                    }
                    isLoading = false;
                    notifyDataSetChanged();
                } else if (activityDao.getResults().get(0).getSeats().getFullCapacity() == 0) {
                    canReserve = false;
                    isReserve = false;
                    isFavourite = false;
                    isLoading = false;
                    notifyDataSetChanged();
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
                    Log.e("Event Detail", "ACCESS " + access);
                    if (access) {
                        Call<RoundDao> callReservedList = HttpManager.getInstance().getService().loadReservedRounds();
                        callReservedList.enqueue(callbackReservedList);
                    } else {
                        canReserve = true;
                        isReserve = false;
                        isFavourite = false;
                        isLoading = false;
                        notifyDataSetChanged();
                    }
                }
            } else {
                try {
                    Log.e("EventDetail", "Call Round List Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Log.e("EventDetail", "Call Round List Fail");
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
                                    break search;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                isLoading = false;
                notifyDataSetChanged();
            } else {
                try {
                    Log.e("EventDetail", "Call Reserved Round List Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Log.e("EventDetail", "Call Reserved Round List Fail ");
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
                    if (!isLoading) {
                        convertView.findViewById(R.id.event_detail_loading).setVisibility(View.GONE);
                        ((TextView) convertView.findViewById(R.id.button_title)).setText("จอง EVENT");
                        ((TextView) convertView.findViewById(R.id.button_detail)).setText("Event นี้ต้องทำการจองเพื่อเข้าร่วม");
                        ((TextView) convertView.findViewById(R.id.button_title)).
                                setTextColor(ContextCompat.getColor(parent.getContext(), R.color.black));
                        ((TextView) convertView.findViewById(R.id.button_detail)).
                                setTextColor(ContextCompat.getColor(parent.getContext(), R.color.black));
                        convertView.findViewById(R.id.reserve_button).
                                setBackgroundResource(R.drawable.shape_round_rec_pink_stroke);
                        ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_ticket_pink);
                    }
                } else {
                    if (isReserve) {
                        if (!isLoading) {
                            convertView.findViewById(R.id.event_detail_loading).setVisibility(View.GONE);
                            ((TextView) convertView.findViewById(R.id.button_title)).setText("จอง Event แล้ว");
                            ((TextView) convertView.findViewById(R.id.button_detail)).setText("กดเพื่อยกเลิกการสนใจ Event");
                            ((TextView) convertView.findViewById(R.id.button_title)).
                                    setTextColor(ContextCompat.getColor(parent.getContext(), R.color.white));
                            ((TextView) convertView.findViewById(R.id.button_detail)).
                                    setTextColor(ContextCompat.getColor(parent.getContext(), R.color.white));
                            convertView.findViewById(R.id.reserve_button).
                                    setBackgroundResource(R.drawable.shape_round_rec_pink);
                            ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_ticket);
                        }
                    } else {
                        if (!isFavourite) {
                            if (!isLoading) {
                                convertView.findViewById(R.id.event_detail_loading).setVisibility(View.GONE);
                                ((TextView) convertView.findViewById(R.id.button_title)).setText("สนใจ Event");
                                ((TextView) convertView.findViewById(R.id.button_detail)).setText("Event นี้สามารถเข้าร่วมได้โดยไม่ต้องจอง");
                                ((TextView) convertView.findViewById(R.id.button_title)).
                                        setTextColor(ContextCompat.getColor(parent.getContext(), R.color.black));
                                ((TextView) convertView.findViewById(R.id.button_detail)).
                                        setTextColor(ContextCompat.getColor(parent.getContext(), R.color.black));
                                convertView.findViewById(R.id.reserve_button).
                                        setBackgroundResource(R.drawable.shape_round_rec_pink_stroke);
                                ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_small_star_pink);
                            }
                        } else {
                            if (!isLoading) {
                                convertView.findViewById(R.id.event_detail_loading).setVisibility(View.GONE);
                                ((TextView) convertView.findViewById(R.id.button_title)).setText("สนใจ Event แล้ว");
                                ((TextView) convertView.findViewById(R.id.button_detail)).setText("กดเพื่อยกเลิกการสนใจ Event");
                                ((TextView) convertView.findViewById(R.id.button_title)).
                                        setTextColor(ContextCompat.getColor(parent.getContext(), R.color.white));
                                ((TextView) convertView.findViewById(R.id.button_detail)).
                                        setTextColor(ContextCompat.getColor(parent.getContext(), R.color.white));
                                convertView.findViewById(R.id.reserve_button).
                                        setBackgroundResource(R.drawable.shape_round_rec_pink);
                                ((ImageView) convertView.findViewById(R.id.button_icon)).setImageResource(R.drawable.ic_small_star);
                            }
                        }
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
                            .load("https://api.chulaexpo.com" + imageUrl)
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
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(18)
                        .build()
        ));
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_event))
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
        cuexpo.cuexpo2017.dao.Location location = new cuexpo.cuexpo2017.dao.Location();
        location.setLatitude(lat);
        location.setLongitude(lng);
        if (act instanceof IGoToMapable) {
            ((IGoToMapable) act).goToMap(new NormalPinMapEntity(title, location, "Event"));
        }
        FragmentManager fragmentManager = fragment.getFragmentManager();
        int stackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackCount; i++) fragmentManager.popBackStack();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void scheduleNotification() {

        if (!DateUtil.isSameDay(this.startTimeISO, this.endTimeISO)) {
            // Don't show notification if the duration of event isn't the same day.
            return;
        }

        String notiDesc = this.time + "\n" + this.description;
        if (notiDesc.length() > 140) {
            notiDesc = notiDesc.subSequence(0, 140) + "...";
        }

        Intent notiIntentOpenApp = new Intent(context, MainActivity.class);
        PendingIntent openAppIntent = PendingIntent.getActivity(context, 0, notiIntentOpenApp, 0);

        NotificationCompat.Builder notiBuilder =
                new NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.noti_logo)
                        .setContentTitle("Chula Expo : " + this.place)
                        .setContentText(this.time)
                        .setContentIntent(openAppIntent)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notiDesc))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);

        int curNotiID = 0;
        if (NotificationIDMapping.containsKey(this.id)) {
            curNotiID = NotificationIDMapping.get(this.id);
        } else {
            curNotiID = notiID++;
            NotificationIDMapping.put(this.id, curNotiID);
        }

        long notificationTimestamp = DateUtil.convertToMillisecond(this.startTimeISO);
        notificationTimestamp -= 15 * 60 * 1000; // Show noti before 15 minutes

        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < notificationTimestamp) {
            Intent notificationIntent = new Intent(this.context, NotificationReceiver.class);
            notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notiBuilder.build());
            notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, curNotiID);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, curNotiID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            long futureInMillis = SystemClock.elapsedRealtime() + notificationTimestamp - currentTimestamp;
//            long futureInMillis = SystemClock.elapsedRealtime() + 5000;
            AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    private View.OnClickListener reserveOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (canReserve) {
                if (access) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.event_detail_overlay, ReservedCheckFragment.newInstance(id, title, lat, lng));
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    error("การจอง");
                }
            } else {
                if (isReserve) {
                    Call<DeleteResultDao> callDelete = HttpManager.getInstance().getService().removeRound(reserveId);
                    callDelete.enqueue(callbackDelete);
                    canReserve = true;
                    notifyDataSetChanged();
                } else {
                    if (!isFavourite) {
                        editor = sharedPref.edit();
                        editor.putString(id, "");
                        editor.apply();
                        favouritePlaceEditor = favouritePlace.edit();
                        favouritePlaceEditor.putString(id, title + "," + lat + "," + lng);
                        favouritePlaceEditor.apply();
                        isFavourite = true;
                        // Add alert notification when time arrive
                        scheduleNotification();

                        notifyDataSetChanged();
                    } else {
                        isFavourite = false;
                        if (sharedPref.contains(id)) {
                            editor = sharedPref.edit();
                            editor.remove(id);
                            editor.apply();
                        }
                        if (favouritePlace.contains(id)) {
                            favouritePlaceEditor = favouritePlace.edit();
                            favouritePlaceEditor.remove(id);
                            favouritePlaceEditor.apply();
                        }
                        notifyDataSetChanged();
                    }
                }
            }
        }
    };

    Callback<DeleteResultDao> callbackDelete = new Callback<DeleteResultDao>() {
        @Override
        public void onResponse(Call<DeleteResultDao> call, Response<DeleteResultDao> response) {
            if (response.isSuccessful()) {
                DeleteResultDao dao = response.body();
                if (dao.getSuccess() && reservedPlace.contains(id)) {
                    reservedPlaceEditor = reservedPlace.edit();
                    reservedPlaceEditor.remove(id);
                    reservedPlaceEditor.apply();
                }
                Toast.makeText(Contextor.getInstance().getContext(), dao.getSuccess() ? "ยกเลิกการจองสำเร็จ" : "ยกเลิกการจองไม่สำเร็จ"
                        + dao.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("EventDetail", "Delete Round " + dao.getSuccess() + " " + dao.getMessage());
            } else {
                Toast.makeText(Contextor.getInstance().getContext(), "ยกเลิกการจองไม่สำเร็จ", Toast.LENGTH_LONG).show();
                Log.e("EventDetail", "Delete Round Not Success " + response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<DeleteResultDao> call, Throwable t) {
            Log.e("EventDetail", "Delete Round Fail");
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
        }
    };

    public void error(String errorMsg) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("ขออภัย");
        alert.setMessage("ฟังก์ชัน" + errorMsg + "เปิดให้เฉพาะ Facebook User เท่านั้น!");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert2 = alert.create();
        alert2.show();
    }
}
