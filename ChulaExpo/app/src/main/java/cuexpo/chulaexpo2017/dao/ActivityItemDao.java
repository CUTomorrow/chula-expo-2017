
package cuexpo.chulaexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityItemDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private ActivityItemResultDao results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ActivityItemResultDao getResults() {
        return results;
    }

    public void setResults(ActivityItemResultDao results) {
        this.results = results;
    }

}
