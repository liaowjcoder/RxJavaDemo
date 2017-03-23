package com.zeal.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);

    }

    public void click(View view) {
        request();
    }

    //private Disposable mLoginDisposable;

    private void request() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().build();

                Retrofit retrofit = new Retrofit.Builder().client(client).
                        baseUrl("http://app.carjob.com.cn/")//
                        //.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

                LoginService loginService = retrofit.create(LoginService.class);

                Map<String, String> params = new HashMap<>();
                params.put("email", "liaoxy@36.cn");
                params.put("password", "aaaaaa");
                Call<UserBean> call = loginService.login();
                //                Observable<UserBean> observable = loginService.login(params);
                try {
                    Response<UserBean> response = call.execute();
                    if (response.isSuccessful()) {
                        Log.e("zeal", "success");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //                observable.subscribeOn(Schedulers.io())
                //                        .subscribe(new Observer<UserBean>() {
                //                            @Override
                //                            public void onSubscribe(Disposable d) {
                //                                mLoginDisposable = d;
                //                            }
                //
                //                            @Override
                //                            public void onNext(UserBean userBean) {
                //                                if (userBean != null) {
                //                                    //mText.setText(userBean.toString());
                //                                    Log.e("zeal", "success");
                //                                }
                //                            }
                //
                //                            @Override
                //                            public void onError(Throwable e) {
                //                               // mText.setText(e.toString());
                //                            }
                //
                //                            @Override
                //                            public void onComplete() {
                //                                //mText.setText(mText.getText().toString());
                //                            }
                //                        });
                //            }
                //        }).start();

            }
        }).start();

    }
}
