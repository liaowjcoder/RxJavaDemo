package com.zeal.retrofitdemo7;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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

    //@Field的使用：
    //@FormUrlEncoded
    //@POST("/user/login")
    //Call<UserBean> login(@Field("email") String email, @Field("password") String password);

    //@FieldMap的使用：
    @POST("/user/login")
    @FormUrlEncoded
    Call<UserBean> login(@FieldMap Map<String, String> params);

}
