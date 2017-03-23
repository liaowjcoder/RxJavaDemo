package com.zeal.retrofitdemo14;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/24 14:29
 * @描述 ${TODO}
 *
 *
 */

public class ServiceGenerator {

    private static String apiBaseUrl = "http://app.carjob.com.cn/";
    //记录日志
    /*
    注意：
        不要多次去添加 HttpLoggingInterceptor ，okhttpClient.interceptors().contains(logging)去判断
        不要每次在createService中retrofit = builder.build();
     */
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel
            (HttpLoggingInterceptor.Level.BODY);

    //统一添加自定义header不成功
    private static Interceptor commonIntercept = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //header():表示若先前存在同一样的key的header将会被覆盖
            //addHeader：表示若是先前存在同一样的key的header也不会有关系，不会将其覆盖
            Request request = chain.request().newBuilder().addHeader("Cache-Control", "no-cache")
                    .header("Cache-Control", "no-store").
                    build();

            return chain.proceed(request);
        }
    };


    /*
    每一个请求都需要带一个请求参数：platform=Android
    这样服务器就可以辨别该接口是由Android调用的，而不是iOS调用的。
     */
    private static Interceptor commonQueryParamsInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();

            HttpUrl httpUrl = original.url().newBuilder().addQueryParameter("platform", "Android").build();

            Request.Builder requestBuilder = original.newBuilder().url(httpUrl);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };
    private static OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder().addInterceptor
            (commonIntercept).addInterceptor(logging).addInterceptor(commonQueryParamsInterceptor);

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
        //        if (! okhttpClient.interceptors().contains(commonIntercept)) {
        //            okhttpClient.addInterceptor(commonIntercept);
        //            retrofitBuilder.client(okhttpClient.build());
        //            retrofit = retrofitBuilder.client(okhttpClient.build()).build();
        //        }
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
