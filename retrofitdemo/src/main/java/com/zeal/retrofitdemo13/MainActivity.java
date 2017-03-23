package com.zeal.retrofitdemo13;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;
import com.zeal.retrofitdemo12.ServiceGenerator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/*
反馈系统
方式1：很明显这个功能是可以实现的，但是呢，某一些参数是相对静态的，例如osName,osVersion，而在参数中
只有message是动态的，因此能不能让这些相对静态的数据让它自己的实现呢？
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

    public void request() throws Exception {
        feedback("app so good...");

    }


    private void feedback(String message) throws Exception {
        //      方式1
        //        ServiceGenerator.changeApiBaseUrl("http://188.188.0.30:8080/");
        //        FeedbackService service = ServiceGenerator.createService(FeedbackService.class);
        //        Call<ResponseBody> call = service.feedback("Android", Build.VERSION.SDK_INT +
        // "", message);
        //        Response<ResponseBody> response = call.execute();
        //        if (response.isSuccessful()) {
        //            final String text = response.body().string();
        //            runOnUiThread(new Runnable() {
        //                @Override
        //                public void run() {
        //
        //                    ((TextView) findViewById(R.id.textview)).setText(//
        //                            "返回内容：" + text);
        //
        //                }
        //            });
        //        } else {
        //            runOnUiThread(new Runnable() {
        //                @Override
        //                public void run() {
        //                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
        //                }
        //            });
        //        }
        //方式2
        ServiceGenerator.changeApiBaseUrl("http://188.188.0.30:8080/");
        FeedbackService service = ServiceGenerator.createService(FeedbackService.class);

        Feedback feedback = new Feedback(message);
        Call<ResponseBody> call = service.feedback2(feedback);
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
