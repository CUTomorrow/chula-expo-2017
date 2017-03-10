package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.datatype.InterestItem;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

//public class InterestListAdapter extends BaseAdapter{
//    private static LayoutInflater inflater = null;
//    private ArrayList<InterestItem> interestItems;
//    private Context context;
//
//    public InterestListAdapter(Context context, ArrayList<InterestItem> interestItems){
//        this.interestItems = interestItems;
//        this.context = context;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//
//    @Override
//    public int getCount() {
//        return interestItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return interestItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    private class InterestViewHolder {
//        TextView titleTxt;
//        ImageView interestImage;
//        ImageView checkImage;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        InterestViewHolder interestViewHolder = new InterestViewHolder();
//        View interestView;
//        InterestItem interestItem = interestItems.get(position);
//
//        if(convertView!=null)
//            interestView = convertView;
//        else
//            interestView = inflater.inflate(R.layout.item_interest, null);
//        interestViewHolder.interestImage = (ImageView) interestView.findViewById(R.id.interest_image);
//        interestViewHolder.titleTxt = (TextView) interestView.findViewById(R.id.interest_title);
//        interestViewHolder.checkImage = (ImageView) interestView.findViewById(R.id.check_image);
//
//        Glide.with(context)
//                .load(interestItem.getImageUrl())
//                .placeholder(R.drawable.cir_mock)
//                .bitmapTransform(new CropCircleTransformation(context))
//                .into(interestViewHolder.interestImage);
//        if(interestItem.isInterest()) interestViewHolder.checkImage.setVisibility(View.VISIBLE);
//        else interestViewHolder.checkImage.setVisibility(View.INVISIBLE);
//        interestViewHolder.titleTxt.setText(interestItem.getTitle());
//        return interestView;
//    }
//}
