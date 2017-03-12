package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 3/12/2017.
 */

public class TagPageAdapter extends BaseAdapter {

    private static LayoutInflater inflater;


    public TagPageAdapter(Context context) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 0;
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
        View TagDetailView;

        switch(position) {
            case 0:
        }
        TagDetailView = convertView;
        return TagDetailView;
    }
}
