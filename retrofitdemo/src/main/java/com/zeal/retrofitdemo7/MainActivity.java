package com.zeal.retrofitdemo7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/*
在retrofit2中所有的同步都使用Call包裹着。
异步Call.enqueue
同步Call.execute

对于同步请求，需要注意将其放在子线程中去执行，因为它在Android4.0以上的机器会导致
crash现象。


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


        UserInterface service = ServiceGenerator.createService(UserInterface.class);
        //Call<UserBean> userBeanCall = service.login("tanggh@36.cn", "aaaaaa");
        Map<String, String> params = new HashMap<>();
        params.put("email", "tanggh@36.cn");
        params.put("password", "aaaaaa");
        Call<UserBean> userBeanCall = service.login(params);
        //同步操作
        final Response<UserBean> response = userBeanCall.execute();
        //Get Raw HTTP Response
        // 得到 raw response 对象，也就是okhttp中的response对象
        //okhttp3.Response rawResponse = response.raw();
        if (response.isSuccessful()) {
            final UserBean userBean = response.body();//得到真正的实体对象。
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) findViewById(R.id.textview)).setText(//
                            "PerName:" + userBean.getPerName() + "\n" +
                                    "code:" + userBean.getCode());
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //异步操作
        /*userBeanCall.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                //Get Raw HTTP Response
                // 得到 raw response 对象，也就是okhttp中的response对象
                okhttp3.Response rawResponse = response.raw();
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {

            }
        });*/

    }

}
