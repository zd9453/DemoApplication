package com.example.zd.mycontentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zd.mycontentprovider.annitioon.AnnotationUtils;
import com.example.zd.mycontentprovider.annitioon.FBean;
import com.example.zd.mycontentprovider.annitioon.ProgressBean;
import com.example.zd.mycontentprovider.annitioon.TestAnnotation;
import com.example.zd.mycontentprovider.view.ViewTest;
import com.example.zd.mycontentprovider.view.WrapContentTextView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "class information";
    private ViewTest view;

    @TestAnnotation()
    int i;
    private WrapContentTextView textText;
    private ProgressBar progressBar;
    private MHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(1000);
//        mHandler = new MHandler(this);

        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                message.recycle();
                return false;
            }
        });

        textText = (WrapContentTextView) findViewById(R.id.tv_test);
//        textText.setWrapContent(true);
        textText.setText("好的的一个家，哎我大中华");

        textText.setOnClickListener(this);

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
        switch (view.getId()) {
            case R.id.jump:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case R.id.tv_test:
                textText.setWrapContent(!textText.getWrapContent());
//                new MThread(this).start();
                long l = System.currentTimeMillis();
                Log.d(TAG, "run: --------" + l);

                long l1 = SystemClock.currentThreadTimeMillis();
                Log.d(TAG, "onClick: -----" + l1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        for (int i = 0; i < 20; i++) {
                            Message message = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bean", new ProgressBean(i, "this is time"));
                            message.setData(bundle);

                            if (mHandler == null) {
                                mHandler = new MHandler(MainActivity.this);
                            }

                            mHandler.sendMessage(message);
                            message.recycle();

                            Looper.loop();
                            SystemClock.sleep(1000);

//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }).start();
                break;
            case R.id.view_test:
                getInformation();
                break;
        }
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

    private static class MThread extends Thread {

        private WeakReference<MainActivity> weakReference;

        public MThread(MainActivity activity) {
            this.weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void run() {
            MainActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                for (int i = 0; i < 60; i++) {
                    mainActivity.progressBar.setProgress(i * 1000 / 60);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class MHandler extends Handler {

        private WeakReference<MainActivity> weakReference;

        MHandler(MainActivity mainActivity) {
            this.weakReference = new WeakReference<MainActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            Log.d(TAG, "handleMessage: ------------" + what);

            Object obj = msg.obj;
            if (obj instanceof Integer) {
                Log.d(TAG, "handleMessage: -----------" + ((Integer) obj));
            }

            Bundle data = msg.getData();
            if (data != null) {
                Serializable bean = data.getSerializable("bean");
                if (bean != null && bean instanceof ProgressBean) {
                    int time = ((ProgressBean) bean).getTime();
                    String str = ((ProgressBean) bean).getStr();

                    Log.d(TAG, "handleMessage: ------ time: " + time + "  str: " + str);
                }
            }

            MainActivity activity = weakReference.get();
            if (activity != null) {
                activity.progressBar.setProgress(activity.progressBar.getProgress() + 1000 / 20);

                if (activity.progressBar.getProgress() == 1000) {
                    activity.progressBar.setProgress(0);
                }
            }
        }
    }
}
