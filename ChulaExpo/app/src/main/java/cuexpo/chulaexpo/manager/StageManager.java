package cuexpo.chulaexpo.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public int setCircle(int[] param, int[] param2) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(df.format(c.getTime()));
        df = new SimpleDateFormat("mm");
        int minute = Integer.parseInt(df.format(c.getTime()));

        if ((param[0] == hour && hour == param2[0])&&(param[1]<=minute && minute<=param2[1])) {
            return 3;
        } else if (((param[0] == hour && hour < param2[0]) && (param[1] <= minute))){
            return 3;
        }else if((param2[0] == hour && param[0] < hour) && (minute <= param2[1])) {
            return 3;
        }else if((param[0] < hour && hour < param2[0])){
            return 3;
        } else if ((param2[0] < hour)
                || ((param2[0] == hour) && param2[1] < minute)) {
            return 2;
        } else
            return 1;
    }

}
