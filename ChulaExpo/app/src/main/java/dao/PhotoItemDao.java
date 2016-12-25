package dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dragonnight on 26/12/2559.
 */

//USER JSON https://github.com/nutstick/Chula-Expo/blob/master/Schema.json

public class PhotoItemDao {
    @SerializedName("link") private String link;
    @SerializedName("image_url") private String imageUrl;
}
