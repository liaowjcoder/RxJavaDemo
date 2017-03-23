package com.zeal.retrofitdemo11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zeal.retrofitdemo.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/*
get请求传递参数
@Query注解标识
单一参数的情况
@GET("/user/login")
Call<UserBean> login(@Query("email") String email, @Query("password") String password);

多个参数的名字一样的情况
//http://www.baidu.com/user/tasks/id=1&id=2&id=3
@GET("/user/tasks")
Call<UserBean> getTasks(@Query("id") List<Long> ids);
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

        /*UserInterface service = ServiceGenerator.createService(UserInterface.class);

        Call<UserBean> userBeanCall = service.login("liaoxy@36.cn", "aaaaaa");
        Response<UserBean> response = userBeanCall.execute();
        final UserBean userBean = response.body();
        if (response.isSuccessful() && response.body().getCode().equals("200")) {
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
                    ((TextView) findViewById(R.id.textview)).setText("登录失败");
                }

            });
        }
*/


        //测试GET请求

        //        ServiceGenerator.changeApiBaseUrl("http://188.188.0.30:8080/");
        //        DemoInterface service = ServiceGenerator.createService(DemoInterface.class);
        //        Call<ResponseBody> call = service.getTextFromServer("zhangsan", "123456");
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


        ServiceGenerator.changeApiBaseUrl("http://188.188.0.30:8080/");
        DemoInterface service = ServiceGenerator.createService(DemoInterface.class);
        Call<User> call = service.login("zhangsan张三", "123456");
        Response<User> response = call.execute();
        User user = response.body();
        if (response.isSuccessful()) {
            final String text = user.toString();
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
