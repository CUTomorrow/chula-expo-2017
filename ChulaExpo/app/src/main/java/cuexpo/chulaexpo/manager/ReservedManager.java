package cuexpo.chulaexpo.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ReservedManager {

    public int count;
    public int offset[] = new int[10];

    private static ReservedManager instance;

    public static ReservedManager getInstance() {
        if (instance == null)
            instance = new ReservedManager();
        return instance;
    }

    private Context mContext;

    private ReservedManager() {
        mContext = Contextor.getInstance().getContext();
        offset[0] = 0;
        offset[1]= offset[0] + (getReservedDate(0)==0 ? 1 : getReservedDate(0)) + 1;
        offset[2]= offset[1] + (getReservedDate(1)==0 ? 1 : getReservedDate(1)) + 1;
        offset[3]= offset[2] + (getReservedDate(2)==0 ? 1 : getReservedDate(2)) + 1;
        offset[4]= offset[3] + (getReservedDate(3)==0 ? 1 : getReservedDate(3)) + 1;
        count = offset[4] + (getReservedDate(4)==0 ? 1 : getReservedDate(4)) + 1;
    }

    public int getReservedDate(int date){
        switch(date){
            case 0 : {return 3;}
            case 1 : {return 0;}
            case 2 : {return 1;}
            case 3 : {return 0;}
            case 4 : {return 2;}
            default : {return 0;}
        }
    }

}
