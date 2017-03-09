package cuexpo.chulaexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by APTX-4869 (LOCAL) on 3/5/2017.
 */
//            "rooms": [
//            "58a8724795f58c38f62d15fd",
//            "58abf0734a1728016e60368e"
//            ],
public class PlaceItemResultDao {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("name")
    @Expose
    private Name name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
