
package cuexpo.cuexpo2017.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZoneResult {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("shortName")
    @Expose
    private ShortName shortName;
    @SerializedName("welcomeMessage")
    @Expose
    private WelcomeMessage welcomeMessage;
    @SerializedName("places")
    @Expose
    private List<String> places = null;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("banner")
    @Expose
    private String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public ShortName getShortName() {
        return shortName;
    }

    public void setShortName(ShortName shortName) {
        this.shortName = shortName;
    }

    public WelcomeMessage getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(WelcomeMessage welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBanner() { return banner; }

    public void setBanner(String banner) {this.banner = banner; }

}
