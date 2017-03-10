package cuexpo.cuexpo2017.adapter;

import android.graphics.Color;
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
        super();
        this.faculties = faculties;
    }

    @Override
    public int getCount() {
        return 21;
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
            case 36: return "NUR";
            case 37: return "AHS";
            case 38: return "PSY";
            case 39: return "SPSC";
            case 40: return "SAR";
            case 42: return "GRAD";
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        long id = position + 21;
        if (id == 41) return 42;
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null)
            faculty = (FacultyListItem) convertView;
        else
            faculty = new FacultyListItem(parent.getContext());

        setLayout(position);

//        switch((int) getItemId(position)) {
//            case 21:
//                faculty.setFacultyBg(R.drawable.eng_bg);
//                faculty.setFacultyIcon(R.drawable.eng);
//                faculty.setFacultyTag("ENG", Color.WHITE, Color.rgb(128, 0, 0));
//                faculty.setFacultyTitle("วิศวกรรมศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Engineering");
//                break;
//            case 22:
//                faculty.setFacultyBg(R.drawable.arts_bg);
//                faculty.setFacultyIcon(R.drawable.arts);
//                faculty.setFacultyTag("ARTS", Color.WHITE, Color.rgb(153, 153, 153));
//                faculty.setFacultyTitle("อักษรศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Arts");
//                break;
//            case 23:
//                faculty.setFacultyBg(R.drawable.sci_bg);
//                faculty.setFacultyIcon(R.drawable.sci);
//                faculty.setFacultyTag("SCI", Color.BLACK, Color.rgb(255, 222, 00));
//                faculty.setFacultyTitle("วิทยาศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Science");
//                break;
//            case 24:
//                faculty.setFacultyBg(R.drawable.polsci_bg);
//                faculty.setFacultyIcon(R.drawable.polsci);
//                faculty.setFacultyTag("POLSCI", Color.WHITE, Color.BLACK);
//                faculty.setFacultyTitle("รัฐศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Political Science");
//                break;
//            case 25:
//                faculty.setFacultyBg(R.drawable.arch_bg);
//                faculty.setFacultyIcon(R.drawable.arch);
//                faculty.setFacultyTag("ARCH", Color.WHITE, Color.rgb(153, 51, 0));
//                faculty.setFacultyTitle("สถาปัตยกรรมศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Architecture");
//                break;
//            case 26:
//                faculty.setFacultyBg(R.drawable.banshi_bg);
//                faculty.setFacultyIcon(R.drawable.banshi);
//                faculty.setFacultyTag("BANSHI", Color.WHITE, Color.rgb(00, 171, 214));
//                faculty.setFacultyTitle("พาณิชยศาสตร์และการบัญชี");
//                faculty.setFacultyTitleEng("Faculty of Commerce and Accountancy");
//                break;
//            case 27:
//                faculty.setFacultyBg(R.drawable.edu_bg);
//                faculty.setFacultyIcon(R.drawable.edu);
//                faculty.setFacultyTag("EDU", Color.WHITE, Color.rgb(255, 51, 00));
//                faculty.setFacultyTitle("ครุศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Education");
//                break;
//            case 28:
//                faculty.setFacultyBg(R.drawable.commarts_bg);
//                faculty.setFacultyIcon(R.drawable.commarts);
//                faculty.setFacultyTag("C-ARTS", Color.WHITE, Color.rgb(00, 00, 128));
//                faculty.setFacultyTitle("นิเทศศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Communication Arts");
//                break;
//            case 29:
//                faculty.setFacultyBg(R.drawable.econ_bg);
//                faculty.setFacultyIcon(R.drawable.econ);
//                faculty.setFacultyTag("ECON", Color.BLACK, Color.rgb(164, 134, 11));
//                faculty.setFacultyTitle("เศรษฐศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Economics");
//                break;
//            case 30:
//                faculty.setFacultyBg(R.drawable.med_bg);
//                faculty.setFacultyIcon(R.drawable.med);
//                faculty.setFacultyTag("MED", Color.WHITE, Color.rgb(00, 102, 00));
//                faculty.setFacultyTitle("แพทยศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Medicine");
//                break;
//            case 31:
//                faculty.setFacultyBg(R.drawable.vet_bg);
//                faculty.setFacultyIcon(R.drawable.vet);
//                faculty.setFacultyTag("VET", Color.BLACK, Color.rgb(102, 204, 204));
//                faculty.setFacultyTitle("สัตวแพทยศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Veterinary Science");
//                break;
//            case 32:
//                faculty.setFacultyBg(R.drawable.dent_bg);
//                faculty.setFacultyIcon(R.drawable.dent);
//                faculty.setFacultyTag("DENT", Color.WHITE, Color.rgb(57, 25, 122));
//                faculty.setFacultyTitle("ทันตแพทยศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Dentistry");
//                break;
//            case 33:
//                faculty.setFacultyBg(R.drawable.pham_bg);
//                faculty.setFacultyIcon(R.drawable.pharm);
//                faculty.setFacultyTag("PHARM", Color.WHITE, Color.rgb(102, 204, 204));
//                faculty.setFacultyTitle("เภสัชศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Pharmaceutical Sciences");
//                break;
//            case 34:
//                faculty.setFacultyBg(R.drawable.law_bg);
//                faculty.setFacultyIcon(R.drawable.law);
//                faculty.setFacultyTag("LAW", Color.WHITE, Color.rgb(00, 00, 00));
//                faculty.setFacultyTitle("นิติศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Law");
//                break;
//            case 35:
//                faculty.setFacultyBg(R.drawable.faa_bg);
//                faculty.setFacultyIcon(R.drawable.faa);
//                faculty.setFacultyTag("FAA", Color.WHITE, Color.rgb(196, 28, 28));
//                faculty.setFacultyTitle("ศิลปกรรมศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Fine and Applied Arts");
//                break;
//            case 36:
//                faculty.setFacultyBg(R.drawable.nur_bg);
//                faculty.setFacultyIcon(R.drawable.nur);
//                faculty.setFacultyTag("NUR", Color.WHITE, Color.rgb(255, 00, 00));
//                faculty.setFacultyTitle("พยาบาลศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Nursing");
//                break;
//            case 37:
//                faculty.setFacultyBg(R.drawable.ahs_bg);
//                faculty.setFacultyIcon(R.drawable.ahs);
//                faculty.setFacultyTag("AHS", Color.BLACK, Color.rgb(204, 153, 255));
//                faculty.setFacultyTitle("สหเวชศาสตร์");
//                faculty.setFacultyTitleEng("Faculty of Allied Health Sciences");
//                break;
//            case 38:
//                faculty.setFacultyBg(R.drawable.psy_bg);
//                faculty.setFacultyIcon(R.drawable.psy);
//                faculty.setFacultyTag("PSY", Color.WHITE, Color.rgb(89, 51, 238));
//                faculty.setFacultyTitle("จิตวิทยา");
//                faculty.setFacultyTitleEng("Faculty of Psychology");
//                break;
//            case 39:
//                faculty.setFacultyBg(R.drawable.spsc_bg);
//                faculty.setFacultyIcon(R.drawable.spsc);
//                faculty.setFacultyTag("SPSC", Color.WHITE, Color.rgb(255, 102, 00));
//                faculty.setFacultyTitle("วิทยาศาสตร์การกีฬา");
//                faculty.setFacultyTitleEng("Faculty of Sports Science");
//                break;
//            case 40:
//                faculty.setFacultyBg(R.drawable.sar_bg);
//                faculty.setFacultyIcon(R.drawable.sar);
//                faculty.setFacultyTag("SAR", Color.WHITE, Color.rgb(147, 43, 42));
//                faculty.setFacultyTitle("ทรัพยากรการเกษตร");
//                faculty.setFacultyTitleEng("School of Agricultural Resource");
//                break;
//            case 42:
//                faculty.setFacultyBg(R.drawable.grad_bg);
//                faculty.setFacultyIcon(R.drawable.grad);
//                faculty.setFacultyTag("GRAD", Color.WHITE, Color.rgb(233, 73, 127));
//                faculty.setFacultyTitle("บัณฑิตวิทยาลัย");
//                faculty.setFacultyTitleEng("Graduate School");
//                break;
//        }
        return faculty;
    }

    private void setLayout(int position){
        String nameUpperCase = (String) getItem(position);
        String nameLowerCase = nameUpperCase.toLowerCase();
        String facultyTag = nameUpperCase;
        if (nameUpperCase.equals("COMMARTS")) facultyTag = "C-ARTS";

        faculty.setFacultyBg(Resource.getDrawable(nameLowerCase + "_bg"));
        faculty.setFacultyIcon(Resource.getDrawable(nameLowerCase));
        faculty.setFacultyTag(facultyTag, Color.WHITE, Resource.getColor(nameUpperCase));
        InterestItem facultyItem = faculties.get((int) getItemId(position));
        faculty.setFacultyTitle(facultyItem.getName());
        faculty.setFacultyTitleEng(facultyItem.getNameEng());
    }
}
