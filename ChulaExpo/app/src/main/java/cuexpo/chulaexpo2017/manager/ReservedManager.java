package cuexpo.chulaexpo2017.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ReservedManager {

    public int count;

    private static ReservedManager instance;

    public static ReservedManager getInstance() {
        if (instance == null)
            instance = new ReservedManager();
        return instance;
    }

    private Context mContext;

    private ReservedManager() {
        mContext = Contextor.getInstance().getContext();
        count = 1;
    }

    public int getReservedDate(int date){
        switch(date){
            case 0 : {return 0;}
            case 1 : {return 0;}
            case 2 : {return 0;}
            case 3 : {return 0;}
            case 4 : {return 0;}
            default : {return 0;}
        }
    }

}
