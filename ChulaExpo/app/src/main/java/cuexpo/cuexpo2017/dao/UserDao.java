
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private UserResult result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserResult getResults() {
        return result;
    }

    public void setResults(UserResult result) {
        this.result = result;
    }

}
