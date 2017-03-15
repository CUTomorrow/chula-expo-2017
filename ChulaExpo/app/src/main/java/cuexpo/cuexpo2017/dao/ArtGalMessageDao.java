
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtGalMessageDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private ArtGalMessageResult results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArtGalMessageResult getResults() {
        return results;
    }

    public void setResults(ArtGalMessageResult results) {
        this.results = results;
    }

}
