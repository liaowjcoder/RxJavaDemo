package com.zeal.retrofitdemo3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zeal.retrofitdemo.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/*
 * Retrofit对象 和 Builder 是所有请求的核心。
 * 利用这两个对象去配置
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
