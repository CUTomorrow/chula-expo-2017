package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dragonnight on 13/3/2560.
 */

public class ArtGalMessageResult {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("activity")
    @Expose
    private String activity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
