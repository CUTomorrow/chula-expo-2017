package cuexpo.cuexpo2017.dao;

/**
 * Created by APTX-4869 (LOCAL) on 3/16/2017.
 */

public class ArtGalMessage {
    private String message;
    private String activity;

    public ArtGalMessage(String message, String activity) {
        this.message = message;
        this.activity = activity;
    }

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
