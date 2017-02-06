package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cuexpo.chulaexpo.view.CityListItem;

/**
 * Created by Administrator on 1/30/2017.
 */

public class CityListAdapter extends BaseAdapter {

    public CityListAdapter() {

    }

    @Override
    public int getCount() {
        return 17;
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
        CityListItem city;
        if (convertView != null)
            city = (CityListItem) convertView;
        else {
            city = new CityListItem(parent.getContext());
        }

        //mock
        if(position%3==0){
            city.setCityTitle("Smart City");
            city.setNumberOfEvents("143 events");
            city.setCityTag("SMART", Color.WHITE, Color.rgb(185,0,4));
            city.setCityIcon("0");
            city.setImageUrl("0");
        }
        else if(position%3==1){
            city.setCityTitle("Health City");
            city.setNumberOfEvents("99 events");
            city.setCityTag("HEALTH", Color.WHITE, Color.rgb(185,0,4));
            city.setCityIcon("0");
            city.setImageUrl("1");
        } else {
            city.setCityTitle("Human City");
            city.setNumberOfEvents("212 events");
            city.setCityTag("HUMAN", Color.WHITE, Color.rgb(185,0,4));
            city.setCityIcon("0");
            city.setImageUrl("0");
        }
        return city;
    }
}
