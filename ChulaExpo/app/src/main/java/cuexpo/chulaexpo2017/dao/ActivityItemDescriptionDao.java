
package cuexpo.chulaexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityItemDescriptionDao {

    @SerializedName("th")
    @Expose
    private String th;
    @SerializedName("en")
    @Expose
    private String en;

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

}
