package cuexpo.cuexpo2017.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DateConversionManager {

    private String Months[] = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน", "ธันวาคม"};

    private static DateConversionManager instance;

    public static DateConversionManager getInstance() {
        if (instance == null)
            instance = new DateConversionManager();
        return instance;
    }

    private Context mContext;

    private DateConversionManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public String ConvertDate(String startDate, String endDate){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(startDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = day + " " + Months[month] + " " +  year;

        return date + " : " + startDate.substring(11, 16)
                + " - " + endDate.substring(11, 16);
    }

}
