package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.utility.Resource;
import cuexpo.cuexpo2017.view.ActivityListItem;

/**
 * Created by TEST on 1/11/2017.
 */
public class FavouriteListAdapter extends BaseAdapter {

    private ActivityItemCollectionDao activityDao;
    private SharedPreferences sharedPref;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    private Boolean isZero = true;
    private String holder = "";

    public boolean getIsZero() {
        return isZero;
    }

    public void setHolder(String text) {
        holder = text;
    }

    public void setIsZero(boolean value) {
        isZero = value;
    }

    public void setActivityDao(ActivityItemCollectionDao dao) {
        this.activityDao = dao;
    }

    @Override
    public int getCount() {
        if (activityDao == null) return 1;
        return activityDao.getResults().size();
    }

    @Override
    public ActivityItemResultDao getItem(int position) {
        return activityDao.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityListItem item;
        if (convertView != null) {
            if (!isZero && convertView instanceof ActivityListItem) {
                item = (ActivityListItem) convertView;
            } else {
                item = new ActivityListItem(parent.getContext());
            }
        } else
            item = new ActivityListItem(parent.getContext());

        if (isZero) {
            convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                    inflate(R.layout.item_empty, null);
            ((TextView) convertView.findViewById(R.id.item_empty_text)).setText(holder);
            return convertView;
        } else {

            ActivityItemResultDao dao = activityDao.getResults().get(position);
            sharedPref = parent.getContext().getSharedPreferences("ZoneKey", parent.getContext().MODE_PRIVATE);
            String zoneShortName = sharedPref.getString(dao.getZone(), "");

            item.setNameText(dao.getName().getTh());
            item.setTimeText(DateUtil.getDateRangeThai(dao.getStart(), dao.getEnd())
                    + " \u2022 " + dao.getStart().substring(11, 16)
                    + "-" + dao.getEnd().substring(11, 16));
            //Handle with Faculty with Light Background Color
            boolean isLight = false;
            for (int i = 0; i < lightZone.length - 1; i++) {
                if (zoneShortName.equals(lightZone[i])) isLight = true;
            }
            if (isLight)
                item.setFacultyText(zoneShortName, Color.BLACK, Resource.getColor(zoneShortName));
            else item.setFacultyText(zoneShortName, Color.WHITE, Resource.getColor(zoneShortName));
            item.setImageUrl("http://staff.chulaexpo.com" + dao.getThumbnail());

            return item;
        }
    }

}
