
package com.example.processesdemo.http;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * created by taofu on 2018/11/27
 **/
public abstract  class BaseDataService<T> {

    private static final long DEFAULT_TIMEOUT = 20000;
    private static volatile Object mRetrofitService;

    abstract T getService();


    public static Object getService(Class zclas,String baseUrl) {

        if (mRetrofitService == null) {
            synchronized (BaseDataService.class) {
                if (mRetrofitService == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging)
//                            .addInterceptor(new AddHeaderInterceptor())
//                            .addInterceptor(new SaveCookieInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(baseUrl)
                            .build();

                    mRetrofitService = retrofit.create(zclas);
                }
            }
        }
        return mRetrofitService;
    }
}
/**
 * 使用：创建一个接口 WanAndroidService 是app下的 可以参考 package com.base.gyh.baselib.data.remote.retrofit;
 * 注意不是lib下的需要自己在app下创建
 * public class DataService {
 * <p>
 * public static WanAndroidService getService(){
 * return  (WanAndroidService) BaseDataService.getService(WanAndroidService.class);
 * }
 * }
 */
