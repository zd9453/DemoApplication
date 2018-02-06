package com.example.zd.demoapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zd.demoapplication.R;

public class VideoTest2Activity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView surfaceView;
    private ImageButton imb_start;
    private TextView tvTime;
    private static final String TAG = "VideoTest2Activity";

    /**
     * 基本数据类型
     */
    private short aShort;//短整形 2字节 16位
    private byte aByte;//字节型 1字节 8位
    private char aChar;//
    private double aDouble;// 8 64
    private long aLong;
    private int anInt;
    private boolean aBoolean;
    private float aFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_camera);
        surfaceView = (SurfaceView) findViewById(R.id.sfv_view);
        imb_start = (ImageButton) findViewById(R.id.imb_start);
        tvTime = (TextView) findViewById(R.id.tv_time);
        imb_start.setOnClickListener(this);


        Context context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_start:
                Log.d(TAG, "onClick: ----------");
                break;
            default:
                break;
        }
    }

    private void changeDate() {

    }
}
