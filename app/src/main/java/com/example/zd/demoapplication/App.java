package com.example.zd.demoapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Application
 * Created by zhangdong on 2017/12/19 0019.
 */

public class App extends Application {

    private static Context mContext;
    private static final String TAG = "App";

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Log.d(TAG, "onCreate: --------------" + BuildConfig.DEBUG);

//        boolean debug = getDebug();
//        Log.d(TAG, "onCreate: -------------- isDebug = " + debug);
    }

    private boolean getDebug() {

        ApplicationInfo info = this.getApplicationInfo();

        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

    }
}
