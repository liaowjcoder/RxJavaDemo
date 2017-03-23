package com.zeal.retrofitdemo10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/*
先前已经提到怎么给请求添加header，但这只是单一header的添加，若是一次需要添加多个的时候
该怎么实现呢？
@HeaderMap注解标记
代码实例：
@POST("/user/login")
@FormUrlEncoded
Call<UserBean> login(@FieldMap Map<String,String> params, @HeaderMap Map<String, String> headers);
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
        Map<String, String> params = new HashMap<>();
        params.put("email", "liaoxy@36.cn");
        params.put("password", "aaaaaa");

        Map<String, String> headers = new HashMap<>();
        headers.put("from", "android");
        headers.put("version", "1");
        Call<UserBean> call = service.login(params, headers);
        Response<UserBean> response = call.execute();
        if (response.isSuccessful() && response.body().getCode().equals("200")) {
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
        }
    }

}
