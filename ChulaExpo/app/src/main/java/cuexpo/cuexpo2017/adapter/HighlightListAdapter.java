package cuexpo.cuexpo2017.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemResultDao;
import cuexpo.cuexpo2017.fragment.EventDetailFragment;
import cuexpo.cuexpo2017.view.HighlightListItem;

/**
 * Created by dragonnight on 6/1/2560.
 */

public class HighlightListAdapter extends PagerAdapter {

    ActivityItemCollectionDao dao;
    Fragment fragment;
    public HighlightListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }
    public void setDao(ActivityItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null) return  0;
        if(dao.getResults() == null) return 0;
        return dao.getResults().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container,final int position) {
        HighlightListItem item;
        item = new HighlightListItem(container.getContext());
        final ActivityItemResultDao result = dao.getResults().get(position);
        item.setNameText(result.getName().getTh());
        item.setDescriptionText(result.getShortDescription().getTh());
        item.setImageUrl("https://staff.chulaexpo.com" + result.getBanner());

        //LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView ivHighlight = item.getIvHighlight();
        //ImageView ivHighlight = (ImageView) page.findViewById(R.id.ivHighlight);
        ivHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences activitySharedPref = container.getContext().getSharedPreferences("Event", Context.MODE_PRIVATE);
                String activityId = result.getId();
                Log.e("highlight","Activity ID: "+activityId);
                activitySharedPref.edit().putString("EventID", activityId).apply();

                FragmentManager fragmentManager = fragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, new EventDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        /*mock highlight
        if(position%3==0){
            item.setNameText("ละครนิทรรศ 17");
            item.setDescriptionText("การกลับมาอีกครั้งในรอบ 4 ปีของละครเวทีคณะวิศวกรรมศาสตร์ จุฬาลงกรณ์มหาวิทยาลัย");
            item.setImageUrl("0");
        }
        else if(position%3==1){
            item.setNameText("The Accountant นักบัญชี");
            item.setDescriptionText("งาน STAT CHULA EXPO 2017 ของภาควิชาสถิติ คณะพาณิชยศาสตร์และการบัญชี จุฬาฯ วันที่ 15-19 มีนาคม 2017");
            item.setImageUrl("1");
        } else {
            item.setNameText("Psyxcho Highlight");
            item.setDescriptionText("งานจุฬาฯ Expo 2017 คณะจิตวิทยา" +
                    "Psyche Chula Expo 2017");
            item.setImageUrl("2");
        }
        */
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
