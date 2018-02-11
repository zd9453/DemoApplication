package com.example.zd.mycontentprovider.annitioon;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * use to do
 *
 * @author zhangdong on 2018/2/9 0009.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class AnnotationUtils {

    private String TAG = "AnnotationUtils";
    private static WeakReference<Object> objectWeakReference;

    private AnnotationUtils() {
    }

    private static volatile AnnotationUtils instence = null;

    public static AnnotationUtils newInstance(Object cla) {
        if (instence == null) {
            synchronized (AnnotationUtils.class) {
                if (instence == null) {
                    instence = new AnnotationUtils();
                    objectWeakReference = new WeakReference<Object>(cla);
                }
            }
        }
        return instence;
    }

    public void getI() throws IllegalAccessException, InstantiationException {

        Class<FBean> fBeanClass = FBean.class;

        FBean instance = fBeanClass.newInstance();

        //得到声明的字段
        Field[] field = fBeanClass.getDeclaredFields();
        if (field != null) {
            for (Field mField : field) {
                Log.d(TAG, "getI: ------- mField : " + mField.getName());

                Class<?> type = mField.getType();

                String name = type.getName();
                Log.d(TAG, "getI: ---------- type : " + name);

                TestAnnotation annotation = mField.getAnnotation(TestAnnotation.class);
                if (annotation != null) {

                    mField.setAccessible(true);
                    mField.set(instance, annotation.tag());
                    mField.setAccessible(false);

                    Log.d(TAG, "getI: ------- toString : " + instance.toString());

                    int tag = annotation.tag();
                    Log.d(TAG, "getI: ----------- tag : " + tag + "  value : " /*+ annotation.value()*/);
                }
            }
        }
    }

    public void getActi() {
        Class<?> aClass = null;
        if (objectWeakReference != null) {
            aClass = objectWeakReference.get().getClass();
        }

        Field[] declaredFields = aClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            String name = declaredFields[i].getName();
            Log.d(TAG, "getActi: --------------" + name);
        }
    }
}
