package cuexpo.chulaexpo.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import cuexpo.chulaexpo.manager.http.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James on 12/25/2016.
 */
public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;
    private ApiService service;

    private HttpManager()
    {
        mContext = Contextor.getInstance().getContext();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://nuuneoi.com/courses/500px/")
                .baseUrl("http://192.168.43.134:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public ApiService getService() {
        return service;
    }
}
