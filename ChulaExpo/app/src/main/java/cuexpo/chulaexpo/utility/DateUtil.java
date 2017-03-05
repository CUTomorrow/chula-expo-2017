package cuexpo.chulaexpo.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by APTX-4869 (LOCAL) on 3/5/2017.
 */

public class DateUtil {

    private static String MONTHS_FULL[] = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
            "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
            "กันยายน", "ตุลาคม", "พฤษจิกายน", "ธันวาคม"};

    public static String getDateThai(String strDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int month=0, day=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s %s", day, MONTHS_FULL[month]);
    }

    public static String getDateRangeThai(String strDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int month=0, sDay=0, eDay=0;
        try {
            Date sDate = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(sDate);
            month = c.get(Calendar.MONTH);
            sDay = c.get(Calendar.DATE);
            Date eDate = df.parse(endDate);
            c = Calendar.getInstance();
            c.setTime(eDate);
            eDay = c.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (sDay == eDay) return String.format("%s %s", sDay, MONTHS_FULL[month]);
        return String.format("%s-%s %s", sDay, eDay, MONTHS_FULL[month]);
    }
}
