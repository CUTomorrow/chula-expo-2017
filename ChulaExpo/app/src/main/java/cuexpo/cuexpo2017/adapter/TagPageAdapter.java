package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.utility.Resource;

/**
 * Created by Administrator on 3/12/2017.
 */

public class TagPageAdapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private List<ActivityItemResultDao> eventList = new ArrayList<>();
    private TextView header, detail;
    private String title, detailString;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    private Context context;
    private String holder;
    private boolean isZero;

    public TagPageAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences sharedPreferences = context.getSharedPreferences("TagDetail", Context.MODE_PRIVATE);
        title = sharedPreferences.getString("title", "");
        detailString = sharedPreferences.getString("detail", "");
        isZero = true;
        holder = "Loading Data...";
    }

    public boolean getIsZero() {
        return isZero;
    }

    public void setIsZero(boolean isZero) {
        this.isZero = isZero;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setEvent(List<ActivityItemResultDao> eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        if(eventList.size()==0) return 4;
        return 3 + eventList.size();
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
        View TagDetailView;

        switch (position) {
            case 0:
                convertView = inflater.inflate(R.layout.item_tag_detail_header, null);
                header = (TextView) convertView.findViewById(R.id.tag_title);
                header.setText(title);
                break;
            case 1:
                convertView = inflater.inflate(R.layout.item_event_detail_detail, null);
                detail = (TextView) convertView.findViewById(R.id.detail);
                detail.setText(detailString);
                break;
            case 2:
                convertView = inflater.inflate(R.layout.item_list_header, null);
                ((TextView) convertView.findViewById(R.id.title)).setText("RELATED EVENTS");
                ((TextView) convertView.findViewById(R.id.description)).setText("Event ที่เกี่ยวข้องทั้งหมด");
                ((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.ic_event);
                break;
            default:
                if(isZero){
                    convertView = inflater.inflate(R.layout.item_empty, null);
                    ((TextView) convertView.findViewById(R.id.item_empty_text)).setText(holder);
                    return convertView;
                }else {
                    convertView = inflater.inflate(R.layout.item_event, null);
                    ActivityItemResultDao event = eventList.get(position - 3);
                    ((TextView) convertView.findViewById(R.id.title)).setText(event.getName().getTh());
                    String time = DateUtil.getDateRangeThai(event.getStart(), event.getEnd())
                            + " \u2022 " + event.getStart().substring(11, 16)
                            + "-" + event.getEnd().substring(11, 16);
                    ((TextView) convertView.findViewById(R.id.time)).setText(time);

                    SharedPreferences sharedPref = context.getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
                    String zoneShortName = sharedPref.getString(event.getZone(), "");
                    TextView eventTag = (TextView) convertView.findViewById(R.id.event_tag);
                    eventTag.setText(zoneShortName);
                    eventTag.setBackgroundResource(Resource.getColor(zoneShortName));
                    for (int i = 0; i < lightZone.length - 1; i++) {
                        if (zoneShortName.equals(lightZone[i])) {
                            eventTag.setTextColor(Color.BLACK);
                            break;
                        }
                    }
                    Glide.with(context)
                            .load("https://api.chulaexpo.com" + event.getThumbnail())
                            .placeholder(R.drawable.banner)
                            .centerCrop()
                            .into((ImageView) convertView.findViewById(R.id.event_image));
                }
                break;
        }
        TagDetailView = convertView;
        return TagDetailView;
    }
}
