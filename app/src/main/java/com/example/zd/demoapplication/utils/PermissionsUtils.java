package com.example.zd.demoapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by zhangdong on 2017/12/28 0028.
 */

public class PermissionsUtils {

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
}
