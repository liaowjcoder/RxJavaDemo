package com.zeal.retrofitdemo8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;
import com.zeal.retrofitdemo6.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Retrofit可以使用@Body注解将一个对象作为请求体进行提交

这种方式需要服务器支持，因为最终是将对应转化为一个json格式的数据发送给服务器端。

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

    public void request() throws IOException {

        Task task = new Task("tanggh@36.cn", "aaaaaa");
        ServiceGenerator.changeApiBaseUrl("http://app.carjob.com.cn/");
        TaskInterface taskInterface = ServiceGenerator.createService(TaskInterface.class);
        Call<UserBean> taskCall = taskInterface.creatTask(task);
        taskCall.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
        /*Response<UserBean> taskResponse = taskCall.execute();

        if (taskResponse.isSuccessful()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

    }

}
