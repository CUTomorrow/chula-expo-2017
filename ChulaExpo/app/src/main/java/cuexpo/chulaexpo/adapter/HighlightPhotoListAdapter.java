package cuexpo.chulaexpo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.manager.PhotoListManager;
import cuexpo.chulaexpo.view.HighlightPhotoListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class HighlightPhotoListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 20;
        /*
        if(PhotoListManager.getInstance().getDao() == null) return  0;
        if(PhotoListManager.getInstance().getDao().getData() == null) return 0;
        return PhotoListManager.getInstance().getDao().getData().size();
        */
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HighlightPhotoListItem item;
        if(convertView!=null)
            item = (HighlightPhotoListItem) convertView;
        else
            item = new HighlightPhotoListItem(parent.getContext());

        //Mock
        ImageView ivHighlight;
        if(position%3==0){
            item.setNameText("Vidva Highlight");
            item.setDescriptionText("นิทรรศการวิชาการทางวิศวกรรมครั้งที่ 17 (Nitad 17)");
            item.setImageUrl("0");
        }
        else if(position%3==1){
            item.setNameText("Stat Highlight");
            item.setDescriptionText("งาน STAT CHULA EXPO 2017 ของภาควิชาสถิติ คณะพาณิชยศาสตร์และการบัญชี จุฬาฯ วันที่ 15-19 มีนาคม 2017");
            item.setImageUrl("1");
        } else {
            item.setNameText("Psyxcho Highlight");
            item.setDescriptionText("งานจุฬาฯ Expo 2017 คณะจิตวิทยา\n" +
                    "Psyche Chula Expo 2017");
            item.setImageUrl("2");
        }

        return item;
    }
}
