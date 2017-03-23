package com.zeal.retrofitdemo5;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/24 11:10
 * @描述 ${TODO} 
 */

public interface GithubClient {
    @GET("//users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
