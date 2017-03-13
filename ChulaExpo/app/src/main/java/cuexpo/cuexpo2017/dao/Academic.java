package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dragonnight on 13/3/2560.
 */

public class Academic {
    @SerializedName("level")
    @Expose
    private String academicLevel;
    @SerializedName("school")
    @Expose
    private String academicSchool;
    @SerializedName("year")
    @Expose
    private String academicYear;


    public String getAcademicSchool() {
        return academicSchool;
    }

    public void setAcademicSchool(String academicSchool) {
        this.academicSchool = academicSchool;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }
}
