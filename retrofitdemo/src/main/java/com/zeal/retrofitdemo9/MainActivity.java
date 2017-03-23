package com.zeal.retrofitdemo9;

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

1. 静态添加
    @Headers("aaaa: bbbb")
    @POST("/user/login")
    @FormUrlEncoded
    Call<UserBean> login( @FieldMap Map<String, String> params);

2. 动态添加
    @POST("/user/login")
    @FormUrlEncoded
    Call<UserBean> login(@Header("from") String from, @FieldMap Map<String, String> params);

Retrofit 统一添加header 拦截器实现
.header(key, value): overrides the respective header key with value if there is already an
existing header identified by key
.addHeader(key, value): adds the respective header key and value even if there is an existing
header field with the same key
header():表示若先前存在同一样的key的header将会被覆盖
addHeader：表示若是先前存在同一样的key的header也不会有关系，不会将其覆盖
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

        UserInterface service = ServiceGenerator2.createService(UserInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("email", "liaoxy@36.cn");
        params.put("password", "aaaaaa");
        Call<UserBean> call = service.login("android", params);
        Response<UserBean> response = call.execute();
        if (response.isSuccessful() && response.body().getCode().equals("200")) {
            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
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
