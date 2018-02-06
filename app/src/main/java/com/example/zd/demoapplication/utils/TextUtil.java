package com.example.zd.demoapplication.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.zd.demoapplication.activity.MediaActivity;

/**
 * @author zhangdong on 2017/12/14 0014.
 */
public class TextUtil {
    /**
     * 设置文字后面跟上图标
     *
     * @param textView   view
     * @param str        显示的文字
     * @param resourceId 后面图标的id
     * @see MediaActivity
     */
    public static void setTextEndWithIcon(Context context, TextView textView, String str, int resourceId) {
        ImageSpan imageSpan = new ImageSpan(context, resourceId);

        String dependStr = str + "  p";

        SpannableString spannableString = new SpannableString(dependStr);

        spannableString.setSpan(imageSpan, dependStr.length() - 1, dependStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

}
