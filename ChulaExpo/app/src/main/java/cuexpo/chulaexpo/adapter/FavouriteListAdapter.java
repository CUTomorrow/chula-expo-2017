package cuexpo.chulaexpo.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cuexpo.chulaexpo.fragment.FavouriteFragment;
import cuexpo.chulaexpo.manager.FavouriteManager;
import cuexpo.chulaexpo.view.DateSection;
import cuexpo.chulaexpo.view.FavouriteListItem;

/**
 * Created by TEST on 1/11/2017.
 */
public class FavouriteListAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return FavouriteManager.getInstance().count;
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
        FavouriteListItem item;
        DateSection date = new DateSection(parent.getContext());
        FavouriteManager manager = FavouriteManager.getInstance();

        for(int i = 15;i < 20;i++) {
            if (position == manager.offset[i-15]) {
                date.setDate(i + "");
                return date;
            } else if (position == manager.offset[i-15] + 1) {
                if (manager.getFavouriteDate(i-15) == 0) {
                    TextView tv = new TextView(parent.getContext());
                    tv.setText("No Favourite Event On This Day");
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    final float scale = parent.getResources().getDisplayMetrics().density;
                    int pixels = (int) (15 * scale + 0.5f);
                    tv.setPadding(0,pixels,0,pixels);
                    return tv;
                } else {
                    item = new FavouriteListItem(parent.getContext());
                    return item;
                }
            }
        }

        item = new FavouriteListItem(parent.getContext());
        return item;
    }

    @Override
    public boolean isEnabled(int position) {
        FavouriteManager manager = FavouriteManager.getInstance();
        for(int i = 0 ;i < 5 ;i++){
            if(position==manager.offset[i]+1 && manager.getFavouriteDate(i) == 0){
                return false;
            }
        }
        return true;
    }
}
