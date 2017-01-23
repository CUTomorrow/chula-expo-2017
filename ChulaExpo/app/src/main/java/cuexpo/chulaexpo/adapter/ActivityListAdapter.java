package cuexpo.chulaexpo.adapter;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.view.ActivityListItem;
import cuexpo.chulaexpo.dao.PhotoItemCollectionDao;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class ActivityListAdapter extends BaseAdapter{

    PhotoItemCollectionDao dao;
    MutableInteger lastPositionInteger;
    ViewPager vpHighlight;
    RelativeLayout layoutActivity;

    public ActivityListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return 100;
        /*
        if(cuexpo.chulaexpo.dao == null) return  0;
        if(cuexpo.chulaexpo.dao.getData() == null) return 0;
        return cuexpo.chulaexpo.dao.getData().size();
        */
    }

    @Override
    public Object getItem(int position)
    {
        return null;
        // return cuexpo.chulaexpo.dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityListItem item;
        if(convertView!=null)
            item = (ActivityListItem) convertView;
        else
            item = new ActivityListItem(parent.getContext());

        //Mock
        if(position%3==0){
            item.setNameText("Vidva Highlight");
            item.setTimeText("15 Mar 09.40 - 10.40");
            item.setFacultyText("ENG",Color.WHITE, Color.rgb(185,0,4));
            item.setImageUrl("0");
        }
        else if(position%3==1){
            item.setNameText("The Accountant นักบัญชีในตำนาน");
            item.setTimeText("15 Mar 11.40 - 12.40");
            item.setFacultyText("ACC",Color.WHITE, Color.rgb(126,166,217));
            item.setImageUrl("1");
        } else {
            item.setNameText("Psyxcho Highlight");
            item.setTimeText("15 Mar 13.00 - 15.00");
            item.setFacultyText("PSY",Color.WHITE, Color.rgb(234,220,0));
            item.setImageUrl("2");
        }

        return item;
    }
}
