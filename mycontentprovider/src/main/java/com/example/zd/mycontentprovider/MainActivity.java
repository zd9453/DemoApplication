package com.example.zd.mycontentprovider;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zd.mycontentprovider.annitioon.AnnotationUtils;
import com.example.zd.mycontentprovider.annitioon.FBean;
import com.example.zd.mycontentprovider.annitioon.TestAnnotation;
import com.example.zd.mycontentprovider.view.ViewTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "class information";
    private ViewTest view;

    @TestAnnotation()
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = new TabLayout(this);

        view = (ViewTest) findViewById(R.id.view_test);
        view.setOnClickListener(this);

        AnnotationUtils utils = AnnotationUtils.newInstance(this);

        try {
            utils.getI();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

//        utils.getActi();
//        FBean fBean = new FBean();
//        Log.d(TAG, "onCreate: --------- " + fBean.toString());


        try {
            Class<?> aClass = Class.forName(FBean.class.getName());

            FBean bean = ((FBean) aClass.newInstance());


        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
//        view.setSelected(!view.isSelected());

        getInformation();

    }

    private void getInformation() {
        Class<?> fBean = FBean.class;

        //获取除私有属性外的其他属性
        Field[] fBeanFields = fBean.getFields();
        for (Field m : fBeanFields) {
            Log.d(TAG, "onClick: ---------fBean.getFields: " + m.getName());
        }

        //获取成员变量
        Field[] fields = fBean.getDeclaredFields();
        for (Field mField : fields) {
            Log.d(TAG, "onCreate: ---------- Field : " + mField.getName() + " "
                    + mField.getType().getSimpleName() + " "
                    + Modifier.toString(mField.getModifiers()));
        }

        //获取所有的共有方法，包括父类的
        Method[] fBeanMethods = fBean.getMethods();
        for (Method m : fBeanMethods) {
            Log.d(TAG, "onClick: ------ fBean.getMethods: " + m.getName());
        }

        //获取自己定义的所有方法
        Method[] methods = fBean.getDeclaredMethods();
        for (Method mMethod : methods) {
            Log.d(TAG, "onCreate: ---------- Method : " + mMethod.getName());
        }

        //获取所有的构造方法
        Constructor<?>[] constructors = fBean.getConstructors();
        for (Constructor mConstructor : constructors) {
            Log.d(TAG, "onCreate: -------- Constructor : " + mConstructor.getName());
        }
    }
}
