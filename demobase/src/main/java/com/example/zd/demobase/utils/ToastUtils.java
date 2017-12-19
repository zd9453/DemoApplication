package com.example.zd.demobase.utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.zd.demobase.App;


/**
 * 吐司工具
 * Created by zhangdong on 2017/12/19 0019.
 */

public class ToastUtils {

    private static final String TAG = "ToastUtils";

    /**
     * 短时间
     *
     * @param str 显示内容
     */
    public static void showShortT(String str) {
        if (!TextUtils.isEmpty(str))
            Toast.makeText(App.getmContext(), str, Toast.LENGTH_SHORT).show();
        else
            Log.d(TAG, "showShortT: ------------ This Toast Is Null Information");
    }

    /**
     * 长时间
     *
     * @param str 显示的内容
     */
    public static void showLongT(String str) {
        if (!TextUtils.isEmpty(str))
            Toast.makeText(App.getmContext(), str, Toast.LENGTH_LONG).show();
        else
            Log.d(TAG, "showLongT: ------------ This Toast Is Null Information");
    }

}
