
package cuexpo.chulaexpo2017.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityItemCollectionDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private List<ActivityItemResultDao> results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ActivityItemResultDao> getResults() {
        return results;
    }

    public void setResults(List<ActivityItemResultDao> results) {
        this.results = results;
    }

    public void addResults(ActivityItemResultDao resultDao){
        this.results.add(resultDao);
    }

}
