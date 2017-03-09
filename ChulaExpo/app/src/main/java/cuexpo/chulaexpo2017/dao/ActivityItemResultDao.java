
package cuexpo.chulaexpo2017.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityItemResultDao {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("isHighlight")
    @Expose
    private Boolean isHighlight;
    @SerializedName("pdf")
    @Expose
    private String pdf;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("rounds")
    @Expose
    private List<Object> rounds = null;
    @SerializedName("location")
    @Expose
    private ActivityItemLocationDao location;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("link")
    @Expose
    private List<String> link = null;
    @SerializedName("pictures")
    @Expose
    private String[] pictures = null;
    @SerializedName("description")
    @Expose
    private ActivityItemDescriptionDao description;
    @SerializedName("shortDescription")
    @Expose
    private ActivityShortDescriptionDao shortDescription;
    @SerializedName("name")
    @Expose
    private ActivityNameDao name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Boolean getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Boolean isHighlight) {
        this.isHighlight = isHighlight;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<Object> getRounds() {
        return rounds;
    }

    public void setRounds(List<Object> rounds) {
        this.rounds = rounds;
    }

    public ActivityItemLocationDao getLocation() {
        return location;
    }

    public void setLocation(ActivityItemLocationDao location) {
        this.location = location;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<String> getLink() {
        return link;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public ActivityItemDescriptionDao getDescription() {
        return description;
    }

    public void setDescription(ActivityItemDescriptionDao description) {
        this.description = description;
    }

    public ActivityShortDescriptionDao getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(ActivityShortDescriptionDao shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ActivityNameDao getName() {
        return name;
    }

    public void setName(ActivityNameDao name) {
        this.name = name;
    }

}
