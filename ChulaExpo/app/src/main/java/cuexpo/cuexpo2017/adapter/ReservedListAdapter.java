package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
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
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.utility.Resource;
import cuexpo.cuexpo2017.view.ActivityListItem;

/**
 * Created by TEST on 1/11/2017.
 */
public class ReservedListAdapter extends BaseAdapter {


    private RoundDao roundDao;
    private ActivityItemCollectionDao activityDao;
    private SharedPreferences sharedPref;
    private String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    private Boolean isZero = true;
    private String holder = "";

    public void setHolder(String text){
        holder = text;
    }

    public void setIsZero(boolean value) {
        isZero = value;
    }

    public void setRoundDao(RoundDao dao) {
        this.roundDao = dao;
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
                item = (ActivityListItem)convertView;
            } else{
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
            item.setTimeText(dateThai(roundDao.getResults().get(position).getStart())
                    + " \u2022 " + roundDao.getResults().get(position).getStart().substring(11, 16) + "-"
                    + roundDao.getResults().get(position).getEnd().substring(11, 16));
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

    public static String dateThai(String strDate) {
        String Months[] = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤษจิกายน", "ธันวาคม"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return String.format("%s %s", day, Months[month]);
    }

}
