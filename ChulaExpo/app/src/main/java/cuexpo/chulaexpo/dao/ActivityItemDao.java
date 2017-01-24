package cuexpo.chulaexpo.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dragonnight on 26/12/2559.
 */

//USER JSON https://github.com/nutstick/Chula-Expo/blob/master/Schema.json

public class ActivityItemDao implements Parcelable {
    //TODO: Apply according API

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("reservable")
    @Expose
    private List<Object> reservable = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("videoUrl")
    @Expose
    private List<Object> videoUrl = null;
    @SerializedName("imgUrl")
    @Expose
    private List<Object> imgUrl = null;




    public ActivityItemDao(){

    }

    protected ActivityItemDao(Parcel in) {
        id = in.readString();
        name = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        faculty = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    public static final Creator<ActivityItemDao> CREATOR = new Creator<ActivityItemDao>() {
        @Override
        public ActivityItemDao createFromParcel(Parcel in) {
            return new ActivityItemDao(in);
        }

        @Override
        public ActivityItemDao[] newArray(int size) {
            return new ActivityItemDao[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(faculty);
        dest.writeString(startTime);
        dest.writeString(endTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<Object> getReservable() {
        return reservable;
    }

    public void setReservable(List<Object> reservable) {
        this.reservable = reservable;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Object> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(List<Object> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<Object> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<Object> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
