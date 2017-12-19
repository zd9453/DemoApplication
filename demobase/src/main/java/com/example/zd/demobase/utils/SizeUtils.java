package com.example.zd.demobase.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * 尺寸工具类
 * Created by zhangdong on 2017/12/4 0004.
 */

public class SizeUtils {
    /**
     * dp to px
     *
     * @param dp .
     * @return .
     */
    public static float dpToPx(float dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return dp * displayMetrics.density;
    }

    /**
     * px to dp
     *
     * @param px .
     * @return .
     */
    public static float pxToDp(float px) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return px / displayMetrics.density;
    }

    /**
     * 获取activity的根布局
     *
     * @param activity .
     * @return 此activity的最外层布局
     */
    public static View getRootView(Activity activity) {
        return ((ViewGroup) activity.getWindow().getDecorView()
                .findViewById(android.R.id.content))
                .getChildAt(0);
    }
}
