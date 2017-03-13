
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResult {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("academic")
    @Expose
    private String academic;
    @SerializedName("worker")
    @Expose
    private String worker;
    @SerializedName("staff")
    @Expose
    private String staff;
    @SerializedName("academicLevel")
    @Expose
    private String academicLevel;
    @SerializedName("academicYear")
    @Expose
    private String academicYear;
    @SerializedName("academicSchool")
    @Expose
    private String academicSchool;
    @SerializedName("workerJob")
    @Expose
    private String workerJob;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
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
}
