package cuexpo.chulaexpo.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dragonnight on 26/12/2559.
 */

//USER JSON https://github.com/nutstick/Chula-Expo/blob/master/Schema.json

public class PhotoItemDao implements Parcelable {
    //TODO: Apply according API

    @SerializedName("id")               private int id;
    @SerializedName("link")             private String link;
    @SerializedName("image_url")        private String imageUrl;
    @SerializedName("caption")          private String caption;
    @SerializedName("user_id")          private int userId;
    @SerializedName("username")         private String username;
    @SerializedName("profile_picture")  private String profilePicture;
    @SerializedName("tags")             private List<String> tags = new ArrayList<>();
    @SerializedName("created_time")     private Date createdTime;
    @SerializedName("camera")           private String camera;
    @SerializedName("lens")             private String lens;
    @SerializedName("focal_length")     private String focalLength;
    @SerializedName("iso")              private String iso;
    @SerializedName("shutter_speed")    private String shutterSpeed;
    @SerializedName("aperture")         private String aperture;

    public  PhotoItemDao(){

    }

    protected PhotoItemDao(Parcel in) {
        id = in.readInt();
        link = in.readString();
        imageUrl = in.readString();
        caption = in.readString();
        userId = in.readInt();
        username = in.readString();
        profilePicture = in.readString();
        tags = in.createStringArrayList();
        camera = in.readString();
        lens = in.readString();
        focalLength = in.readString();
        iso = in.readString();
        shutterSpeed = in.readString();
        aperture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(link);
        dest.writeString(imageUrl);
        dest.writeString(caption);
        dest.writeInt(userId);
        dest.writeString(username);
        dest.writeString(profilePicture);
        dest.writeStringList(tags);
        dest.writeString(camera);
        dest.writeString(lens);
        dest.writeString(focalLength);
        dest.writeString(iso);
        dest.writeString(shutterSpeed);
        dest.writeString(aperture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemDao> CREATOR = new Creator<PhotoItemDao>() {
        @Override
        public PhotoItemDao createFromParcel(Parcel in) {
            return new PhotoItemDao(in);
        }

        @Override
        public PhotoItemDao[] newArray(int size) {
            return new PhotoItemDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public static Creator<PhotoItemDao> getCREATOR() {
        return CREATOR;
    }
}
