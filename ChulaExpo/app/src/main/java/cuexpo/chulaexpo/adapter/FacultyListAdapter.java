package cuexpo.chulaexpo.adapter;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.view.FacultyListItem;

/**
 * Created by Administrator on 2/1/2017.
 */
/*
public class FacultyListAdapter extends RecyclerView.Adapter<FacultyListAdapter.Holder> {

    ArrayList<FacultyListItem> data;

    public FacultyListAdapter(FacultyListItem item) {
        data = new ArrayList<FacultyListItem>();
        data.add(item);
    }

    public FacultyListAdapter(ArrayList<FacultyListItem> item) {
        data = new ArrayList<FacultyListItem>();
        data.addAll(item);
    }

    public void addFaculty(FacultyListItem item) {
        data.add(item);
    }

    public void addFaculty(ArrayList<FacultyListItem> item) {
        data.addAll(item);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faculty, parent, false);
        Holder faculty = new Holder(item);
        return faculty;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.d("bind", "bind data in onBindViewHolder position : " + position);
        Log.d("bind", "bind data " + data.get(position).getFacultyTitle().getText().toString());
        FacultyListItem item = data.get(position);
        holder.facultyItem.setFacultyTitle(item.getFacultyTitle().getText().toString());
        holder.facultyItem.setFacultyTitleEng(item.getFacultyTitleEng().getText().toString());
        holder.facultyItem.setFacultyBg(item.getFacultyBg());
        holder.facultyItem.setFacultyIcon(item.getfacultyIcon());
        holder.facultyItem.setFacultyTag(item.getFacultyTag().getText().toString(), item.getFacultyTag().getCurrentTextColor(), item.getFacultyTag().getDrawingCacheBackgroundColor());
        holder.title.setText(item.getFacultyTitle().getText().toString());
        holder.titleEng.setText(item.getFacultyTitleEng().getText().toString());
        holder.background.setImageDrawable(item.getFacultyBg());
        holder.icon.setImageDrawable(item.getfacultyIcon());
        holder.tag.setText(item.getFacultyTag().getText().toString());
        holder.tag.setTextColor(item.getFacultyTag().getCurrentTextColor());
        holder.tag.setBackgroundColor(item.getFacultyTag().getDrawingCacheBackgroundColor());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public TextView title, titleEng, tag;
        public ImageView background, icon;

        public Holder(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.faculty_bg);
            icon = (ImageView) itemView.findViewById(R.id.faculty_icon);
            tag = (TextView) itemView.findViewById(R.id.faculty_tag);
            title = (TextView) itemView.findViewById(R.id.faculty_title);
            titleEng = (TextView) itemView.findViewById(R.id.faculty_title_eng);
        }
    }

}
*/
public class FacultyListAdapter extends BaseAdapter {

    public FacultyListAdapter() {

    }

    @Override
    public int getCount() {
        return 17;
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
        FacultyListItem faculty;
        if (convertView != null)
            faculty = (FacultyListItem) convertView;
        else
            faculty = new FacultyListItem(parent.getContext());

        //mock
        if (position%4 == 0) {
            faculty.setFacultyBg("1");
            faculty.setFacultyIcon("0");
            faculty.setFacultyTag("ENG", Color.WHITE, Color.rgb(156, 11, 16));
            faculty.setFacultyTitle("วิศวกรรมศาสตร์");
            faculty.setFacultyTitleEng("Faculty of Engineering");
        }
        else if (position%4 == 1) {
            faculty.setFacultyBg("0");
            faculty.setFacultyIcon("0");
            faculty.setFacultyTag("ART", Color.WHITE, Color.rgb(85, 85, 85));
            faculty.setFacultyTitle("อักษรศาสตร์");
            faculty.setFacultyTitleEng("Faculty of Arts");
        }
        else if (position%4 == 2) {
            faculty.setFacultyBg("2");
            faculty.setFacultyIcon("0");
            faculty.setFacultyTag("SC", Color.GRAY, Color.rgb(254, 198, 1));
            faculty.setFacultyTitle("วิทยาศาสตร์");
            faculty.setFacultyTitleEng("Faculty of Science");
        }
        else {
            faculty.setFacultyBg("1");
            faculty.setFacultyIcon("0");
            faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
            faculty.setFacultyTitle("รัฐศาสตร์");
            faculty.setFacultyTitleEng("Faculty of Political Science");
        }

        return faculty;
    }
}
