package com.zeal.retrofitdemo11;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 10:43
 * @描述 ${TODO}
 *
 *
 */

public interface UserInterface {

    @GET("/user/login")
    Call<UserBean> login(@Query("email") String email, @Query("password") String password);


    //多个参数是相同名字的情况
    //http://www.baidu.com/user/tasks/id=1&id=2&id=3
    @GET("/user/tasks")
    Call<UserBean> getTasks(@Query("id") List<Long> ids);
}
