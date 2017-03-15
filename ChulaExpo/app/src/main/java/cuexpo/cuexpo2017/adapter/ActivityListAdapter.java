package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.datatype.MutableInteger;
import cuexpo.cuexpo2017.utility.DateUtil;
import cuexpo.cuexpo2017.utility.Resource;
import cuexpo.cuexpo2017.view.ActivityListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class ActivityListAdapter extends BaseAdapter {

    private ActivityItemCollectionDao dao;
    private MutableInteger lastPositionInteger;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};

    public ActivityListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) return 0;
        if (dao.getResults() == null) return 0;
        return dao.getResults().size();
    }

    @Override
    public ActivityItemResultDao getItem(int position) {
        return dao.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityListItem item;
        if (convertView != null)
            item = (ActivityListItem) convertView;
        else
            item = new ActivityListItem(parent.getContext());

        //Prepare Data
        ActivityItemResultDao dao = getItem(position);
        sharedPref = parent.getContext().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
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
        item.setImageUrl("https://api.chulaexpo.com" + dao.getThumbnail());

        return item;
    }
}
