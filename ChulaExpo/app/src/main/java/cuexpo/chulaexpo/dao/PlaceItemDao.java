package cuexpo.chulaexpo.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by APTX-4869 (LOCAL) on 3/5/2017.
 */

public class PlaceItemDao {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private PlaceItemResultDao results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PlaceItemResultDao getResults() {
        return results;
    }

    public void setResults(PlaceItemResultDao results) {
        this.results = results;
    }
}



