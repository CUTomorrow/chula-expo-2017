package cuexpo.chulaexpo.adapter;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.fragment.EventDetailFragment;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by APTX-4869 (LOCAL) on 1/23/2017.
 */

public class EventDetailListAdapter extends BaseAdapter implements OnMapReadyCallback {
    private static LayoutInflater inflater = null;
    private Context context;
    private int id;

    // mock up
    String[] imageUrls = {
            "http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/51QOphdZd3L.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/ed/23/33/ed23333677310a4e4d95bae6a780be33.jpg",
            "https://i.ytimg.com/vi/Hu7NNfqe46c/hqdefault.jpg",
            "http://www.irobotweb.com/~/media/MainSite/Images/Home/Support/Product%20Resources/Roomba%20700/Overview/Roomba780_QuickStart.jpg?h=638&la=en&w=1160",
            "http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg",
            "http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/51QOphdZd3L.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/ed/23/33/ed23333677310a4e4d95bae6a780be33.jpg",
            "https://i.ytimg.com/vi/Hu7NNfqe46c/hqdefault.jpg",
            "http://www.irobotweb.com/~/media/MainSite/Images/Home/Support/Product%20Resources/Roomba%20700/Overview/Roomba780_QuickStart.jpg?h=638&la=en&w=1160",
            "http://www.womie.ru/wp-content/uploads/2014/04/%D0%96%D0%B5%D0%BD%D1%89%D0%B8%D0%BD%D1%8B-%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B5%D1%82%D0%B0%D1%82%D0%B5%D0%BB%D0%B8-1.jpg"
    };

    public EventDetailListAdapter(Context context, int id){
        this.id = id;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


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

        if (convertView != null)
            eventDetailView = convertView;
        else {
            switch (position) {
                case 0:
                    convertView = inflater.inflate(R.layout.item_event_detail_schedule, null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.item_event_detail_detail, null);
                    TextView title = (TextView) convertView.findViewById(R.id.detail);
                    title.setText("ปกติทุกคนมีขั้นตอนการทำความสะอาดบ้าน ห้องคอนโดฯ หรือ " +
                            "ที่อยู่อาศัยกันยังไงบ้าง? เชื่อว่าหลายคนก็ต้องตอบเหมือนๆ กันว่า " +
                            "ต้องเริ่มจากการกวาดบ้าน เพื่อกำจัดฝุ่นผงที่อยู่บนพื้นออกให้ " +
                            "หมดก่อนใช่ไหมล่ะ ซึ่งแน่นอนว่าการกวาดบ้านมันก็ไม่ใช่เรื่องเล็กๆ " +
                            "เลย แถมถ้าเราไม่มีอุปกรณ์ดีๆ บางทีก็ไม่สามารถเก็บฝุ่นผงจากที่ " +
                            "ซอกหลืบต่างๆ ได้อีกด้วย นี่แหละถึงเป็นเหตุผลว่า ทำไมเราถึงต้อง " +
                            "เลือกใช้ “หุ่นยนต์ดูดฝุ่นอัตโนมัติ” แทนการพึ่งพาไม้กวาดแบบเดิม" +
                            "ปกติทุกคนมีขั้นตอนการทำความสะอาดบ้าน ห้องคอนโดฯ หรือ " +
                            "ที่อยู่อาศัยกันยังไงบ้าง? เชื่อว่าหลายคนก็ต้องตอบเหมือนๆ กันว่า " +
                            "ต้องเริ่มจากการกวาดบ้าน เพื่อกำจัดฝุ่นผงที่อยู่บนพื้นออกให้ " +
                            "หมดก่อนใช่ไหมล่ะ ซึ่งแน่นอนว่าการกวาดบ้านมันก็ไม่ใช่เรื่องเล็กๆ " +
                            "เลย แถมถ้าเราไม่มีอุปกรณ์ดีๆ บางทีก็ไม่สามารถเก็บฝุ่นผงจากที่ " +
                            "ซอกหลืบต่างๆ ได้อีกด้วย นี่แหละถึงเป็นเหตุผลว่า ทำไมเราถึงต้อง " +
                            "เลือกใช้ “หุ่นยนต์ดูดฝุ่นอัตโนมัติ” แทนการพึ่งพาไม้กวาดแบบเดิม");
                    LinearLayout pictureLayout = (LinearLayout) convertView.findViewById(R.id.picture_layout);
                    for(String imageUrl: imageUrls){
                        ImageView image = new ImageView(context);
                        RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(
                                dpToPx(44), dpToPx(44));
                        image.setLayoutParams(imageParam);
                        Glide.with(context)
                                .load(imageUrl)
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
        }
        return eventDetailView;
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

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
