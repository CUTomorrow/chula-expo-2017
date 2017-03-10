package cuexpo.cuexpo2017.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StageManager {

    private static StageManager instance;

    public static StageManager getInstance() {
        if (instance == null)
            instance = new StageManager();
        return instance;
    }

    private Context mContext;

    private StageManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public int setCircle(int[] param, int[] param2, int stageDay) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        int hour = Integer.parseInt(sdf.format(c.getTime()));
        sdf = new SimpleDateFormat("mm", Locale.getDefault());
        int minute = Integer.parseInt(sdf.format(c.getTime()));
        sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        int year = Integer.parseInt(sdf.format(c.getTime()));
        sdf = new SimpleDateFormat("MM", Locale.getDefault());
        int month = Integer.parseInt(sdf.format(c.getTime()));
        sdf = new SimpleDateFormat("dd", Locale.getDefault());
        int day = Integer.parseInt(sdf.format(c.getTime()));

        if (year == 2017) {
            if (month == 3) {
                if (day == stageDay) {
                    if ((param[0] == hour && hour == param2[0]) && (param[1] <= minute && minute < param2[1])) {
                        return 3;
                    } else if (((param[0] == hour && hour < param2[0]) && (param[1] <= minute))) {
                        return 3;
                    } else if ((param2[0] == hour && param[0] < hour) && (minute < param2[1])) {
                        return 3;
                    } else if ((param[0] < hour && hour < param2[0])) {
                        return 3;
                    } else if ((param2[0] < hour)
                            || ((param2[0] == hour) && param2[1] <= minute)) {
                        return 2;
                    } else
                        return 1;
                } else if (day > stageDay) {
                    return 2;
                } else {
                    return 1;
                }
            } else if(month > 3){
                return 2;
            } else {
                return 1;
            }
        } else if(year > 2017){
            return 2;
        } else{
            return 1;
        }
    }

    public int setLine(int groupPosition, int groupCount) {
        if (groupPosition == 0) {
            if (groupCount > 1)
                return 2;
            else
                return 4;
        } else if (groupPosition == groupCount - 1) {
            return 3;
        } else {
            return 1;
        }
    }

    public int setDrop(boolean isExpanded) {
        if (isExpanded) {
            return 2;
        } else {
            return 1;
        }
    }

}
