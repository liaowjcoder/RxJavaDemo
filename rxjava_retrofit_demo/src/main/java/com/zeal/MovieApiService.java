package com.zeal;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @作者 廖伟健
 * @创建时间 2017/3/23 11:17
 * @描述 ${TODO} 
 */

public interface MovieApiService {


//    @GET("top250")
//    Call<MovieEntity> getMovie(@Query("start") int start, @Query("count") int count);
    //rxjava 版本
    @GET("top250")
    Observable<MovieEntity> getMovieByRxJava(@Query("start") int start, @Query("count") int count);

}
