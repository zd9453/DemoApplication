package com.example.zd.demoapplication.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.view.ZdTimerView;
import com.example.zd.demobase.utils.SizeUtils;

public class CreateViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "CreateViewActivity";
    private ZdTimerView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        testView = (ZdTimerView) findViewById(R.id.test_view);
        testView.setOnClickListener(this);


        View dicView = SizeUtils.getRootView(this);
        Log.d(TAG, "onCreate: --------" + dicView.getClass().getName());
    }

    public void moveTo(View view) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(testView, "translationX", 100);
        translationX.setDuration(3000);
        translationX.start();

//        testView.setInfo("测试显示汉字折什么效果");

        boolean b = SizeUtils.viewClickSelf(testView);
        Log.d(TAG, "moveTo:  ---------- " + b);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ----------------");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
