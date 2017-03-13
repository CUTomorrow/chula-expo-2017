package cuexpo.cuexpo2017.utility;

import android.graphics.Color;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.datatype.InterestItem;

/**
 * Created by APTX-4869 (LOCAL) on 2/17/2017.
 */

public class Resource {
    public static String[] lightZone = {"SCI", "ECON", "LAW", "VET"};
    public static int[] tagBgs = {
            R.drawable.energy_bg, R.drawable.aging_society_bg, R.drawable.body_bg, R.drawable.mind_bg, R.drawable.social_bg,
            R.drawable.invention_bg, R.drawable.qlt_of_life_bg, R.drawable.food_agriculture_bg, R.drawable.community_bg,
            R.drawable.art_cult_bg, R.drawable.med_interest_bg, R.drawable.economy_bg, R.drawable.transport_bg, R.drawable.technology_bg,
            R.drawable.environment_bg, R.drawable.thai_wisdom_bg, R.drawable.design_bg, R.drawable.law_interest_bg, R.drawable.education_bg,
            R.drawable.royal_duties_bg, R.drawable.beauty_bg, R.drawable.life_bg, R.drawable.communication_bg, R.drawable.sport_bg,
            R.drawable.science_bg, R.drawable.entertainment_bg, R.drawable.international_issue_bg
    };

    public static int[] tagIcons = {
            R.drawable.int_energy, R.drawable.int_aging, R.drawable.int_body, R.drawable.int_mind, R.drawable.int_social_issue,
            R.drawable.int_invention, R.drawable.int_qlt_of_life, R.drawable.int_food, R.drawable.int_community, R.drawable.int_art_cult,
            R.drawable.int_med, R.drawable.int_economy, R.drawable.int_transport, R.drawable.int_tech, R.drawable.int_environ,
            R.drawable.int_thai_wis, R.drawable.int_design, R.drawable.int_law, R.drawable.int_education, R.drawable.int_royal,
            R.drawable.int_beauty, R.drawable.int_life, R.drawable.int_communication, R.drawable.int_sport, R.drawable.int_science,
            R.drawable.int_entertainment, R.drawable.int_inter
    };

    public static int getColor(String color) {
        try {
            return R.color.class.getField(color).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }
    }

    public static int getTagColor(String zoneShortName){
        for(String zone: lightZone) if(zoneShortName.equals(zone)) return Color.BLACK;
        return Color.WHITE;

    }

    public static int getDrawable(String drawable) {
        try {
            return R.drawable.class.getField(drawable).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.DEFAULT;
        }
    }

    public static int getTagBg(int id){
        return tagBgs[id-1];
    }

    public static int getTagIcon(int id){
        return tagIcons[id-1];
    }

    public static String getFacultyShortName(int id) {
        switch(id) {
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
        return "-";
    }

    public static int getFaculltyTagIcon(int id){
        String facultyShortName = getFacultyShortName(id).toLowerCase();
        return Resource.getDrawable(facultyShortName);
    }

    public static int getFaculltyTagBg(int id){
        String facultyShortName = getFacultyShortName(id).toLowerCase();
        return Resource.getDrawable(facultyShortName + "_bg");
    }

    public static String getFacultyTagDisplayName(int id, String name) {
        switch (id) {
            case 40: return "ทรัพยากรการเกษตร";
            case 42: return "บัณฑิตวิทยาลัย";
            case 41: return "หอพักนิสิตฯ";
            case 44: return "พยาบาลตำรวจ";
            case 45: return "พยาบาลกาชาด";
            default: return  name.substring(3);
        }
    }
}
