package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by APTX-4869 (LOCAL) on 3/14/2017.
 */

public class WhereText {
    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("th")
    @Expose
    private String th;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

}
