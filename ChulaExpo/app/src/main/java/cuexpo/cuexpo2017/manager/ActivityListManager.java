package cuexpo.cuexpo2017.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ActivityListManager {

    private Context mContext;
    private ActivityItemCollectionDao dao;
    private static ActivityListManager instance;

    public ActivityListManager() {
        mContext = Contextor.getInstance().getContext();
        //Load data from persistent storage
        loadCache();
    }

    public static ActivityListManager getInstance() {
        if(instance == null) instance = new ActivityListManager();
        return instance;
    }

    public ActivityItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
        //Save to Persistent storage
        saveCache();
    }

    public Bundle onSavedInstanceState(){
        Bundle bundle = new Bundle();
        //bundle.putParcelable("cuexpo/chulaexpo/dao",dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("cuexpo/chulaexpo/dao");
    }

    private void saveCache(){
        /*
        ActivityItemCollectionDao cacheDao = new ActivityItemCollectionDao();
        if(dao != null && dao.getData() != null)
            cacheDao.setData(dao.getData().subList(0,Math.min(20,dao.getData().size())));
        String json = new Gson().toJson(cacheDao);
        SharedPreferences prefs = mContext.getSharedPreferences("photos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
        editor.putString("json",json);
        editor.apply();
        */
    }

    private void loadCache(){
        SharedPreferences prefs = mContext.getSharedPreferences("photos",Context.MODE_PRIVATE);
        String json = prefs.getString("json",null);
        if(json == null) return;
        dao = new Gson().fromJson(json,ActivityItemCollectionDao.class);
    }
}
