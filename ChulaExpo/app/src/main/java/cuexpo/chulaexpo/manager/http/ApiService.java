package cuexpo.chulaexpo.manager.http;

import org.json.JSONObject;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
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
    @GET("/api/zones")
    Call<ZoneDao> loadZoneList();

    @GET("/api/zones/{zid}")
    Call<ZoneResult>  loadZoneById(@Path("zid") String zid);
}
