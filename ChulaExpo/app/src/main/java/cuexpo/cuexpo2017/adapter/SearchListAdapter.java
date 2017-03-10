package cuexpo.cuexpo2017.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;
import cuexpo.cuexpo2017.view.EventListItem;

/**
 * Created by APTX-4869 (LOCAL) on 1/25/2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnMapReadyCallback {

    private List<EventListItem> eventList;
    private boolean isSearching;
    private final short EVENT = 0;
    private final short HEADER = 1;
    private final short MAP = 2;
    private FragmentManager fragmentManager;

    public class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView title, time, tag;
        public EventViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            tag = (TextView) view.findViewById(R.id.event_tag);
            view.setOnClickListener(onEventClick);
        }
    }

    private View.OnClickListener onEventClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new EventDetailFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

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

    public SearchListAdapter(List<EventListItem> eventList, boolean isSearching, FragmentManager fragmentManager) {
        this.eventList = eventList;
        this.isSearching = isSearching;
        this.fragmentManager = fragmentManager;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case HEADER:
                setHeaderItem((HeaderViewHolder) holder, position);
                break;
            case MAP:
                setMapItem((MapViewHolder) holder);
                break;
            case EVENT:
                setEventItem((EventViewHolder) holder, position-3);
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
    }

    private void setMapItem(MapViewHolder holder) {
        holder.mapView.onCreate(null);
        holder.mapView.onResume();
        holder.mapView.getMapAsync(this);
        holder.location.setText("ห้อง i-Scale 404 ชั้น 4 ตึก 100 ปี คณะวิศวกรรมศาสตร์");
    }

    private void setHeaderItem(HeaderViewHolder holder, int position) {
        if(position == 0){
            holder.title.setText("WHERE AM I?");
            holder.description.setText("แนะนำ event จากสถานที่ปัจจุบันของคุณ");
            holder.icon.setImageResource(R.drawable.ic_pin_white);
        } else {
            holder.title.setText("POPULAR EVENTS");
            holder.description.setText("Event ที่กำลังได้รับความนิยมในขณะนี้");
            holder.icon.setImageResource(R.drawable.ic_heart_white);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
//        googleMap.setOnMarkerClickListener(this);
//        googleMap.setOnMapClickListener(this);
        double lat = 13.74010;
        double lng = 100.53045;
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(18)
                        .build()
        ));
//        googleMap.addMarker(
//                new MarkerOptions()
//                        .position(new LatLng(lat, lng))
//                        .icon(BitmapDescriptorFactory.fromResource(drawable("pin_" + faculty)))
//        );
    }
}
