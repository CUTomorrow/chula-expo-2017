package cuexpo.chulaexpo.manager.http;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import cuexpo.chulaexpo.dao.ActivityItemDao;
import cuexpo.chulaexpo.dao.ActivityItemResultDao;
import cuexpo.chulaexpo.dao.ZoneDao;
import cuexpo.chulaexpo.dao.ZoneResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dragonnight on 26/12/2559.
 */

public interface ApiService {
    @GET("/api/activities")
    Call<ActivityItemCollectionDao> loadActivityList();
    @GET("/api/activities/{aid}")
    Call<ActivityItemDao> loadActivityItem(@Path("aid") String aid);
    @GET("/api/zones")
    Call<ZoneDao> loadZoneList();
    @GET("/api/zones/:zid")
    Call<ZoneResult>  loadZoneById(@Path("zid") String zid);
}
