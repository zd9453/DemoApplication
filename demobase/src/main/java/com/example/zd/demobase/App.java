package com.example.zd.demobase;

import android.app.Application;
import android.content.Context;

/**
 * Application
 * Created by zhangdong on 2017/12/19 0019.
 */

public class App extends Application {

    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
