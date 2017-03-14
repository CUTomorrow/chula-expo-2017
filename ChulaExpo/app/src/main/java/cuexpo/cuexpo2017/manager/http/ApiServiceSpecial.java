package cuexpo.cuexpo2017.manager.http;

import org.json.JSONObject;

import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemDao;
import cuexpo.cuexpo2017.dao.ActivitySearchItemCollectionDao;
import cuexpo.cuexpo2017.dao.DeleteResultDao;
import cuexpo.cuexpo2017.dao.EditAdultUser;
import cuexpo.cuexpo2017.dao.EditStudentUser;
import cuexpo.cuexpo2017.dao.FacilityDao;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.dao.PlaceItemDao;
import cuexpo.cuexpo2017.dao.ReserveDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.dao.TagWrapper;
import cuexpo.cuexpo2017.dao.UserDao;
import cuexpo.cuexpo2017.dao.UserProfile;
import cuexpo.cuexpo2017.dao.ZoneDao;
import cuexpo.cuexpo2017.dao.ZoneItemDao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dragonnight on 26/12/2559.
 */

public interface ApiServiceSpecial {

    @GET("/search")
    Call<ActivitySearchItemCollectionDao> searchActivities(@Query("u") String user,
                                                           @Query("lat") double lat,
                                                           @Query("lng") double lng,
                                                           @Query("cutoff") int distance,
                                                           @Query("q") String queryString);

    @GET("/search")
    Call<ActivitySearchItemCollectionDao> searchActivities(@Query("u") String user,
                                                           @Query("q") String queryString);

    @GET("/search")
    Call<ActivitySearchItemCollectionDao> searchActivities(@Query("q") String queryString);

    @GET("/nearby")
    Call<ActivityItemCollectionDao> loadNearbyActivities(@Query("lat") double lat,
                                                         @Query("lng") double lng);

    @GET("/nearby")
    Call<ActivityItemCollectionDao> loadNearbyActivities(@Query("u") String user,
                                                         @Query("lat") double lat,
                                                         @Query("lng") double lng);



}
