package com.example.zd.demobase.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

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
}
