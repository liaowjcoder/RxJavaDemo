package com.zeal.retrofitdemo9;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 10:43
 * @描述 ${TODO}
 *
 * POST请求需要加上@FormUrlEncoded标注
 * 为什么？
 *
 */

public interface UserInterface {

    @Headers("aaaa: bbbb")
    @POST("/user/login")
    @FormUrlEncoded
    Call<UserBean> login(@Header("from")String  from,@FieldMap Map<String, String> params);

}
