package com.zeal.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Retrofit2.0
 * 内置了 Okhttp,不需要引入
 * 引入依赖包，配置访问网络权限
 * 1. 定义接口
 * 2. 定义模型
 * 3. 配置Retrofit（Retrofit 1.9 是配置 RetrofitAdapter）
 * 4. 发起请求（异步或者同步）
 *
 * 这种方式给别人看起来，Retroift 的使用也是比较复杂的，但是呢？你应该继续往下学习，你会爱上 Retrofit 的。
 *
 * 本节简单的jieso
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void click(View view) {
        new Thread() {
            @Override
            public void run() {
                try {
                    request();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /*
    在这个请求中，我们对Retrofit的配置都比较少，但是已经可以发起一个网络请求了。

    大部分情况下，访问服务器一般会返回的格式并不是对应的 Model 类型的，可能是 Json 类型或者是 Xml 类型
    Retrofit 提供了转换器 GsonConverterFactory 可以将 json 格式的返回值转化为对应的 Model 类型。
     */
    public void request() throws IOException {
        String API_BASE_URL = "https://api.github.com/";


        //对Okhttp配置
        OkHttpClient.Builder okHttpclient = new OkHttpClient.Builder();

        //对Retrofit配置
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        //retrofit与okhhtp建立关联
        Retrofit retrofit = retrofitBuilder.client(okHttpclient.build()).build();

        //发起网络请求
        final GithubClient githubClient = retrofit.create(GithubClient.class);

        Call<List<GitHubRepo>> call = githubClient.reposForUser("liaowjcoder");

        //call.request().newBuilder().method()

        //同步请求
        Response<List<GitHubRepo>> execute = call.execute();

        //异步请求
        /*call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>>
                    response) {
                Log.e("zeal", "onResponse");
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.e("zeal", "onFailure");
            }
        });*/

        final List<GitHubRepo> gitHubRepoList = execute.body();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < gitHubRepoList.size(); i++) {
                    String s = gitHubRepoList.get(i).getId() + "---" + gitHubRepoList.get(i)
                            .getName();
                    sb.append(s + "\n");
                }

                ((TextView) findViewById(R.id.text)).setText(sb.toString());
            }
        });


    }

}
