package com.example.zd.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zd.demoapplication.activity.ConstraintLayoutActivity;
import com.example.zd.demoapplication.activity.CoordinatorLayoutActivity;
import com.example.zd.demoapplication.activity.CreateViewActivity;
import com.example.zd.demoapplication.activity.MediaActivity;
import com.example.zd.demoapplication.activity.TimerViewActivity;

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
    }

    public void button3(View view) {
        Intent intent = new Intent(this, MediaActivity.class);
        startActivity(intent);
    }

    public void button4(View view) {
        Intent intent = new Intent(this, TimerViewActivity.class);
        startActivity(intent);
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
    }
}
