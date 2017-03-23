package com.zeal.retrofitdemo6;

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


        //运行时期修改baseurl
        ServiceGenerator.changeApiBaseUrl("http://app.carjob.com.cn/");
        UserInterface service = ServiceGenerator.createService(UserInterface.class);
        //Call<UserBean> userBeanCall = service.login("tanggh@36.cn", "aaaaaa");
        Map<String, String> params = new HashMap<>();
        params.put("email", "tanggh@36.cn");
        params.put("password", "aaaaaa");
        Call<UserBean> userBeanCall = service.login(params);
        final Response<UserBean> response = userBeanCall.execute();
        if (response.isSuccessful()) {
            final UserBean userBean = response.body();
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

    }

}
