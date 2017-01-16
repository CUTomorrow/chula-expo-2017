package cuexpo.chulaexpo.adapter;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import cuexpo.chulaexpo.dao.PhotoItemCollectionDao;
import cuexpo.chulaexpo.datatype.MutableInteger;
import cuexpo.chulaexpo.view.ActivityListItem;
import cuexpo.chulaexpo.view.StageListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class StageListAdapter extends BaseAdapter{

    public StageListAdapter() {

    }

    @Override
    public int getCount() {
        return 3;
        /*
        if(cuexpo.chulaexpo.dao == null) return  0;
        if(cuexpo.chulaexpo.dao.getData() == null) return 0;
        return cuexpo.chulaexpo.dao.getData().size();
        */
    }

    @Override
    public Object getItem(int position)
    {
        return null;
        // return cuexpo.chulaexpo.dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StageListItem item;
        if(convertView!=null)
            item = (StageListItem) convertView;
        else
            item = new StageListItem(parent.getContext());

        //Mock
        if(position%3==0){
            item.setTvStageId("STAGE 1");
            item.setTvStageLocation("Main Stage");
            item.setTvStageTime("08.30 - 09.30");
            item.setTvStageTitle("Innovation Show");
            item.setIvStageStatus(1);
        }
        else if(position%3==1){
            item.setTvStageId("STAGE 2");
            item.setTvStageLocation("Grand Auditorium");
            item.setTvStageTime("09.00 - 10.30");
            item.setTvStageTitle("โครงการหุ่นยนต์ดำน้ำ");
            item.setIvStageStatus(0);
        } else {
            item.setTvStageId("STAGE 3");
            item.setTvStageLocation("ศาลาพระเกี้ยว");
            item.setTvStageTime("10.00 - 10.30");
            item.setTvStageTitle("Chula 100 years showcase");
            item.setIvStageStatus(1);
        }

        return item;
    }
}
