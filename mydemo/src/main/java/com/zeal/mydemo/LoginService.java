package com.zeal.mydemo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @作者 廖伟健
 * @创建时间 2017/3/6 16:40
 * @描述 ${TODO} 
 */
public interface LoginService {
    @GET("/user/login")
//    Observable<UserBean> login(@FieldMap Map<String, String> params);
    Call<UserBean> login(/*@FieldMap Map<String, String> params*/);

}
