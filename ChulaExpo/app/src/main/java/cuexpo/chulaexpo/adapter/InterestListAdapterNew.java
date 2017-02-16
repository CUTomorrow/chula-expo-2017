package cuexpo.chulaexpo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.datatype.InterestItem;

/**
 * Created by Administrator on 2/15/2017.
 */

public class InterestListAdapterNew extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<InterestItem> interestItems;
    private Context context;

    public InterestListAdapterNew(Context context, ArrayList<InterestItem> interestItems) {
        this.context = context;
        this.interestItems = interestItems;
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

        if (convertView != null)
            interestView = convertView;
        else
            interestView = inflater.inflate(R.layout.item_interest_v2, null);
        holder.titleTxt = (TextView) interestView.findViewById(R.id.interest_title);
        holder.titleEngTxt = (TextView) interestView.findViewById(R.id.interest_title_eng);
        holder.interestImage = (ImageView) interestView.findViewById(R.id.interest_image);
        holder.checkImage = (ImageView) interestView.findViewById(R.id.interest_check);
        holder.iconImage = (ImageView) interestView.findViewById(R.id.interest_icon);

        Glide.with(context)
                .load(interestItem.getIconUrl())
                .placeholder(R.drawable.cir_mock)
                .into(holder.iconImage);
        Glide.with(context)
                .load(interestItem.getImageUrl())
                .placeholder(R.drawable.faculty_1)
                .into(holder.interestImage);

        if(interestItem.isInterest()) holder.checkImage.setVisibility(View.VISIBLE);
        else holder.checkImage.setVisibility(View.INVISIBLE);

        holder.titleTxt.setText(interestItem.getTitle());
        holder.titleEngTxt.setText(interestItem.getTitleEng());

        return interestView;
    }
}
