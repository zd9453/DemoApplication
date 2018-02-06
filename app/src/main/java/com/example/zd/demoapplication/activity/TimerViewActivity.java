package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.TimerUtils;
import com.example.zd.demoapplication.view.TimerTextView;

import static com.example.zd.demoapplication.utils.TimerUtils.STATE_END;

public class TimerViewActivity extends AppCompatActivity implements TimerUtils.TimerListener, View.OnClickListener {

    private TimerTextView time;
    private TimerUtils instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_view);
        time = (TimerTextView) findViewById(R.id.tv_time);

        time.setOnClickListener(this);

        instance = TimerUtils.getInstance();
        instance.setTimerListener(this);
        instance.startTimer(time, 10);

    }

    @Override
    public void animationState(@TimerUtils.TimerState String state) {
        switch (state) {
            case STATE_END:
                if (instance != null) {
                    instance = null;
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (instance != null) {
            instance.stopTimer();
        }
    }
}
