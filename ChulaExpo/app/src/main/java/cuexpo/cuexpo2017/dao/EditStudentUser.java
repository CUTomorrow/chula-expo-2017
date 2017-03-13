package cuexpo.cuexpo2017.dao;

/**
 * Created by dragonnight on 14/3/2560.
 */

public class EditStudentUser {
    private String name;
    private int age;
    private String gender;
    private String academicLevel;
    private String academicYear;
    private String academicSchool;

    public EditStudentUser(String name, int age, String gender, String academicLevel, String academicYear, String academicSchool) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.academicLevel = academicLevel;
        this.academicYear = academicYear;
        this.academicSchool = academicSchool;
    }
}
