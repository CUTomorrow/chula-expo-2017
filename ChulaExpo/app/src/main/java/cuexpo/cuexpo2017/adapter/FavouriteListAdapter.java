package cuexpo.cuexpo2017.adapter;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cuexpo.cuexpo2017.manager.FavouriteManager;
import cuexpo.cuexpo2017.view.FavouriteListItem;

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
        FavouriteManager manager = FavouriteManager.getInstance();

        if (manager.getFavouriteDate(0) == 0) {
            TextView tv = new TextView(parent.getContext());
            tv.setText("ไม่มี Event ที่กำลังจะเกิดขึ้น");
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            final float scale = parent.getResources().getDisplayMetrics().density;
            int pixels = (int) (15 * scale + 0.5f);
            tv.setPadding(0, pixels, 0, pixels);
            return tv;
        }

        item = new FavouriteListItem(parent.getContext());
        return item;
    }

    @Override
    public boolean isEnabled(int position) {
        FavouriteManager manager = FavouriteManager.getInstance();
        if (manager.getFavouriteDate(0) == 0) {
            return false;
        }
        return true;
    }
}
