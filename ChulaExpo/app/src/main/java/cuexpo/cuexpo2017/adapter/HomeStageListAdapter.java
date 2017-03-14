package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.view.HomeStageListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class HomeStageListAdapter extends BaseAdapter {

    private ActivityItemCollectionDao dao;
    private SharedPreferences sharedPref;
    private SharedPreferences sharedPref2;

    public HomeStageListAdapter() {
    }

    public ActivityItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
    }

    public void addDao(ActivityItemResultDao dao) {
        this.dao.addResults(dao);
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
        HomeStageListItem item;
        if (convertView != null)
            item = (HomeStageListItem) convertView;
        else
            item = new HomeStageListItem(parent.getContext());

        //prepare data
        ActivityItemResultDao dao = getItem(position);
        sharedPref = parent.getContext().getSharedPreferences("ZoneKey", Context.MODE_PRIVATE);
        sharedPref2 = parent.getContext().getSharedPreferences("ZoneKeyEn", Context.MODE_PRIVATE);
        String zoneShortName = sharedPref.getString(dao.getZone(), "");
        String zoneShortNameEn = sharedPref2.getString(dao.getZone(), "");

        if(zoneShortName.equals("SALA ST")) {
            zoneShortName = "ศาลาพระเกี้ยว";
            zoneShortNameEn = "CU@100";
        }

        item.setTvStageId(zoneShortName);
        item.setTvStageLocation(zoneShortNameEn);
        item.setTvStageTime(dateThai(dao.getStart()) + " \u2022 " + dao.getStart().substring(11, 16) + "-" + dao.getEnd().substring(11, 16));
        item.setTvStageTitle(dao.getName().getTh());
        return item;
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
