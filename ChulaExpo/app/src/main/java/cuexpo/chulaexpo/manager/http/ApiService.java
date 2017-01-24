package cuexpo.chulaexpo.manager.http;

import cuexpo.chulaexpo.dao.ActivityItemCollectionDao;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by dragonnight on 26/12/2559.
 */

public interface ApiService {
    @POST("")
    Call<ActivityItemCollectionDao> loadPhotoList();
}
