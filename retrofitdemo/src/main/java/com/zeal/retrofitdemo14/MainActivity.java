package com.zeal.retrofitdemo14;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zeal.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Cancel Requests
测试的取消和失败
call.cancel();
当调用了Call.cancel()方法之后，会将该 request 请求归类为失败的请求，
并且会去调用 onFail() 的回调方法。

没有网络连接时，或者数据在网络传输过程中出实现问题。

在 fail 的回调中应该去调用 Call.isCanceled() 判断当前请求是否是被取消的。

TODO 1.若是需要取消一个 Activity 的全部 request，那么可以调用 Okhttp#cancelAll()
TODO 2.若是返回的是一个 Observable 对象，那该怎么取消请求？


 */
public class MainActivity extends AppCompatActivity {
    private List<Call> mCallList = new ArrayList<>();

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

        DemoInterface service = ServiceGenerator.createService(DemoInterface.class);
        Call<ResponseBody> call = service.getTextFromServer("zhangsan", "123");
        mCallList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (call.isCanceled()) {
                    //该请求是被取消的
                } else {
                    //其他原因 例如没有网络连接异常等
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消网络请求
        for (int i = 0; i < mCallList.size(); i++) {
            Call call = mCallList.get(i);
            call.cancel();
        }

    }
}
