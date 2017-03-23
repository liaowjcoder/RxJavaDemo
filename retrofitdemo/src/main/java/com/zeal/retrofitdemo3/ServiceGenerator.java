package com.zeal.retrofitdemo3;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/24 14:29
 * @描述 ${TODO} 
 */

public class ServiceGenerator {

    private static final String BSER_URL = "https://api.github.com/";

    private static OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder =//
            new Retrofit.Builder()//
                    .baseUrl(BSER_URL)//
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.client(okhttpClient.build()).build();


    public static <T> T createService(Class<T> serviceClass) {
        if (! okhttpClient.interceptors().contains(logging)) {
            okhttpClient.addInterceptor(logging);
            retrofitBuilder.client(okhttpClient.build());
            retrofit = retrofitBuilder.build();
        }

        return retrofit.create(serviceClass);
    }

    //记录日志
    /*
    注意：
        不要多次去添加 HttpLoggingInterceptor ，okhttpClient.interceptors().contains(logging)去判断
        不要每次在createService中retrofit = builder.build();
     */
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel
            (HttpLoggingInterceptor.Level.BODY);


}
