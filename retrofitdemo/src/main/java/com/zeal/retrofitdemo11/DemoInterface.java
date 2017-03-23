package com.zeal.retrofitdemo11;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 18:59
 * @描述 ${TODO} 
 */

public interface DemoInterface {

    @GET("/RetrofitDemp/aaa")
    Call<ResponseBody> getTextFromServer(@Query("username") String username, @Query("password")
            String password);


    @FormUrlEncoded
    @POST("/RetrofitDemp/aaa")
    Call<User> login(@Field(value = "username") String username, @Field(value =
            "password") String password);

}
