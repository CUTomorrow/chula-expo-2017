package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by APTX-4869 (LOCAL) on 3/14/2017.
 */

public class WhereResult {
    @SerializedName("text")
    @Expose
    private WhereText text;

    public WhereText getText() {
        return text;
    }

    public void setText(WhereText text) {
        this.text = text;
    }
}
