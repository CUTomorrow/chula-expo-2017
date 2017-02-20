package cuexpo.chulaexpo.manager.http;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.RoundDao;
import cuexpo.chulaexpo.dao.RoundResult;
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
    @GET("/api/zones")
    Call<ZoneDao> loadZoneList();
    @GET("/api/zones/:zid")
    Call<ZoneResult>  loadZoneById(@Path("zid") String zid);
    @GET("/api/activities/{aid}/rounds")
    Call<RoundDao>  loadRoundsById(@Path("aid") String aid, @Query("limit") int limit);
    /*@POST("/api/activities/:aid/rounds/:rid/reserve")
    Call<>*/
}
