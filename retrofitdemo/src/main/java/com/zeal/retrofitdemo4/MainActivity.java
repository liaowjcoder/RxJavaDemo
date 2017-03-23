package com.zeal.retrofitdemo4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zeal.retrofitdemo.R;
import com.zeal.retrofitdemo3.GitHubRepo;
import com.zeal.retrofitdemo3.GithubClient;
import com.zeal.retrofitdemo3.ServiceGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/*
 * Logging ：
 * 大多数开发者都希望在网络请求时发送了什么数据和接受了什么数据
 * HttpLoggingInterceptor
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void request() throws IOException {
        GithubClient service = ServiceGenerator.createService(GithubClient.class);
        Call<List<GitHubRepo>> reposForUser = service.reposForUser("liaowjcoder");
        Response<List<GitHubRepo>> response = reposForUser.execute();

        final List<GitHubRepo> gitHubRepoList = response.body();

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
