package com.zeal.retrofitdemo12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/*
在get请求使用@Query可以给一个请求添加请求参数，若是需要给全部请求都添加一个一样的请求
参数这样做就不合理了，不过可以使用okhttp 中interceptor帮我们去实现这个功能
代码实例：
Request original = chain.request();
HttpUrl httpUrl = original.url().newBuilder()
.addQueryParameter("common_params", "i " +
        "" + "am dev_liaowj").build();//添加参数
Request.Builder requestBuilder = original.newBuilder().url(httpUrl);
Request request = requestBuilder.build();
return chain.proceed(request);
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


        //测试为每一个请求都添加一个统一的请求参数
        ServiceGenerator.changeApiBaseUrl("http://188.188.0.30:8080/");
        DemoInterface service = ServiceGenerator.createService(DemoInterface.class);
        Call<ResponseBody> call = service.getTextFromServer("zhangsan", "123456");
        Response<ResponseBody> response = call.execute();
        if (response.isSuccessful()) {
            final String text = response.body().string();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ((TextView) findViewById(R.id.textview)).setText(//
                            "返回内容：" + text);

                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}
