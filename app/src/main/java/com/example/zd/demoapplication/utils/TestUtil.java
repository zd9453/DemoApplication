package com.example.zd.demoapplication.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解测试
 * Created by zhangdong on 2017/12/11 0011.
 */

public class TestUtil {
    public static final String TYPE_ONE = "1";
    public static final String TYPE_TWO = "2";
    public static final String TYPE_THREE = "3";
    public static final String TYPE_FOUR = "4";

    /**
     * 声明自己的注解
     */
    @Target(ElementType.PARAMETER)                              //注解用在什么地方
    @Retention(RetentionPolicy.SOURCE)                          //注解什么时候失效
    @StringDef({TYPE_ONE, TYPE_TWO, TYPE_THREE, TYPE_FOUR}) //被该注解注解的默认的参数
    @interface TestUtilDft {

    }

    /**
     * 自定义的方法
     *
     * @param shareType 被注解注解的参数，只能穿注解提前声明好的参数
     * @param title     .
     * @param content   .
     */
    public static void share(@TestUtilDft String shareType, String title, String content) {

    }

    /**
     * 测试注解的方法
     */
    private void test() {
        share(TYPE_ONE, null, null);
        share(TYPE_TWO, null, null);
        share(TYPE_THREE, null, null);
        share(TYPE_FOUR, null, null);
    }
}
