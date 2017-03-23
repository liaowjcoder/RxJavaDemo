package com.zeal.retrofitdemo5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zeal.retrofitdemo.R;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * URL Handling, Resolution and Parsing
 * baseurl：应该是以/结尾的
 * # Good Practice
 * base url: https://futurestud.io/api/
 * endpoint: my/endpoint
 * Result:   https://futurestud.io/api/my/endpoint
 *
 * # Bad Practice
 * base url: https://futurestud.io/api
 * endpoint: /my/endpoint
 * Result:   https://futurestud.io/my/endpoint
 *
 * 这两种方式的差别就是base url 是否是以/结尾的，在Bad Practice中的Result中api就被省略了
 * 在域名的路径②path中需要以/结尾，但是①可以不需要
 * ①String API_BASE_URL = "https://api.github.com/";
 * ②String API_BASE_URL = "https://api.github.com/aa";
 * 但是实际上Retrofit在baseurl没有以/结尾，它就会抛出异常
 * 源码：
 *  if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
 *  throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
 * }
 * 前提：baseurl有路径的情况才会出现这种问题，若是只有域名的话，系统会自动补充"/"作为结尾。
 *
 * 在baseurl中retrofit有记忆scheme的能力。也就是说当endpoint想使用先前定义的baseurl中scheme时，
 * 使用//双斜杠拼接endpoint在前面表示。例如Example 4
 * # Example 3 — completely different url
 * base url: http://futurestud.io/api/
 * endpoint: https://api.futurestud.io/
 * Result:   https://api.futurestud.io/
 *
 * # Example 4 — Keep the base url’s scheme
 * base url: https://futurestud.io/api/
 * endpoint: //api.futurestud.io/
 * Result:   https://api.futurestud.io/
 *
 * # Example 5 — Keep the base url’s scheme
 * base url: http://futurestud.io/api/
 * endpoint: //api.github.com
 * Result:   http://api.github.com
 *
 * @GET("//users/{user}/repos")
 * https://users/liaowjcoder/repos
 *
 * 在Example 3 中，当endpoint的地址跟base url的 scheme 完全不一致时，那么在 Result 中
 * endpoint 会将其替换掉。endpoint传入一个完整的路径。
 *
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
        //String API_BASE_URL = "https://api.github.com/aa";

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
