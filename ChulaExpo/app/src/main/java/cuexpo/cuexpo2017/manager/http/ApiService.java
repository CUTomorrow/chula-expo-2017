package cuexpo.cuexpo2017.manager.http;

import org.json.JSONObject;

import cuexpo.cuexpo2017.dao.ActivityItemCollectionDao;
import cuexpo.cuexpo2017.dao.ActivityItemDao;
import cuexpo.cuexpo2017.dao.FacilityDao;
import cuexpo.cuexpo2017.dao.LoginDao;
import cuexpo.cuexpo2017.dao.PlaceItemDao;
import cuexpo.cuexpo2017.dao.ReserveDao;
import cuexpo.cuexpo2017.dao.RoundDao;

import cuexpo.cuexpo2017.dao.UserProfile;
import cuexpo.cuexpo2017.dao.ZoneDao;
import cuexpo.cuexpo2017.dao.ZoneItemDao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dragonnight on 26/12/2559.
 */

public interface ApiService {
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadActivityList(@Query("fields") String fields,
                                                     @Query("limit") int limit,
                                                     @Query("sort") String sort);
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadIncomingActivityOnStage(@Query("zone") String zone,
                                                                @Query("fields") String fields,
                                                                @Query("end") JSONObject end,
                                                                @Query("sort") String sort,
                                                                @Query("limit") int limit);
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadHighlightActivity(@Query("highlight") boolean highlight,
                                                          @Query("fields") String fields,
                                                          @Query("end") JSONObject end,
                                                          @Query("limit") int limit);

    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadActivityByZone(@Query("zone") String zone,
                                                       @Query("start") String range,
                                                       @Query("sort") String sort);

    @GET("/api/activities/{aid}")
    Call<ActivityItemDao> loadActivityItem(@Path("aid") String aid);

    @GET("/api/activities/{aid}/rounds")
    Call<RoundDao> loadRoundsById(@Path("aid") String aid,
                                   @Query("sort") String sort,
                                   @Query("start") JSONObject range);

    @POST("/api/activities/{aid}/rounds/{rid}/reserve")
    Call<ReserveDao> reserveSelectedRound(@Path("aid") String aid,
                                          @Path("rid") String rid);

    @GET("/api/zones")
    Call<ZoneDao> loadZoneList();

    @GET("/api/zones/{zid}")
    Call<ZoneItemDao> loadZoneById(@Path("zid") String zid);

    @GET("/api/me/reserved_rounds")
    Call<RoundDao> getReservedActivity();

    @GET("/api/places/{pid}")
    Call<PlaceItemDao> loadPlaceItem(@Path("pid") String pid);

    @GET("/api/facilities")
    Call<FacilityDao> loadFacilityList();

//    @GET("/api/rooms/{rid}")
//    Call<RoomItemDao> loadRoomItem(@Path("rid") String rid);

    @GET("/auth/facebook/token")
    Call<LoginDao> accessFacebook(@Query("access_token") String accessToken);

    @POST("/api/signup")
    Call<LoginDao> registerUser(@Body UserProfile userProfile);

}
