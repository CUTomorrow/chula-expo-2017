package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by APTX-4869 (LOCAL) on 1/23/2017.
 */

public class EventDetailListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;
    private int id;

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
//            switch (position) {
//                case 0:
                    convertView = inflater.inflate(R.layout.item_event_detail_detail, null);
                    TextView title = (TextView) convertView.findViewById(R.id.detail);
                    title.setText("Detail Jaaaa");
//                    break;
//                case 1:
//                    convertView = inflater.inflate(R.layout.item2, null);
//                    holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
//                    break;
//            }
            eventDetailView = convertView;
        }
        return eventDetailView;
    }
}
