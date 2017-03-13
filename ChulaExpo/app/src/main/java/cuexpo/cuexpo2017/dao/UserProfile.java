package cuexpo.cuexpo2017.dao;

/**
 * Created by dragonnight on 8/3/2560.
 */

public class UserProfile {
    private String _id;
    private String email;
    private Token[] tokens;
    private String name;
    private String gender;
    private int age;
    private String profile;
    private String type;
    private String tags;
    private String academicLevel;
    private String academicYear;
    private String academicSchool;
    private String workerJob;
    private String facebook;

    public UserProfile(String email, Token[] tokens, String name, String gender, int age, String profile, String type, String tag, String academicLevel, String academicYear, String academicSchool, String workerJob, String facebook) {
        this.email = email;
        this.tokens = tokens;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.profile = profile;
        this.type = type;
        this.tags = tag;
        this.academicLevel = academicLevel;
        this.academicYear = academicYear;
        this.academicSchool = academicSchool;
        this.workerJob = workerJob;
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Token[] getTokens() {
        return tokens;
    }

    public void setTokens(Token[] tokens) {
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tags;
    }

    public void setTag(String tag) {
        this.tags = tag;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getAcademicSchool() {
        return academicSchool;
    }

    public void setAcademicSchool(String academicSchool) {
        this.academicSchool = academicSchool;
    }

    public String getWorkerJob() {
        return workerJob;
    }

    public void setWorkerJob(String workerJob) {
        this.workerJob = workerJob;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
