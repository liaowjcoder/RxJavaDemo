package com.zeal.retrofitdemo6;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/24 14:29
 * @描述 ${TODO}
 *
 * 不要将BASE_URL的硬编码为final类型。
 *
 */

public class ServiceGenerator {

    private static String apiBaseUrl = "https://api.github.com/";
    //记录日志
    /*
    注意：
        不要多次去添加 HttpLoggingInterceptor ，okhttpClient.interceptors().contains(logging)去判断
        不要每次在createService中retrofit = builder.build();
     */
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel
            (HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder().addInterceptor
            (logging);

    private static Retrofit.Builder retrofitBuilder =//
            new Retrofit.Builder()//
                    .baseUrl(apiBaseUrl)//
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.client(okhttpClient.build()).build();


    public static <T> T createService(Class<T> serviceClass) {
        if (! okhttpClient.interceptors().contains(logging)) {
            okhttpClient.addInterceptor(logging);
            retrofitBuilder.client(okhttpClient.build());
            retrofit = retrofitBuilder.client(okhttpClient.build()).build();
        }

        return retrofit.create(serviceClass);
    }

    /**
     * 动态地修改baseurl的地址
     */
    public static void changeApiBaseUrl(String newBaseUrl) {
        apiBaseUrl = newBaseUrl;

        retrofitBuilder = new Retrofit.Builder().baseUrl(apiBaseUrl)//
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = retrofitBuilder.client(okhttpClient.build()).build();

    }


}
