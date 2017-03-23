package com.zeal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
                getMovieByRetrofit();
                //getMovieByRxJava();
            }
        });

    }

    private void getMovieByRetrofit() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();


        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieEntity> movie = movieApiService.getMovie(0, 10);

        movie.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                mTextView.setText(response.message());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                mTextView.setText(t.getMessage());
            }
        });


    }

//    private void getMovieByRxJava() {
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
}
