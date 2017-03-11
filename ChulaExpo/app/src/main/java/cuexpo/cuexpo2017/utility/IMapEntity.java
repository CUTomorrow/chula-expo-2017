package cuexpo.cuexpo2017.utility;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Kasidit on 05-Nov-15.
 */
public interface IMapEntity {
    boolean isVisible();

    void setVisible(boolean visible);

    void setMap(GoogleMap map);

}
