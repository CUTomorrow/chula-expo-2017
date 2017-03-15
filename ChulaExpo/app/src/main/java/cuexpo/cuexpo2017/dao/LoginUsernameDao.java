
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUsernameDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("results")
    @Expose
    private LoginUsernameResultDao results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LoginUsernameResultDao getResults() {
        return results;
    }

    public void setResults(LoginUsernameResultDao results) {
        this.results = results;
    }

}
