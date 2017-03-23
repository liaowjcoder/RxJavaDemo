package com.zeal.retrofitdemo13;

import android.os.Build;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 21:11
 * @描述 ${TODO} 
 */
public class Feedback {


    public String osName = "Android";
    public String osVersion = Build.VERSION.SDK_INT + "";
    public String message;


    public Feedback(String message) {
        this.message = message;
    }
}
