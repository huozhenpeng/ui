package com.example.huozhenpeng.interviewui;

import android.app.Application;

/**
 * 作者 huozhenpeng
 * 日期 2018/7/22
 * 邮箱 huohacker@sina.com
 */

public class MyApplication extends Application {

    public static  MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static MyApplication getInstance()
    {
        return instance;
    }
}
