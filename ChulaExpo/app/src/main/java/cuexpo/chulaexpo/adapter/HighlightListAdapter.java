package cuexpo.chulaexpo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cuexpo.chulaexpo.view.HighlightListItem;

/**
 * Created by dragonnight on 6/1/2560.
 */

public class HighlightListAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HighlightListItem item;
        item = new HighlightListItem(container.getContext());

        //mock highlight
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

        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
