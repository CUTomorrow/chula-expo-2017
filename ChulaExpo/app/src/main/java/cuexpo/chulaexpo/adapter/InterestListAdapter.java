package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.activity.InterestActivity;
import cuexpo.chulaexpo.datatype.InterestItem;
import cuexpo.chulaexpo.view.ActivityListItem;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class InterestListAdapter extends BaseAdapter{
    private static LayoutInflater inflater = null;
    private ArrayList<InterestItem> interestItems;
    private Context context;

    public InterestListAdapter(Context context, ArrayList<InterestItem> interestItems){
        this.interestItems = interestItems;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private class InterestItemHolder {
        TextView titleTxt;
        ImageView interestImage;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InterestItemHolder interestItemHolder = new InterestItemHolder();
        View interestItem;
        if(convertView!=null)
            interestItem = convertView;
        else
            interestItem = inflater.inflate(R.layout.item_interest, null);
        interestItemHolder.interestImage = (ImageView) interestItem.findViewById(R.id.interest_image);
        interestItemHolder.titleTxt = (TextView) interestItem.findViewById(R.id.interest_title);

        Log.e("get view", "get view jaa");
        Glide.with(context)
                .load(interestItems.get(position).getImageUrl())
                .placeholder(R.drawable.iv_profile)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(interestItemHolder.interestImage);
        interestItemHolder.titleTxt.setText(interestItems.get(position).getTitle());
        return interestItem;
    }
}
