package cuexpo.chulaexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by APTX-4869 (LOCAL) on 3/6/2017.
 */

public class FacilityDao {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private List<FacilityResult> results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<FacilityResult> getResults() {
        return results;
    }

    public void setResults(List<FacilityResult> results) {
        this.results = results;
    }
}
