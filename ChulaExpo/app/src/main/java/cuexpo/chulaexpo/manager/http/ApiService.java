package cuexpo.chulaexpo.manager.http;

import org.json.JSONObject;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.ActivityItemDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.dao.ReserveDao;
import cuexpo.chulaexpo.dao.RoundDao;

import cuexpo.chulaexpo.dao.ZoneDao;
import cuexpo.chulaexpo.dao.ZoneResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dragonnight on 26/12/2559.
 */

public interface ApiService {
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadActivityList();
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadIncomingActivityOnStage(@Query("zone") String zone,
                                                            @Query("end") JSONObject end,
                                                                @Query("sort") String sort,
                                                                @Query("limit") int limit);
    @GET("/api/activities/{aid}")
    Call<ActivityItemDao> loadActivityItem(@Path("aid") String aid);
    @GET("/api/zones")
    Call<ZoneDao> loadZoneList();
    Call<ZoneDao> loadZoneByType(@Query("type") String type);
    @GET("/api/zones/:zid")
    Call<ZoneResult>  loadZoneById(@Path("zid") String zid);
    @GET("/api/activities/{aid}/rounds")
    Call<RoundDao>  loadRoundsById(@Path("aid") String aid, @Query("sort") String sort);
    @GET("api/activities")
    Call<ActivityItemCollectionDao> loadActivityByZone
            (@Query("zone") String zone, @Query("sort") String sort, @Query("start") JSONObject range);
    /*
    @GET("api/me/reserved")
    Call<ActivityItemCollectionDao> getReservedActivity();*/

    @POST("/api/activities/{aid}/rounds/{rid}/reserve")
    Call<ReserveDao> reserveSelectedRound(@Path("aid") String aid, @Path("rid") String rid);

}
