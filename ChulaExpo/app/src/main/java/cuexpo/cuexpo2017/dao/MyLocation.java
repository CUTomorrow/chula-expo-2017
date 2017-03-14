package cuexpo.cuexpo2017.dao;

/**
 * Created by APTX-4869 (LOCAL) on 3/14/2017.
 */

public class MyLocation {

    private double latitude;
    private double longitude;

    public MyLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
