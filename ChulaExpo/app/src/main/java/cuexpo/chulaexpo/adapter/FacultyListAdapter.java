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

public class FacultyListAdapter extends BaseAdapter {

    public FacultyListAdapter() {

    }

    @Override
    public int getCount() {
        return 23;
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
        switch(position) {
            case 0:
                faculty.setFacultyBg(R.drawable.eng_bg);
                faculty.setFacultyIcon(R.drawable.eng);
                faculty.setFacultyTag("ENG", Color.WHITE, Color.rgb(128, 0, 0));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
                break;
            case 1:
                faculty.setFacultyBg(R.drawable.arts_bg);
                faculty.setFacultyIcon(R.drawable.arts);
                faculty.setFacultyTag("ARTS", Color.WHITE, Color.rgb(153, 153, 153));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
                break;
            case 2:
                faculty.setFacultyBg(R.drawable.sci_bg);
                faculty.setFacultyIcon(R.drawable.sci);
                faculty.setFacultyTag("SCI", Color.GRAY, Color.rgb(255, 222, 00));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
                break;
            case 3:
                faculty.setFacultyBg(R.drawable.polsci_bg);
                faculty.setFacultyIcon(R.drawable.polsci);
                faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 4:
                faculty.setFacultyBg(R.drawable.arch_bg);
                faculty.setFacultyIcon(R.drawable.arch);
                faculty.setFacultyTag("ARCH", Color.WHITE, Color.rgb(153, 51, 0));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
                break;
            case 5:
                faculty.setFacultyBg(R.drawable.banshi_bg);
                faculty.setFacultyIcon(R.drawable.banshi);
                faculty.setFacultyTag("BANSHI", Color.WHITE, Color.rgb(00, 171, 214));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
                break;
            case 6:
                faculty.setFacultyBg(R.drawable.edu_bg);
                faculty.setFacultyIcon(R.drawable.edu);
                faculty.setFacultyTag("EDU", Color.GRAY, Color.rgb(255, 51, 00));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
                break;
            case 7:
                faculty.setFacultyBg(R.drawable.commarts_bg);
                faculty.setFacultyIcon(R.drawable.commarts);
                faculty.setFacultyTag("C-ARTS", Color.WHITE, Color.rgb(00, 00, 128));
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 8:
                faculty.setFacultyBg(R.drawable.econ_bg);
                faculty.setFacultyIcon(R.drawable.econ);
                faculty.setFacultyTag("ECON", Color.WHITE, Color.rgb(164, 134, 11));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
                break;
            case 9:
                faculty.setFacultyBg(R.drawable.med_bg);
                faculty.setFacultyIcon(R.drawable.med);
                faculty.setFacultyTag("MED", Color.WHITE, Color.rgb(00, 102, 00));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
                break;
            case 10:
                faculty.setFacultyBg(R.drawable.vet_bg);
                faculty.setFacultyIcon(R.drawable.vet);
                faculty.setFacultyTag("VET", Color.GRAY, Color.rgb(102, 204, 204));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
                break;
            case 11:
                faculty.setFacultyBg(R.drawable.dent_bg);
                faculty.setFacultyIcon(R.drawable.dent);
                faculty.setFacultyTag("DENT", Color.WHITE, Color.rgb(57, 25, 122));
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 12:
                faculty.setFacultyBg(R.drawable.pham_bg);
                faculty.setFacultyIcon(R.drawable.pharm);
                faculty.setFacultyTag("PHARM", Color.WHITE, Color.rgb(102, 204, 204));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
                break;
            case 13:
                faculty.setFacultyBg(R.drawable.law_bg);
                faculty.setFacultyIcon(R.drawable.law);
                faculty.setFacultyTag("LAW", Color.WHITE, Color.rgb(139, 139, 139));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
                break;
            case 14:
                faculty.setFacultyBg(R.drawable.faa_bg);
                faculty.setFacultyIcon(R.drawable.faa);
                faculty.setFacultyTag("FAA", Color.GRAY, Color.rgb(196, 28, 28));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
                break;
            case 15:
                faculty.setFacultyBg(R.drawable.nur_bg);
                faculty.setFacultyIcon(R.drawable.nur);
                faculty.setFacultyTag("NUR", Color.WHITE, Color.rgb(255, 00, 00));
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 16:
                faculty.setFacultyBg(R.drawable.ahs_bg);
                faculty.setFacultyIcon(R.drawable.ahs);
                faculty.setFacultyTag("AHS", Color.WHITE, Color.rgb(204, 153, 255));
                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Engineering");
                break;
            case 17:
                faculty.setFacultyBg(R.drawable.psy_bg);
                faculty.setFacultyIcon(R.drawable.psy);
                faculty.setFacultyTag("PSY", Color.WHITE, Color.rgb(89, 51, 238));
                faculty.setFacultyTitle("อักษรศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Arts");
                break;
            case 18:
                faculty.setFacultyBg(R.drawable.spsc_bg);
                faculty.setFacultyIcon(R.drawable.spsc);
                faculty.setFacultyTag("SPSC", Color.GRAY, Color.rgb(255, 102, 00));
                faculty.setFacultyTitle("วิทยาศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Science");
                break;
            case 19:
                faculty.setFacultyBg(R.drawable.sar_bg);
                faculty.setFacultyIcon(R.drawable.sar);
                faculty.setFacultyTag("SAR", Color.WHITE, Color.rgb(147, 43, 42));
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 20:
                faculty.setFacultyBg(R.drawable.sar_bg);
                faculty.setFacultyIcon(R.drawable.sar);
                faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 21:
                faculty.setFacultyBg(R.drawable.sar_bg);
                faculty.setFacultyIcon(R.drawable.sar);
                faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
            case 22:
                faculty.setFacultyBg(R.drawable.grad_bg);
                faculty.setFacultyIcon(R.drawable.grad);
                faculty.setFacultyTag("GRAD", Color.WHITE, Color.rgb(233, 73, 127));
                faculty.setFacultyTitle("รัฐศาสตร์");
                faculty.setFacultyTitleEng("Faculty of Political Science");
                break;
        }

        return faculty;
    }
}
