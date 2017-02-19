package cuexpo.chulaexpo.adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.dao.ZoneResult;
import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.manager.ActivityListManager;
import cuexpo.chulaexpo.manager.HttpManager;
import cuexpo.chulaexpo.utility.Resource;
import cuexpo.chulaexpo.view.ActivityListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class ActivityListAdapter extends BaseAdapter{

    ActivityItemCollectionDao dao;
    ZoneResult zoneDao;
    MutableInteger lastPositionInteger;
    ViewPager vpHighlight;
    RelativeLayout layoutActivity;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String[] lightZone = {"SCI", "ECON", "LAW", "VET"};

    public ActivityListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
    }

    public void setZoneDao(ZoneResult zoneDao) {
        this.zoneDao = zoneDao;
    }

    @Override
    public int getCount() {
        if(ActivityListManager.getInstance().getDao() == null) return  0;
        if(ActivityListManager.getInstance().getDao().getResults() == null) return 0;
        return ActivityListManager.getInstance().getDao().getResults().size();
    }

    @Override
    public Object getItem(int position)
    {
        return ActivityListManager.getInstance().getDao().getResults().get(position);
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
        //prepare data
        ActivityItemResultDao dao = (ActivityItemResultDao) getItem(position);
        sharedPref = parent.getContext().getSharedPreferences("ZoneKey",parent.getContext().MODE_PRIVATE);
        String zoneShortName = sharedPref.getString(dao.getZone(),"");

        item.setNameText(dao.getName().getTh());
        item.setTimeText(dateThai(dao.getStart())+"\u2022"+dao.getStart().substring(11,16) + "-"+dao.getEnd().substring(11,16));
        boolean isLight = false;
        for(int i=0;i<lightZone.length-1;i++){
            if(zoneShortName.equals(lightZone[i])) isLight =true;
        }
        if(isLight) item.setFacultyText(zoneShortName,Color.BLACK, Resource.getColor(zoneShortName));
        else item.setFacultyText(zoneShortName,Color.WHITE, Resource.getColor(zoneShortName));
        item.setImageUrl("http://staff.chulaexpo.com" + dao.getThumbnail());

        /*Mock
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
        */
        return item;
    }

    public static String dateThai(String strDate)
    {
        String Months[] = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤษจิกายน", "ธันวาคม"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
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

        return String.format("%s %s", day,Months[month]);
    }
}
