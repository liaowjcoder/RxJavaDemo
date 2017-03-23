package com.zeal.retrofitdemo2;

import com.zeal.retrofitdemo.GitHubRepo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/24 11:10
 * @描述 ${TODO} 
 */
/*
HTTP Method
在该接口中，我们使用了注解的方式去描述 API 和请求
Retrofit为每一种请求方式都定义了注解，只需要简单的使用@GET, @POST, @PUT, @DELETE, @PATCH or @HEAD.等就表示请求方式
注解中的参数表示需要请求的相对地址，此外还需要提供一个基础基地BASE_URL，Retrofit 在请求会去拼接，这种方式就可以定义一次基地址，
并且可以动态的修改基地址。【readMore：https://futurestud
.io/tutorials/retrofit-2-url-handling-resolution-and-parsing】

Function Name & Return Type
分析：在GithubClient接口中定义的方法
方法名：getUserInfo，这个方法名称可以是任意的，只要能见名知意即可。
方法返回值:返回值使用 Call<>将其包裹起来。
    1.若是希望请求服务器之后返回一个特定的 Model 对象，那么可以使用Call<Model> 即可。
    2.如果不指定类型，可以使用Call<ResponseBody>返回一个ResponseBody类型的对象
    3.同样若是不关心服务器返回的类型，可以指定Call<Void>即可。
方法参数：
REST APIS是建立在动态的的URLs基础之上的，你可以动态地去替换部分URL，Retrofit去访问资源时
举个栗子：
    http://www.zeal.com/page/1表示可以去访问该地址下的第一页的数据
    当然这个1是可以动态改变的，可以是2，3，4都行。
基于上面的动态替换，Retrofit提供了一个简单的方式去替换这个值：@PATH
@GET("/users/{user}/repos")
Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
{user}表示需要被动态替换的值，这个值就在@Path("user")中定义。

一般在 get 请求中可以使用 Query 拼接对应的参数去查询对应的资源，多个参数使用逗号隔开
@Query("参数的key")，如果参数中传递了null，那么Retrofit将会忽略这个参数。

在这几个例子中可以学习到了：
    URL的配置，请求方式（get，post等），方法返回类型，@Path动态替换URL和@Query拼接查询参数
 */
public interface GithubClient {


    @GET("/user/info")
    Call<UserInfo> getUserInfo();

    @PUT("/user/info")
    Call<UserInfo> updateUserInfo(@Body UserInfo userInfo);

    @DELETE("/user")
    Call<Void> deleteUser();


    //使用全地址
    @GET("http://xxxx/user/info")
    Call<UserInfo> getUserInfo2();

    /*-------------返回值----------------*/

    //客户端希望接受一个ResponseBody
    @GET("/user/info")
    Call<ResponseBody> getUserInfo3();

    //客户端希望接受一个UserInfo
    @GET("http://xxxx/user/info")
    Call<UserInfo> getUserInfo4();

    //客户端不关心服务端的返回数据
    @GET("http://xxxx/user/info")
    Call<Void> getUserInfo5();

    /*-------------Path方法参数----------------*/
    //@PATH指定需要被动态替换的URL片段
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);

    /*-------------Query方法参数----------------*/
    //一般在 get 请求中可以使用 Query 拼接对应的参数去查询对应的资源，多个参数使用逗号隔开
    //@Query("参数的key")，如果参数中传递了null，那么Retrofit将会忽略这个参数。
    @GET("/user/info")
    Call<List<UserInfo>> getTutorials(@Query("userId") long userId,
                                      @Query("isOnline") Integer isOnline);
}
