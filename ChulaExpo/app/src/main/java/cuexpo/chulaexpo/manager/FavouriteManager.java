package cuexpo.chulaexpo.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FavouriteManager {

    public int count;
    public int offset[] = new int[10];

    private static FavouriteManager instance;

    public static FavouriteManager getInstance() {
        if (instance == null)
            instance = new FavouriteManager();
        return instance;
    }

    private Context mContext;

    private FavouriteManager() {
        mContext = Contextor.getInstance().getContext();
        offset[0] = 0;
        offset[1]= offset[0] + (getFavouriteDate(0)==0 ? 1 : getFavouriteDate(0)) + 1;
        offset[2]= offset[1] + (getFavouriteDate(1)==0 ? 1 : getFavouriteDate(1)) + 1;
        offset[3]= offset[2] + (getFavouriteDate(2)==0 ? 1 : getFavouriteDate(2)) + 1;
        offset[4]= offset[3] + (getFavouriteDate(3)==0 ? 1 : getFavouriteDate(3)) + 1;
        count = offset[4] + (getFavouriteDate(4)==0 ? 1 : getFavouriteDate(4)) + 1;
    }

    public int getFavouriteDate(int date){
        switch(date){
            case 0 : {return 5;}
            case 1 : {return 0;}
            case 2 : {return 0;}
            case 3 : {return 0;}
            case 4 : {return 1;}
            default : {return 0;}
        }
    }

}
