package com.zeal.retrofitdemo10;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 10:43
 * @描述 ${TODO}
 *
 *
 */

public interface UserInterface {

    @POST("/user/login")
    @FormUrlEncoded
    Call<UserBean> login(@FieldMap Map<String,String> params, @HeaderMap Map<String, String> headers);

}
