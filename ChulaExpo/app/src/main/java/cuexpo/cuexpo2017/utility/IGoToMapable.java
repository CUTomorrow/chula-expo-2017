package cuexpo.cuexpo2017.utility;

import android.location.Location;

/**
 * Created by Kasidit on 08-Nov-15.
 */
public interface IGoToMapable {
    void goToMap(int facultyId);
    void goToMap(NormalPinMapEntity entity);
}
