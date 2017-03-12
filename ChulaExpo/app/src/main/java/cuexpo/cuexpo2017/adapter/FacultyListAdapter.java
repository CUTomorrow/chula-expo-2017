package cuexpo.cuexpo2017.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.datatype.InterestItem;
import cuexpo.cuexpo2017.utility.Resource;
import cuexpo.cuexpo2017.view.FacultyListItem;

/**
 * Created by Administrator on 2/1/2017.
 */

public class FacultyListAdapter extends BaseAdapter {
    private FacultyListItem faculty;
    private List<InterestItem> faculties;

    public FacultyListAdapter(List<InterestItem> faculties) {
        this.faculties = faculties;
    }

    @Override
    public int getCount() {
        return faculties.size();
    }


    @Override
    public Object getItem(int position) {
        switch((int) getItemId(position)) {
            case 21: return "ENG";
            case 22: return "ARTS";
            case 23: return "SCI";
            case 24: return "POLSCI";
            case 25: return "ARCH";
            case 26: return "BANSHI";
            case 27: return "EDU";
            case 28: return "COMMARTS";
            case 29: return "ECON";
            case 30: return "MED";
            case 31: return "VET";
            case 32: return "DENT";
            case 33: return "PHARM";
            case 34: return "LAW";
            case 35: return "FAA";
            case 36: return "FON";
            case 37: return "AHS";
            case 38: return "PSY";
            case 39: return "SPSC";
            case 40: return "SAR";
            case 41: return "RCU";
            case 42: return "GRAD";
            case 44: return "PNC";
            case 45: return "TRCN";
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        long id = position + 21;
        switch(position+21) {
            case 41: id = 45; break;
            case 42: id = 44; break;
            case 43: id = 42; break;
            case 44: id = 41; break;
        }
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null)
            faculty = (FacultyListItem) convertView;
        else
            faculty = new FacultyListItem(parent.getContext());

        setLayout(position);
        return faculty;
    }

    private void setLayout(int position){
        String nameUpperCase = (String) getItem(position);
        String nameLowerCase = nameUpperCase.toLowerCase();
        String facultyTag = nameUpperCase;
        if (nameUpperCase.equals("COMMARTS")) facultyTag = "C-ARTS";
        InterestItem facultyItem = faculties.get(position);
        String name = facultyItem.getName();
        switch ((int) getItemId(position)) {
            case 40: name = "ทรัพยากรการเกษตร"; break;
            case 41: name = "พยาบาลกาชาด"; break;
            case 42: name = "พยาบาลตำรวจ"; break;
            case 44: name = "บัณฑิตวิทยาลัย"; break;
            case 45: name = "หอพักนิสิตฯ"; break;
            default: name = name.substring(3); break;
        }

        faculty.setFacultyBg(Resource.getDrawable(nameLowerCase + "_bg"));
        faculty.setFacultyIcon(Resource.getDrawable(nameLowerCase));
        faculty.setFacultyTag(facultyTag, Resource.getTagColor(nameUpperCase), Resource.getColor(nameUpperCase));
        faculty.setFacultyTitle(name);
        faculty.setFacultyTitleEng(facultyItem.getNameEng());
    }
}
