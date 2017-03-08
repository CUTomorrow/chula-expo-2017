
package cuexpo.chulaexpo.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDao {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("results")
    @Expose
    private LoginResultDao results;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResultDao getResults() {
        return results;
    }

    public void setResults(LoginResultDao results) {
        this.results = results;
    }

}
