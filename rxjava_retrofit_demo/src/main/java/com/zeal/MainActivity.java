package com.zeal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_get_movie);
        mTextView = (TextView) findViewById(R.id.text_desc);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                getMovieByRetrofit();
                getMovieByRxJava();
            }
        });

    }

    private void getMovieByRxJava() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //这样一来我们定义的service返回值就不在是一个Call了，而是一个Observable
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)//.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory
                        (RxJava2CallAdapterFactory.create())
                .build();


        MovieApiService movieApiService = retrofit.create(MovieApiService.class);


        movieApiService.getMovieByRxJava(0, 10).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Observer<MovieEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieEntity value) {
                mTextView.setText(value.toString());
            }

            @Override
            public void onError(Throwable e) {
                mTextView.setText(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

//    private void getMovieByRetrofit() {
//
//        String baseUrl = "https://api.douban.com/v2/movie/";
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create()).build();
//
//
//        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
//
//        Call<MovieEntity> movie = movieApiService.getMovie(0, 10);
//
//        movie.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                mTextView.setText(response.message());
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                mTextView.setText(t.getMessage());
//            }
//        });
//    }
}
