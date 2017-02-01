package cuexpo.chulaexpo.adapter;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cuexpo.chulaexpo.view.FacultyListItem;

/**
 * Created by Administrator on 2/1/2017.
 */

public class FacultyListAdapter extends BaseAdapter {

    public FacultyListAdapter() {

    }

    @Override
    public int getCount() {
        return 18;
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
