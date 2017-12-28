package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zd.demoapplication.R;

public class VideoTest2Activity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView surfaceView;
    private ImageButton imb_start;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_camera);
        surfaceView = (SurfaceView) findViewById(R.id.sfv_view);
        imb_start = (ImageButton) findViewById(R.id.imb_start);
        tvTime = (TextView) findViewById(R.id.tv_time);

        imb_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
