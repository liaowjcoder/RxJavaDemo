package com.zeal.retrofitdemo13;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 21:10
 * @描述 ${TODO} 
 */

public interface FeedbackService {

    @FormUrlEncoded
    @POST("RetrofitDemp/feedback")
    Call<ResponseBody> feedback(@Field("osName") String osName, @Field("osVersion") String
            osVersion, @Field("message") String message);


    @FormUrlEncoded
    @POST("RetrofitDemp/feedback")
    Call<ResponseBody> feedback2(@Field("feedback") Feedback feedback);
}
