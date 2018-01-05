package com.example.zd.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zd.demoapplication.activity.ConstraintLayoutActivity;
import com.example.zd.demoapplication.activity.CoordinatorLayoutActivity;
import com.example.zd.demoapplication.activity.CreateViewActivity;
import com.example.zd.demoapplication.activity.FileTestActivity;
import com.example.zd.demoapplication.activity.MediaActivity;
import com.example.zd.demoapplication.activity.NotificationActivity;
import com.example.zd.demoapplication.activity.PickerTestActivity;
import com.example.zd.demoapplication.activity.TimerViewActivity;
import com.example.zd.demoapplication.activity.VideoTest2Activity;
import com.example.zd.demoapplication.activity.VideoTestActivity;

public class MainJumpActivity extends AppCompatActivity {

    private static final String TAG = "MainJumpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jump);
    }

    /**
     * 协调布局
     *
     * @param view
     */
    public void button1(View view) {
        Intent intent = new Intent(this, CoordinatorLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 约束布局
     *
     * @param view
     */
    public void button2(View view) {
        Intent intent = new Intent(this, ConstraintLayoutActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_sc, R.anim.left_out);
    }

    public void button3(View view) {
        Intent intent = new Intent(this, MediaActivity.class);
        startActivity(intent);
    }

    public void button4(View view) {
        Intent intent = new Intent(this, TimerViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ----------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: -----------------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: --------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: --------------------");
    }


    public void button5(View view) {
        Intent intent = new Intent(this, CreateViewActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.in_sc, R.anim.out_sc);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void button6(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void button7(View view) {
        Intent intent = new Intent(this, VideoTestActivity.class);
        startActivity(intent);
    }

    public void button8(View view) {
        Intent intent = new Intent(this, VideoTest2Activity.class);
        startActivity(intent);
    }

    public void button9(View view) {
        startActivity(new Intent(this, FileTestActivity.class));
    }

    public void button10(View view) {
        startActivity(new Intent(this, PickerTestActivity.class));
    }
}
