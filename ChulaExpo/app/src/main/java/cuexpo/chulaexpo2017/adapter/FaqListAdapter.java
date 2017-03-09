package cuexpo.chulaexpo2017.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cuexpo.chulaexpo2017.view.FaqListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class FaqListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 10;
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
        FaqListItem item;

        if(convertView!=null)
            item = (FaqListItem) convertView;
        else
            item = new FaqListItem(parent.getContext());

        //Mock
        if(position%3==0){
            item.setQuestion("Vidva Highlight");
            item.setAnswer("15 Mar 09.40 - 10.40");
        }
        else if(position%3==1){
            item.setQuestion("The Accountant นักบัญชีในตำนาน");
            item.setAnswer("15 Mar 11.40 - 12.40");
        } else {
            item.setQuestion("Psyxcho Highlight");
            item.setAnswer("15 Mar 13.00 - 15.00");
        }
        return item;
    }
}
