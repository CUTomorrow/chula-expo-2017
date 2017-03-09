
package cuexpo.chulaexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seats {

    @SerializedName("fullCapacity")
    @Expose
    private Integer fullCapacity;
    @SerializedName("reserved")
    @Expose
    private Integer reserved;
    @SerializedName("avaliable")
    @Expose
    private Integer avaliable;

    public Integer getFullCapacity() {
        return fullCapacity;
    }

    public void setFullCapacity(Integer fullCapacity) {
        this.fullCapacity = fullCapacity;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public Integer getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Integer avaliable) {
        this.avaliable = avaliable;
    }

}
