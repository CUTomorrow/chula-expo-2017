package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.List;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.MainActivity;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;
import cuexpo.cuexpo2017.utility.Resource;
import cuexpo.cuexpo2017.view.EventListItem;

/**
 * Created by APTX-4869 (LOCAL) on 1/25/2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnMapReadyCallback {

    private List<EventListItem> eventList;
    private boolean isSearching;
    private final short EVENT = 0;
    private final short HEADER = 1;
    private final short MAP = 2;
    private FragmentManager fragmentManager;
    private Context context;
    String[] lightZone = {"SCI", "ECON", "LAW", "VET"};

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time, tag;
        public ImageView thumbnail;
        public View view;
        public EventViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            tag = (TextView) view.findViewById(R.id.event_tag);
            thumbnail = (ImageView) view.findViewById(R.id.event_image);
            this.view = view;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView icon;
        public HeaderViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    public class MapViewHolder extends RecyclerView.ViewHolder {
        public TextView location;
        MapView mapView;
        public MapViewHolder(View view) {
            super(view);
            location = (TextView) view.findViewById(R.id.location);
            mapView = (MapView) view.findViewById(R.id.mapView);
        }
    }

    public SearchListAdapter(Context context, List<EventListItem> eventList, boolean isSearching, FragmentManager fragmentManager) {
        this.context = context;
        this.eventList = eventList;
        this.isSearching = isSearching;
        this.fragmentManager = fragmentManager;
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean searching) {
        isSearching = searching;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case HEADER:
                View headerView = inflater.inflate(R.layout.item_list_header, parent, false);
                viewHolder = new HeaderViewHolder(headerView);
                break;
            case MAP:
                View mapView = inflater.inflate(R.layout.item_where_am_i, parent, false);
                viewHolder = new MapViewHolder(mapView);
                break;
            default:
                View v = inflater.inflate(R.layout.item_event, parent, false);
                viewHolder = new EventViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case HEADER:
                setHeaderItem((HeaderViewHolder) holder, position);
                break;
            case MAP:
                setMapItem((MapViewHolder) holder);
                break;
            case EVENT:
                if(!isSearching) setEventItem((EventViewHolder) holder, position-3);
                else setEventItem((EventViewHolder) holder, position);
                ((EventViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id;
                        if (isSearching) id = eventList.get(position).getId();
                        else id = eventList.get(position-3).getId();
                        SharedPreferences activitySharedPref = context.getSharedPreferences("Event", Context.MODE_PRIVATE);
                        activitySharedPref.edit().putString("EventID", id).apply();

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.container, new EventDetailFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(isSearching) return eventList.size();
        return eventList.size()+3;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isSearching && position == 1) {
            return MAP;
        } else if (!isSearching && position < 3) {
            return HEADER;
        }
        return EVENT;
    }

    private void setEventItem(EventViewHolder holder, int eventPosition) {
        EventListItem movie = eventList.get(eventPosition);
        holder.title.setText(movie.getTitle());
        holder.time.setText(movie.getTime());
        String zoneShortName = movie.getTag();
        boolean isLight = false;
        for(int i=0;i<lightZone.length-1;i++){
            if(zoneShortName.equals(lightZone[i])) isLight =true;
        }
        if(isLight) {
            holder.tag.setText(zoneShortName);
            holder.tag.setTextColor(Color.BLACK);
            holder.tag.setBackgroundResource(Resource.getColor(zoneShortName));
        }
        else {
            holder.tag.setText(zoneShortName);
            holder.tag.setTextColor(Color.WHITE);
            holder.tag.setBackgroundResource(Resource.getColor(zoneShortName));
        }
        Glide.with(Contextor.getInstance().getContext())
                .load("https://api.chulaexpo.com" + movie.getThumbnail())
                .placeholder(R.drawable.thumb)
                .error(R.drawable.thumb)
                .into(holder.thumbnail);
    }

    private void setMapItem(MapViewHolder holder) {
        holder.mapView.onCreate(null);
        holder.mapView.onResume();
        holder.mapView.getMapAsync(this);
        holder.location.setText(MainApplication.getCurrentLocationDetail());
    }

    private void setHeaderItem(HeaderViewHolder holder, int position) {
        if(position == 0){
            holder.title.setText("WHERE AM I?");
            holder.description.setText("แนะนำ event จากสถานที่ปัจจุบันของคุณ");
            holder.icon.setImageResource(R.drawable.ic_pin_white);
        } else {
            holder.title.setText("NEARBY EVENTS");
            holder.description.setText("Event ใกล้ๆตัวคุณ");
            holder.icon.setImageResource(R.drawable.ic_heart_white);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        double lat = 13.74010;
        double lng = 100.53045;

        try {
            lat = MainApplication.getCurrentLocation().getLatitude();
            lng = MainApplication.getCurrentLocation().getLongitude();
        } catch (NullPointerException e) {
            Log.e("searchWhereAmI", e.toString());
        }

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
    }
}