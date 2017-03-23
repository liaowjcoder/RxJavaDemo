package com.zeal.retrofitdemo8;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 14:06
 * @描述 ${TODO} 
 */

public interface TaskInterface {
    @POST("/user/login")
    Call<UserBean> creatTask(@Body Task task);

}
