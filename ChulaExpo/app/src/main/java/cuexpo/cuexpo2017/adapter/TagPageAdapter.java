package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;

/**
 * Created by Administrator on 3/12/2017.
 */

public class TagPageAdapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private List<ActivityItemResultDao> eventList = new ArrayList<>();
    private TextView header, detail;
    private String title, detailString;

    public TagPageAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences sharedPreferences = context.getSharedPreferences("TagDetail", Context.MODE_PRIVATE);
        title = sharedPreferences.getString("title", "");
        detailString = sharedPreferences.getString("detail", "");
    }

    public void setEvent(List<ActivityItemResultDao> eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return 3+eventList.size();
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

        switch(position) {
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
            default :
                break;
        }
        TagDetailView = convertView;
        return TagDetailView;
    }
}
