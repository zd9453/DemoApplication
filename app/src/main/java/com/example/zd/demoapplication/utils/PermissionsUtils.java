package com.example.zd.demoapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;

/**
 * 处理一些权限检查
 * Created by zhangdong on 2017/12/28 0028.
 */

public class PermissionsUtils {

    private static final String TAG = "PermissionsUtils";
    private static final String URI_HEAD = "package:";
    private final Context mContext;

    public PermissionsUtils(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public boolean check(String... permission) {

        for (String p : permission) {
            if (!check(p)) {
                return false;
            }
        }
        return true;
    }

    private boolean check(String permission) {

        int checkSelfPermission = ActivityCompat.checkSelfPermission(mContext, permission);

        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 打开设置权限页面
     *
     * @param requestCode 请求码，用于在设置后回调，再次检查权限授权情况
     */
    public void startActivitySetting(Activity activity, int requestCode) {
//        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,跳手机的应用设置页
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));

//        设置此标志activity将不添加到回退栈（backStack）,离开页面后无法返回
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        如果设置，新activity不保存在最近启动的activity列表中。
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//            startActivity(intent);

        activity.startActivityForResult(intent, requestCode);
    }

    public void getPath() {
        //获取外部存储设备的当前状态，是否存在，是否可读写
        String state = Environment.getExternalStorageState();
        Log.d(TAG, "getPath: -------getExternalStorageState----" + state);

        Log.d(TAG, "getPath: 00--------isExternalStorageRemovable--" + Environment.isExternalStorageRemovable());

        Log.d(TAG, "getPath: ---------isExternalStorageEmulated-" + Environment.isExternalStorageEmulated());

        File file = Environment.getDownloadCacheDirectory();
        Log.d(TAG, "getPath: ------getDownloadCacheDirectory---" + file.getAbsolutePath());

        //获取应用内部缓存目录
        File cacheDir = mContext.getCacheDir();
        Log.d(TAG, "getPath: -------------getCacheDir--" + cacheDir.getAbsolutePath());

        File externalCacheDir = mContext.getExternalCacheDir();
        Log.d(TAG, "getPath: ------------getExternalCacheDir -   " + externalCacheDir.getAbsolutePath() + "   ---  " + externalCacheDir.getPath());




        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            Log.d(TAG, "getPath: ----------" + "MEDIA_MOUNTED");
        } else {
            Log.d(TAG, "getPath: -----------------");
        }
    }
}
