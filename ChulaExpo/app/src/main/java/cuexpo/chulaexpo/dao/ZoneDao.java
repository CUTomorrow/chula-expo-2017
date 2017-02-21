
package cuexpo.chulaexpo.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZoneDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private List<ZoneResult> results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ZoneResult> getResults() {
        return results;
    }

    public void setResults(List<ZoneResult> results) {
        this.results = results;
    }

}
