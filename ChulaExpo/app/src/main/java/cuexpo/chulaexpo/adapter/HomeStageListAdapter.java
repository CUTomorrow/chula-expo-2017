package cuexpo.chulaexpo.adapter;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.view.HomeStageListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class HomeStageListAdapter extends BaseAdapter{

    ActivityItemCollectionDao dao;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public HomeStageListAdapter() {

    }

    public ActivityItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
    }

    public void addDao(ActivityItemResultDao dao){
        this.dao.addResults(dao);
    }


    @Override
    public int getCount() {
        if(dao == null) return  0;
        if(dao.getResults() == null) return 0;
        return dao.getResults().size();
    }

    @Override
    public ActivityItemResultDao getItem(int position)
    {
        return dao.getResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeStageListItem item;
        if(convertView!=null)
            item = (HomeStageListItem) convertView;
        else
            item = new HomeStageListItem(parent.getContext());

        //prepare data
        ActivityItemResultDao dao = (ActivityItemResultDao) getItem(position);
        sharedPref = parent.getContext().getSharedPreferences("ZoneKey",parent.getContext().MODE_PRIVATE);
        String zoneShortName = sharedPref.getString(dao.getZone(),"");
        item.setTvStageId("STAGE " + (position+1));
        item.setTvStageLocation(zoneShortName);
        item.setTvStageTime(dateThai(dao.getStart())+" \u2022 "+dao.getStart().substring(11,16) + "-"+dao.getEnd().substring(11,16));
        item.setTvStageTitle(dao.getName().getTh());
        /*Mock
        if(position%3==0){
            item.setTvStageId("STAGE 1");
            item.setTvStageLocation("เวทีหลัก");
            item.setTvStageTime("08.30 - 09.30");
            item.setTvStageTitle("Innovation Show");
        }
        else if(position%3==1){
            item.setTvStageId("STAGE 2");
            item.setTvStageLocation("หอประชุม จุฬาฯ");
            item.setTvStageTime("09.00 - 10.30");
            item.setTvStageTitle("โครงการหุ่นยนต์ดำน้ำ");
        } else {
            item.setTvStageId("STAGE 3");
            item.setTvStageLocation("ศาลาพระเกี้ยว");
            item.setTvStageTime("10.00 - 10.30");
            item.setTvStageTitle("Chula 100 years showcase");
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
