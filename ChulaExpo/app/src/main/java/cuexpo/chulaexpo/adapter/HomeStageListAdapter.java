package cuexpo.chulaexpo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cuexpo.chulaexpo.view.HomeStageListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class HomeStageListAdapter extends BaseAdapter{

    public HomeStageListAdapter() {

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
        HomeStageListItem item;
        if(convertView!=null)
            item = (HomeStageListItem) convertView;
        else
            item = new HomeStageListItem(parent.getContext());

        //Mock
        if(position%3==0){
            item.setTvStageId("STAGE 1");
            item.setTvStageLocation("เวทีหลัก");
            item.setTvStageTime("08.30 - 09.30");
            item.setTvStageTitle("Innovation Show");
        }
        else if(position%3==1){
            item.setTvStageId("STAGE 2");
            item.setTvStageLocation("หอประชุม จุฬาฯ");
            item.setTvStageTime("09.00 - 10.30");
            item.setTvStageTitle("โครงการหุ่นยนต์ดำน้ำ");
        } else {
            item.setTvStageId("STAGE 3");
            item.setTvStageLocation("ศาลาพระเกี้ยว");
            item.setTvStageTime("10.00 - 10.30");
            item.setTvStageTitle("Chula 100 years showcase");
        }

        return item;
    }
}
