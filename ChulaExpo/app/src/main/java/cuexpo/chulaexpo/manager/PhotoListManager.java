package cuexpo.chulaexpo.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import dao.PhotoItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;
    private PhotoItemCollectionDao dao;

    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }
}
