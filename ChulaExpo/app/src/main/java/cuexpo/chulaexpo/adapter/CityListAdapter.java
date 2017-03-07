package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.view.CityListItem;

/**
 * Created by Administrator on 1/30/2017.
 */

public class CityListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return ""+R.string.ID_eng_faculty;
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
        switch(position) {
            case 0:
                city.setCityTitle("Smart City");
                city.setCityTag("SMART", Color.WHITE, Color.rgb(244,138,74));
                city.setCityIcon(R.drawable.smart_icon);
                city.setImageUrl(R.drawable.smart_bg);
                break;
            case 1:
                city.setCityTitle("Health City");
                city.setCityTag("HEALTH", Color.WHITE, Color.rgb(75,169,131));
                city.setCityIcon(R.drawable.health_icon);
                city.setImageUrl(R.drawable.health_bg);
                break;
            case 2:
                city.setCityTitle("Human City");
                city.setCityTag("HUMAN", Color.WHITE, Color.rgb(74,51,139));
                city.setCityIcon(R.drawable.human_icon);
                city.setImageUrl(R.drawable.human_bg);
                break;
            case 3:
                city.setCityTitle("CU Talk");
                city.setCityTag("CUTALK", Color.WHITE, Color.rgb(255,78,158));
                city.setCityIcon(R.drawable.cutalk_icon);
                city.setImageUrl(R.drawable.cutalk_bg);
                break;
            case 4:city.setCityTitle("CU@100 Exhibition");
                city.setCityTag("CU100", Color.WHITE, Color.rgb(255,78,158));
                city.setCityIcon(R.drawable.cu100_icon);
                city.setImageUrl(R.drawable.cu100_bg);
                break;
            case 5:city.setCityTitle("Art Gallery");
                city.setCityTag("ARTGAL", Color.WHITE, Color.rgb(255,78,158));
                city.setCityIcon(R.drawable.artgal_icon);
                city.setImageUrl(R.drawable.artgal_bg);
                break;
            case 6:city.setCityTitle("International Forum");
                city.setCityTag("FORUM", Color.WHITE, Color.rgb(255,78,158));
                city.setCityIcon(R.drawable.forum_icon);
                city.setImageUrl(R.drawable.forum_bg);
                break;
        }
        return city;
    }
}
