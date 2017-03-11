package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.datatype.InterestItem;

/**
 * Created by Administrator on 2/15/2017.
 */

public class InterestListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<InterestItem> interestItems;
    private Context context;
    private int paddingDp = 0;

    public InterestListAdapter(Context context, ArrayList<InterestItem> interestItems) {
        this.context = context;
        this.interestItems = interestItems;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public InterestListAdapter(Context context, ArrayList<InterestItem> interestItems, int paddingDp) {
        this.context = context;
        this.interestItems = interestItems;
        this.paddingDp = paddingDp;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return interestItems.size();
    }

    @Override
    public Object getItem(int position) {
        return interestItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class InterestViewHolder {
        TextView titleTxt;
        ImageView interestImage;
        ImageView checkImage;
        ImageView iconImage;
        TextView titleEngTxt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InterestViewHolder holder = new InterestViewHolder();
        View interestView;
        InterestItem interestItem = (InterestItem) this.getItem(position);
        int size = (parent.getWidth()-dpToPx(paddingDp))/3;
        GridView.LayoutParams layoutParams = new GridView.LayoutParams(size, size);

        if (convertView != null) {
            interestView = convertView;
            if (interestView.getWidth() == 0) interestView.setLayoutParams(layoutParams);
        } else {
            interestView = inflater.inflate(R.layout.item_interest_v2, null);
            interestView.setLayoutParams(layoutParams);
        }

        holder.titleTxt = (TextView) interestView.findViewById(R.id.interest_title);
        holder.titleEngTxt = (TextView) interestView.findViewById(R.id.interest_title_eng);
        holder.interestImage = (ImageView) interestView.findViewById(R.id.interest_image);
        holder.checkImage = (ImageView) interestView.findViewById(R.id.interest_check);
        holder.iconImage = (ImageView) interestView.findViewById(R.id.interest_icon);
        holder.interestImage.setImageResource(interestItem.getImageSrc());
        holder.iconImage.setImageResource(interestItem.getIconSrc());
        holder.titleTxt.setText(interestItem.getName());
        holder.titleEngTxt.setText(interestItem.getNameEng());

        if(interestItem.isInterest()) {
            holder.checkImage.setVisibility(View.VISIBLE);
            holder.titleTxt.setTextColor(context.getResources().getColor(R.color.green));
            holder.titleEngTxt.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.checkImage.setVisibility(View.INVISIBLE);
            holder.titleTxt.setTextColor(context.getResources().getColor(R.color.white));
            holder.titleEngTxt.setTextColor(context.getResources().getColor(R.color.white));
        }

        return interestView;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
