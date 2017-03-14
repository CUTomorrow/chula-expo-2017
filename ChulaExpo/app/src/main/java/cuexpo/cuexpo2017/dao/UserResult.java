
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
    private Academic academic;
    @SerializedName("staff")
    @Expose
    private Object staff;
    @SerializedName("worker")
    @Expose
    private Worker worker;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;


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

    public Academic getAcademic() {
        return academic;
    }

    public void setAcademic(Academic academic) {
        this.academic = academic;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public Object getStaff() {
        return staff;
    }

    public void setStaff(Object staff) {
        this.staff = staff;
    }
}
